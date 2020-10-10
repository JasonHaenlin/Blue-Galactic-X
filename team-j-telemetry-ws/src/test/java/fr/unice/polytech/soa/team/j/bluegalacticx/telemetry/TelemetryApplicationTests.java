package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.MongoTelemetryConfig;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryRocketDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.utils.JsonUtils;

@AutoConfigureMockMvc
@WebMvcTest(TelemetryController.class)
@ContextConfiguration(classes = { TelemetryController.class, TelemetryRocketDataRepository.class,
        MongoTelemetryConfig.class })
class TelemetryApplicationTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TelemetryService telemetryService;
    private TelemetryRocketData rocketData;

    @BeforeEach
    public void setup() {

        rocketData = new TelemetryRocketData();
        rocketData.setRocketId("125");
        rocketData.fuelLevel(50);

    }

    @Test
    public void createTelemetryDataRocketGoodParameterTest() throws Exception {

        telemetryService.createRocketData(any(TelemetryRocketData.class));

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/rocket")
                    .content(JsonUtils.toJson(rocketData))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
         // @formatter:on

    }

    @Test
    public void createTelemetryDataRocketBadParameterTest() throws Exception {

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/rocket")
                    .content(JsonUtils.toJson("{fuelLevel : 10}"))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
         // @formatter:on
    }

    @Test
    public void createTelemetryDataRocketNoIdTest() throws Exception {

        TelemetryRocketData telemetryRocketDataNoId = new TelemetryRocketData().fuelLevel(50);

        // @formatter:off
        Mockito.doThrow(new TelemetryDataRocketIdException())
                .when(telemetryService)
                .createRocketData(telemetryRocketDataNoId);

        mvc.perform(MockMvcRequestBuilders.post("/telemetry/rocket")
                    .content(JsonUtils.toJson(telemetryRocketDataNoId))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        // @formatter:on
    }

    @Test
    public void createTelemetryDataRocketBadUrl() throws Exception {

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/rocke")
                    .content(JsonUtils.toJson(rocketData))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        // @formatter:off

    }

    @Test
    public void retrieveTelemetryDataRocketNotExistTest() throws Exception {

        when(telemetryService.retrieveRocketData("125")).thenThrow(new NoTelemetryRocketDataException());

        // @formatter:off
        mvc.perform(get("/telemetry/rocket/125")
                    .param("rocketId", "125")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        // @formatter:on
    }

    @Test
    public void retrieveTelemetryDataRocketExistTest() throws Exception {

        TelemetryRocketData telemetryRocketData = new TelemetryRocketData();
        telemetryRocketData.fuelLevel(100);
        telemetryRocketData.setRocketId("125");

        when(telemetryService.retrieveRocketData("125")).thenReturn(telemetryRocketData);

        // @formatter:off
        mvc.perform(get("/telemetry/rocket/125").param("rocketId", "125")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(".fuelLevel").exists())
                    .andExpect(jsonPath(".fuelLevel").value(100))
                    .andExpect(jsonPath(".rocketId").exists())
                    .andExpect(jsonPath(".rocketId").value("125"));
         // @formatter:on
    }

}