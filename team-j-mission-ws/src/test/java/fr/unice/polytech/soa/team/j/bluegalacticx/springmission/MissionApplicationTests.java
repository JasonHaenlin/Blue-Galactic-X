package fr.unice.polytech.soa.team.j.bluegalacticx.springmission;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.MissionController;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.MissionService;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import fr.unice.polytech.soa.team.j.bluegalacticx.springmission.utils.JsonUtils;

@AutoConfigureMockMvc
@WebMvcTest(MissionController.class)
@ContextConfiguration(classes = { MissionController.class })
class MissionApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MissionService missionService;

	private Mission mission;
	private MissionReply missionReply;
	private Date date;
	private int rocketId;

	@BeforeEach
	public void setup() {
		mission = new Mission();
		date = new Date();
		rocketId = 100;
		mission = new Mission(rocketId, date);
		missionReply = new MissionReply(mission);
	}

	@Test
	void createMissionWithGoodParametersTest() throws Exception {
		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content(JsonUtils.toJson(mission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath(".rocketId").value(rocketId))
				.andExpect(jsonPath(".date").exists());

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	void createMissionWithBadParametersTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content("Create a mission or I'll kick ur ass ")
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	void createMissionWithBadUrlTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/create").content(JsonUtils.toJson(mission)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	void createInvalidMissionTest() throws Exception {

		Mission invalidMission = new Mission();

		when(missionService.createMission(any(Mission.class))).thenThrow(new InvalidMissionException());

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content(JsonUtils.toJson(invalidMission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(status().reason("Invalid mission, give a correct date and an available rocket ID (positive and greather than 0)"));

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

}
