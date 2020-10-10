package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketController;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Engine;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.EngineState;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.utils.JsonUtil;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { RocketController.class, RocketService.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-rocket") })
@TestMethodOrder(OrderAnnotation.class)
public class RocketControllerTest {

    private static final String ROCKET_OVERALL = "the rocket is ready, and you got to believe me,"
            + " nobody know more about rocket readiness than I do, it is by far, the most ready rocket that I have ever monitor";

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void getRocketStatusShouldBeOkTest() throws Exception {
        mvc.perform(get("/rocket/status/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(7)));
    }

    @Test
    @Order(2)
    public void getRocketStatusNoReportShouldBeFoundTest() throws Exception {
        mvc.perform(get("/rocket/report/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void postRocketReportShouldBeOkTest() throws Exception {
        mvc.perform(post("/rocket/report/1").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(
                        new RocketReport(100, 0, new Engine(EngineState.READY, EngineState.READY), ROCKET_OVERALL))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void getRocketReportShouldBeFoundTest() throws Exception {
        mvc.perform(get("/rocket/report/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().contains(ROCKET_OVERALL);
    }

    @Test
    @Order(5)
    public void getWrongRocketReportShouldBeNotFoundTest() throws Exception {
        mvc.perform(get("/rocket/report/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    public void getWrongRocketStatusShouldBeNotFoundTest() throws Exception {
        mvc.perform(get("/rocket/status/3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void postEmptyRocketStatusShouldNotAcceptableTest() throws Exception {
        mvc.perform(post("/rocket/report/2").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(new RocketReport()))).andExpect(status().isNotAcceptable());
    }
}
