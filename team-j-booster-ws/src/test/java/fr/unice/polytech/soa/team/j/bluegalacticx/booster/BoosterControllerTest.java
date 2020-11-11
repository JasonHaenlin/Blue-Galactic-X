package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.test.web.servlet.MvcResult;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterLandingStepProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.TelemetryBoosterProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.utils.JsonUtil;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { BoosterController.class, BoosterService.class, TelemetryBoosterProducer.class,
        BoosterStatusProducer.class, BoosterLandingStepProducer.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-booster") })
@TestMethodOrder(OrderAnnotation.class)
public class BoosterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TelemetryBoosterProducer telemetryBoosterProducer;

    @MockBean
    BoosterLandingStepProducer boosterLandingStepProducer;

    @MockBean
    BoosterStatusProducer boosterStatusProducer;

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
        MvcResult resp = mvc.perform(
                post("/booster").contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.toJson(new Booster())))
                .andExpect(status().isOk()).andReturn();

        String json = resp.getResponse().getContentAsString();
        Booster booster = new ObjectMapper().readValue(json, Booster.class);

        mvc.perform(get("/booster/" + booster.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(jsonPath("$.id", is(booster.getId())));
    }
}
