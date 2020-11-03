package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { RocketController.class, RocketService.class, DepartmentStatusProducer.class,
        RocketStatusProducer.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-rocket") })
@TestMethodOrder(OrderAnnotation.class)
public class RocketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentStatusProducer departmentStatusProducer;

    @MockBean
    private RocketStatusProducer rocketStatusProducer;

    @Test
    @Order(1)
    public void getRocketStatusShouldNotOkTest() throws Exception {
        mvc.perform(get("/rocket/telemetry/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

}
