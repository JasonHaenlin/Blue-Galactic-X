package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.MongoTelemetryConfig;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryBoosterDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryRocketDataRepository;


@AutoConfigureMockMvc
@WebMvcTest(TelemetryController.class)
@ContextConfiguration(classes = { TelemetryController.class, TelemetryService.class,
        TelemetryRocketDataRepository.class, TelemetryBoosterDataRepository.class,
        MongoTelemetryConfig.class })
@Tags(value = { @Tag("mvc"), @Tag("mvc-telemetry") })
@TestMethodOrder(OrderAnnotation.class)
public class TelemetryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void check_for_anomalies_the_first_time_should_be_fine() throws Exception {
        // @formatter:off
        mvc.perform(get("/telemetry/anomalies")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        // @formatter:on
    }
}
