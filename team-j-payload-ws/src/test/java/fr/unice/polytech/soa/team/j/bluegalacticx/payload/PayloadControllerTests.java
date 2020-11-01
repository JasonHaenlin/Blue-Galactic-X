package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers.PayloadStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.utils.JsonUtil;

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = { PayloadController.class, PayloadService.class, PayloadStatusProducer.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-payload") })
@TestMethodOrder(OrderAnnotation.class)
class PayloadControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PayloadStatusProducer payloadStatusProducer;

    private static String createdPayloadid;

    @Test
    @Order(1)
    public void getNotExistingPayloadShouldFailTest() throws Exception {
        mvc.perform(get("/payload/5").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    public void createNotValidPayloadShouldFailTest() throws Exception {
        mvc.perform(post("/payload").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void createValidPayloadShouldSuccessTest() throws Exception {
        createdPayloadid = JsonUtil.getIdFromPayloadString(mvc
                .perform(post("/payload").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(new Payload().id("42").type(PayloadType.SPACECRAFT).weight(10000))))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status", is("WAITING_FOR_MISSION")))
                .andExpect(jsonPath("$.id", is("42"))).andReturn().getResponse().getContentAsString());
    }

    @Test
    @Order(4)
    public void getExistingPayloadShoulSuccessTest() throws Exception {
        mvc.perform(get("/payload/" + createdPayloadid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void setPayloadStatusShouldSuccessTest() throws Exception {
        mvc.perform(post("/payload/" + createdPayloadid).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(PayloadStatus.DELIVERED))).andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DELIVERED")));
    }

    @Test
    @Order(5)
    public void setPayloadStatusShouldFailTest() throws Exception {
        mvc.perform(post("/payload/wrong_id").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.toJson(PayloadStatus.DELIVERED))).andExpect(status().isNotFound());
    }

}
