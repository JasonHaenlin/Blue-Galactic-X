package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.MongoTelemetryConfig;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryRocketDataRepository;

@AutoConfigureMockMvc
@WebMvcTest(TelemetryController.class)
@ContextConfiguration(classes = { TelemetryController.class, TelemetryRocketDataRepository.class,
        MongoTelemetryConfig.class })
class TelemetryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TelemetryService telemetryService;

    @MockBean
    private TelemetryRocketData rocketData;

    @MockBean
    private TelemetryPayloadData payloadData;

    @MockBean
    private TelemetryBoosterData boosterData;

    @BeforeEach
    public void setup() {
        rocketData = new TelemetryRocketData();
        rocketData.setRocketId("125");
        rocketData.heatShield(50);
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

}
