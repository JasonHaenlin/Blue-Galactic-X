package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.utils.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.MissionPreparationController;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.MissionPreparationService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.producers.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.producers.MissionPreparationProducer;

@AutoConfigureMockMvc
@WebMvcTest(MissionPreparationController.class)
@ContextConfiguration(classes = { MissionPreparationController.class, MissionPreparationService.class,
		DepartmentStatusProducer.class, MissionPreparationProducer.class })
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class MissionApplicationTests {

	@Autowired
	private MockMvc mvc;

	private Mission mission;

	@MockBean
	DepartmentStatusProducer departmentStatusProducer;

	@MockBean
	MissionPreparationProducer missionPreparationProducer;

	private void missionInit() {
		SpaceCoordinate destination = new SpaceCoordinate(1000, 100, 10);
		mission = new Mission().rocketId("1").boosterIds(new String[] { "1", "2" }).payloadId("1")
				.destination(destination);
	}

	@BeforeAll
	public void setup() {
		missionInit();
	}

	@Test
	@Order(1)
	void createMissionWithGoodParametersTest() throws Exception {
		MvcResult resp = mvc.perform(MockMvcRequestBuilders.post("/missionPreparation/")
				.content(JsonUtils.toJson(mission)).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		String json = resp.getResponse().getContentAsString();
		mission = new ObjectMapper().readValue(json, Mission.class);

		assertNotNull(mission.getId());

	}

	@Test
	@Order(2)
	void createMissionWithBadParametersTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/missionPreparation/").content("Create a mission")
				.characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Order(3)
	void createMissionWithBadUrlTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/").content(JsonUtils.toJson(mission)).characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(4)
	void getDestinationTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/missionPreparation/destination/" + mission.getId())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.x", is(1000))).andExpect(jsonPath("$.y", is(100)))
				.andExpect(jsonPath("$.z", is(10)));
	}

}
