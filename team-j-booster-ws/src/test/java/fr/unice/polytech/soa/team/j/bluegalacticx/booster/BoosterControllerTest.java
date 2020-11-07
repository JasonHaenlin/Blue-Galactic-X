package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.TelemetryBoosterProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.utils.JsonUtil;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { BoosterController.class, BoosterService.class, TelemetryBoosterProducer.class,
        BoosterStatusProducer.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-booster") })
@TestMethodOrder(OrderAnnotation.class)
public class BoosterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TelemetryBoosterProducer telemetryBoosterProducer;

    @MockBean
    BoosterStatusProducer boosterStatusProducer;

    @BeforeAll
    public static void initBoosters() {
        List<Booster> listBoosters = new ArrayList<>();
        listBoosters.add(new Booster().id("1").status(BoosterStatus.PENDING).fuelLevel(100));
        listBoosters.add(new Booster().id("2").status(BoosterStatus.RUNNING).fuelLevel(40)
                .telemetry(new BoosterTelemetry(500, 200)));
    }

    @Test
    @Order(1)
    public void getNotExistingBoosterShouldFailTest() throws Exception {
        mvc.perform(get("/booster/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void createNotValidBoosterShouldFailTest() throws Exception {
        mvc.perform(post("/booster").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void createValidBoosterShouldSuccessTest() throws Exception {
        mvc.perform(post("/booster").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(new Booster().id("42")))).andExpect(status().isOk());

        mvc.perform(get("/booster/42").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PENDING"))).andExpect(jsonPath("$.id", is("42")));
    }
}
