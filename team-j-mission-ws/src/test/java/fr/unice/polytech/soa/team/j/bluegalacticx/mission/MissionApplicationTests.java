package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionStatusRequest.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.utils.JsonUtils;

@AutoConfigureMockMvc
@WebMvcTest(MissionController.class)
@ContextConfiguration(classes = { MissionController.class })
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class MissionApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RestApiService restService;

	@MockBean
	private MissionService missionService;

	private Mission mission;
	private Mission missionReply;
	private Date date;
	private String rocketId;
	private String missionId;
	private String payloadId;
	private SpaceCoordinate destination;

	private void missionInit() {
		mission = new Mission();
		date = new Date();
		rocketId = "100";
		missionId = "1";
		payloadId = "1";
		destination = new SpaceCoordinate(1000, 100, 10);
		mission = new Mission(missionId, rocketId, payloadId, destination, date, MissionStatus.PENDING);
		missionReply = new Mission(missionId, rocketId, payloadId, destination, date, MissionStatus.PENDING);
	}

	@BeforeAll
	public void init() throws BadPayloadIdException, MissionDoesNotExistException, BadRocketIdException {
		missionInit();
		Mockito.doNothing().when(restService).updatePayloadStatus(missionId, PayloadStatus.ON_MISSION);
		Mockito.doNothing().when(restService).updateRocketStatus(missionId, RocketStatus.IN_SERVICE);
	}

	@BeforeEach
	public void setup() {
		missionInit();
	}

	@Test
	@Order(1)
	void createMissionWithGoodParametersTest() throws Exception {
		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/").content(JsonUtils.toJson(mission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	@Order(2)
	void createMissionWithBadParametersTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/mission/").content("Create a mission").characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	@Order(3)
	void createMissionWithBadUrlTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/").content(JsonUtils.toJson(mission)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(missionService, times(0)).createMission(any(Mission.class));

	}

	@Test
	@Order(4)
	void createInvalidMissionTest() throws Exception {

		Mission invalidMission = new Mission();

		when(missionService.createMission(any(Mission.class))).thenThrow(new InvalidMissionException());

		mvc.perform(MockMvcRequestBuilders.post("/mission/").content(JsonUtils.toJson(invalidMission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(status().reason(
						"Invalid mission, give a correct date and an available rocket ID (positive and greather than 0)"));

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	@Order(5)
	void changeMissionStatusToSuccessFul() throws Exception {
		mission.setStatus(MissionStatus.SUCCESSFUL);
		when(missionService.setMissionStatus(any(MissionStatus.class), any())).thenReturn(mission);

		mvc.perform(MockMvcRequestBuilders.post("/mission/status/" + missionId)
				.content(JsonUtils.toJson(MissionStatus.SUCCESSFUL)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath(".status").value("SUCCESSFUL"));

		verify(missionService, times(1)).setMissionStatus(eq(MissionStatus.SUCCESSFUL), eq("1"));
	}

	@Test
	void changeRocketStatusTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/mission/status/" + missionId + "/rocket/")
				.content(JsonUtils.toJson(RocketStatus.IN_SERVICE)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void changePayloadStatusTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/mission/status/" + missionId + "/payload/")
				.content(JsonUtils.toJson(PayloadStatus.ON_MISSION)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getDestinationTest() throws Exception {
		when(missionService.retrieveDestination(any(String.class))).thenReturn(destination);

		mvc.perform(
				MockMvcRequestBuilders.get("/mission/destination/" + missionId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.x", is(1000))).andExpect(jsonPath("$.y", is(100)))
				.andExpect(jsonPath("$.z", is(10)));
	}

}
