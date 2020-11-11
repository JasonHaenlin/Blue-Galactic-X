package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.converters.ZonedDateTimeReadConverter;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.converters.ZonedDateTimeWriteConverter;

@Configuration
@EnableMongoRepositories
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
        converters.add(ZonedDateTimeReadConverter.INSTANCE);
        converters.add(ZonedDateTimeWriteConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }

}
