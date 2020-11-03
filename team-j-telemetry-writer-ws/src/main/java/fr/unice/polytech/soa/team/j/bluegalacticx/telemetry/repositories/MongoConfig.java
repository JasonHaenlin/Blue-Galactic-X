package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.utils.dates.ZonedDateTimeReadConverter;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.utils.dates.ZonedDateTimeWriteConverter;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;
    @Value("${spring.data.mongodb.host}")
    private String hostMongodb;
    @Value("${spring.data.mongodb.port}")
    private String portMongodb;

    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString(
                "mongodb://" + hostMongodb + ":" + portMongodb + "/telemetry");
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

    @Autowired
    private MappingMongoConverter mongoConverter;

    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    public MongoCustomConversions customConversions() {
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mongoConverter);
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
}
