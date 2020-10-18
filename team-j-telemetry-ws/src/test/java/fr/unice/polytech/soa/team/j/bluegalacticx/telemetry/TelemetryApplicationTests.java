package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryPayloadDataException;
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
    private TelemetryBoosterData boosterData;
    private TelemetryPayloadData payloadData;
    private TelemetryBoosterData boosterData;

    @BeforeEach
    public void setup() {

        rocketData = new TelemetryRocketData();
        rocketData.setRocketId("125");
        rocketData.heatShield(50);
        boosterData = new TelemetryBoosterData();
        boosterData.boosterId("10").rocketID("125").fuel(50);

    }

    @Test
    public void createTelemetryDataBoosterGoodParameterTest() throws Exception {

        telemetryService.createBoosterData(any(TelemetryBoosterData.class));

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/booster")
                    .content(JsonUtils.toJson(boosterData))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
         // @formatter:on

        payloadData = new TelemetryPayloadData();
        payloadData.setPayloadId("501");
        payloadData.setWeight(3000);

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
    public void createTelemetryDataPayloadGoodParameterTest() throws Exception {

        telemetryService.createPayloadData(any(TelemetryPayloadData.class));

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/payload")
                    .content(JsonUtils.toJson(payloadData))
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
    public void createTelemetryDataPayloadBadParameterTest() throws Exception {

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/payload")
                    .content(JsonUtils.toJson("{'weight' : '10'}"))
                    .characterEncoding("utf-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
         // @formatter:on
    }

    @Test
    public void createTelemetryDataRocketNoIdTest() throws Exception {

        TelemetryRocketData telemetryRocketDataNoId = new TelemetryRocketData().heatShield(50);

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
    public void createTelemetryDataPayloadNoIdTest() throws Exception {

        TelemetryPayloadData telemetryPayloadDataNoId = new TelemetryPayloadData();

        // @formatter:off
        Mockito.doThrow(new BadPayloadIdException())
                .when(telemetryService)
                .createPayloadData(telemetryPayloadDataNoId);

        mvc.perform(MockMvcRequestBuilders.post("/telemetry/payload")
                    .content(JsonUtils.toJson(telemetryPayloadDataNoId))
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
    public void createTelemetryDataPayloadBadUrl() throws Exception {

        // @formatter:off
        mvc.perform(MockMvcRequestBuilders.post("/telemetry/p")
                    .content(JsonUtils.toJson(payloadData))
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
    public void retrieveTelemetryDataPayloadNotExistTest() throws Exception {

        when(telemetryService.retrievePayloadData("501")).thenThrow(new NoTelemetryPayloadDataException());

        // @formatter:off
        mvc.perform(get("/telemetry/payload/501")
                    .param("payloadId", "501")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        // @formatter:on
    }

    @Test
    public void retrieveTelemetryDataRocketExistTest() throws Exception {

        List<TelemetryRocketData> telemetryRocketDataList = new ArrayList<>();
        TelemetryRocketData telemetryRocketData = new TelemetryRocketData();
        telemetryRocketData.heatShield(95.0);
        telemetryRocketData.setRocketId("125");

        telemetryRocketDataList.add(telemetryRocketData);

        when(telemetryService.retrieveRocketData("125")).thenReturn(telemetryRocketDataList);

        // @formatter:off
        mvc.perform(get("/telemetry/rocket/125").param("rocketId", "125")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(".heatShield").exists())
                    .andExpect(jsonPath(".heatShield").value(95.0))
                    .andExpect(jsonPath(".rocketId").exists())
                    .andExpect(jsonPath(".rocketId").value("125"));
         // @formatter:on
    }

    @Test
    public void retrieveTelemetryDataPayloadExistTest() throws Exception {

        when(telemetryService.retrievePayloadData("501")).thenReturn(payloadData);

        // @formatter:off
        mvc.perform(get("/telemetry/payload/501").param("payloadId", "501")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(".weight").exists())
                    .andExpect(jsonPath(".weight").value(3000))
                    .andExpect(jsonPath(".payloadId").exists())
                    .andExpect(jsonPath(".payloadId").value("501"));
         // @formatter:on
    }

}
