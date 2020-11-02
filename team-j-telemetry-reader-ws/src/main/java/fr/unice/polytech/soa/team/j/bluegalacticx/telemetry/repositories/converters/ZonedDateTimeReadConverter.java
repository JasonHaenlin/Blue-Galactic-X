package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.converters;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public enum ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

    INSTANCE;

    @Override
    public ZonedDateTime convert(Date date) {
        return date.toInstant().atZone(ZoneOffset.UTC);
    }
}
