package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.converters;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public enum ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Date> {

    INSTANCE;

    @Override
    public Date convert(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }
}
