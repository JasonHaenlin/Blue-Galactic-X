package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.kafka.producer.AnomalyProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AnomaliesCentreServiceTest {

    @Captor
    ArgumentCaptor<List<AnomalyRequest>> anomaliesTypeCaptor;

    @Mock
    AnomalyProducer ayProducer;

    @InjectMocks
    AnomaliesCentreService anomaliesCentreService;

    @Test
    public void shouldDispatchNoRocketAnomaliesTest() {
        // @formatter:off
        TelemetryRocketRequest req = TelemetryRocketRequest
                                            .newBuilder()
                                            .setRocketId("15")
                                            .setIrradiance(15)
                                            .setTemperature(300)
                                            .setVibration(15)
                                            .setBoosterRGA(15)
                                            .setMidRocketRGA(45)
                                            .setHeatShield(99)
                                            .setSpeed(500)
                                            .setDistance(5000)
                                            .setTotalDistance(10000)
                                            .build();
        // @formatter:on

        anomaliesCentreService.checkRocketTelemetry(req);

        Mockito.verify(ayProducer).alertAnomalies(anomaliesTypeCaptor.capture());

        assertEquals(0, anomaliesTypeCaptor.getValue().size());
    }

    @Test
    public void shouldDispatchTwoRocketAnomaliesTest() {
        // @formatter:off
        TelemetryRocketRequest req = TelemetryRocketRequest
                                            .newBuilder()
                                            .setRocketId("15")
                                            .setIrradiance(15)
                                            .setTemperature(400)
                                            .setVibration(15)
                                            .setBoosterRGA(15)
                                            .setMidRocketRGA(45)
                                            .setHeatShield(29)
                                            .setSpeed(500)
                                            .setDistance(5000)
                                            .setTotalDistance(10000)
                                            .build();
        // @formatter:on

        anomaliesCentreService.checkRocketTelemetry(req);

        Mockito.verify(ayProducer).alertAnomalies(anomaliesTypeCaptor.capture());

        assertEquals(2, anomaliesTypeCaptor.getValue().size());
    }

    @Test
    public void shouldDispatchnoBoosterAnomaliesTest() {
        // @formatter:off
        TelemetryBoosterRequest req = TelemetryBoosterRequest
                                            .newBuilder()
                                            .setBoosterId("15")
                                            .setRocketId("42")
                                            .setDistanceFromEarth(400)
                                            .setSpeed(600)
                                            .setFuel(60)
                                            .setBoosterStatus("RUNNING")
                                            .build();
        // @formatter:on

        anomaliesCentreService.checkBoosterTelemetry(req);

        Mockito.verify(ayProducer).alertAnomalies(anomaliesTypeCaptor.capture());

        assertEquals(0, anomaliesTypeCaptor.getValue().size());
    }

    @Test
    public void shouldDispatchOneBoosterAnomaliesTest() {
        // @formatter:off
        TelemetryBoosterRequest req = TelemetryBoosterRequest
                                            .newBuilder()
                                            .setBoosterId("15")
                                            .setRocketId("42")
                                            .setDistanceFromEarth(300)
                                            .setSpeed(600)
                                            .setBoosterStatus("LANDING")
                                            .setFuel(60)
                                            .build();
        // @formatter:on

        anomaliesCentreService.checkBoosterTelemetry(req);

        Mockito.verify(ayProducer).alertAnomalies(anomaliesTypeCaptor.capture());

        assertEquals(1, anomaliesTypeCaptor.getValue().size());
    }

}
