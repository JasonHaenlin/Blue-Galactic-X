package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MissionLogRepository.class)
public class MongoMissionLogReaderConfig {

        @Value("${api.mongodb.host}")
        private String hostMongodb;
        @Value("${api.mongodb.port}")
        private String portMongodb;

        @Bean
        public MongoClient mongo() throws Exception {
                final ConnectionString connectionString = new ConnectionString(
                                "mongodb://" + hostMongodb + ":" + portMongodb + "/missionlog");
                final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                                .applyConnectionString(connectionString).build();
                return MongoClients.create(mongoClientSettings);
        }

        @Bean
        public MongoTemplate mongoTemplate() throws Exception {
                return new MongoTemplate(mongo(), "missionlog");
        }
}
