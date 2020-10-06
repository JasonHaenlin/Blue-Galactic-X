package fr.unice.polytech.soa.team.j.bluegalacticx.springPayload.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;

public class JsonUtil {

    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String getIdFromPayloadString(String s) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Payload p = mapper.readValue(s, Payload.class);
        return p.getId();
    }
}
