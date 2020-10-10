package fr.unice.polytech.soa.team.j.bluegalacticx.springmission;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.RocketStatus;

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
	private String rocketId;
	private String missionId;
	private String payloadId;
	private SpaceCoordinate destination;

	@BeforeEach
	public void setup() {
		mission = new Mission();
		date = new Date();
		rocketId = "100";
		missionId = "1";
		payloadId = "1";
		destination = new SpaceCoordinate(1000, 100, 10);
		mission = new Mission(missionId, rocketId, payloadId, destination, date);
		missionReply = new MissionReply(mission);
	}

	@Test
	@Order(1)
	void createMissionWithGoodParametersTest() throws Exception {
		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content(JsonUtils.toJson(mission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath(".rocketId").value(rocketId))
				.andExpect(jsonPath(".date").exists());

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	@Order(2)
	void createMissionWithBadParametersTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content("Create a mission")
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	@Order(3)
	void createMissionWithBadUrlTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/create").content(JsonUtils.toJson(mission)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	@Order(4)
	void createInvalidMissionTest() throws Exception {

		Mission invalidMission = new Mission();

		when(missionService.createMission(any(Mission.class))).thenThrow(new InvalidMissionException());

		mvc.perform(MockMvcRequestBuilders.post("/mission/create").content(JsonUtils.toJson(invalidMission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(status().reason(
						"Invalid mission, give a correct date and an available rocket ID (positive and greather than 0)"));

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	@Order(5)
	void changeMissionStatusToSuccessFul() throws Exception {
		mission.setMissionStatus(MissionStatus.SUCCESSFUL);
		when(missionService.setMissionStatus(any(MissionStatus.class), any())).thenReturn(new MissionReply(mission));

		mvc.perform(MockMvcRequestBuilders.post("/mission/status/" + missionId)
				.content(JsonUtils.toJson(MissionStatus.SUCCESSFUL)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath(".missionStatus").value("SUCCESSFUL"));

		verify(missionService, times(1)).setMissionStatus(eq(MissionStatus.SUCCESSFUL), eq("1"));
	}

	// @Test
	// void changeRocketStatusTest() throws Exception {
	// mvc.perform(MockMvcRequestBuilders.post("/status/rocket/" + rocketId)
	// .content(JsonUtils.toJson(RocketStatus.IN_SERVICE)).characterEncoding("utf-8")
	// .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	// }

	// @Test
	// void changePayloadStatusTest() throws Exception {
	// mvc.perform(MockMvcRequestBuilders.post("/status/payload/" + payloadId)
	// .content(JsonUtils.toJson(PayloadStatus.ON_MISSION)).characterEncoding("utf-8")
	// .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	// }

	@Test
	void getDestinationTest() throws Exception {
		when(missionService.retrieveDestination(any(String.class))).thenReturn(destination);

		mvc.perform(MockMvcRequestBuilders.get("/destination/" + missionId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.x", is(1000))).andExpect(jsonPath("$.y", is(100)))
				.andExpect(jsonPath("$.z", is(10)));
	}

}
