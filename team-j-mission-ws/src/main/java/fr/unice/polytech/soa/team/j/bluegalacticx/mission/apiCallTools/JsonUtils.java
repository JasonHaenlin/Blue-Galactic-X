package fr.unice.polytech.soa.team.j.bluegalacticx.mission.apiCallTools;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static String toJson(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
