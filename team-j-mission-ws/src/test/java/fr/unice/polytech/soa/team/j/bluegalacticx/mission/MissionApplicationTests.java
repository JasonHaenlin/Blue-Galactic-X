package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.utils.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.MissionPreparationController;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.MissionPreparationService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate;

@AutoConfigureMockMvc
@WebMvcTest(MissionPreparationController.class)
@ContextConfiguration(classes = { MissionPreparationController.class })
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class MissionApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MissionPreparationService missionService;

	private Mission mission;
	private Mission missionReply;
	private String missionId;
	private SpaceCoordinate destination;

	private void missionInit() {
		missionId = "1";
		destination = new SpaceCoordinate(1000, 100, 10);
		mission = new Mission().id(missionId).rocketId("1").boosterIds(new String[] { "1", "2" }).payloadId("1")
				.destination(destination);
	}

	@BeforeEach
	public void setup() {
		missionInit();
	}

	@Test
	@Order(1)
	void createMissionWithGoodParametersTest() throws Exception {
		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/missionPreparation/").content(JsonUtils.toJson(mission))
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(missionService, times(1)).createMission(any(Mission.class));

	}

	@Test
	@Order(2)
	void createMissionWithBadParametersTest() throws Exception {

		when(missionService.createMission(any(Mission.class))).thenReturn(missionReply);

		mvc.perform(MockMvcRequestBuilders.post("/missionPreparation/").content("Create a mission")
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
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
	void getDestinationTest() throws Exception {
		when(missionService.retrieveDestination(any(String.class))).thenReturn(destination);

		mvc.perform(MockMvcRequestBuilders.get("/missionPreparation/destination/" + missionId)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.x", is(1000))).andExpect(jsonPath("$.y", is(100)))
				.andExpect(jsonPath("$.z", is(10)));
	}

}
