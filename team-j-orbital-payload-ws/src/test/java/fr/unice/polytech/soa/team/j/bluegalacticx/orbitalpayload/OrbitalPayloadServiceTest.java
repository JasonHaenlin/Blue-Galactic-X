package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers.PayloadStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers.TelemetryPayloadProducer;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrbitalPayloadServiceTest {

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Captor
    ArgumentCaptor<Payload> payloadCaptor;

    @Mock
    PayloadStatusProducer payloadStatusProducer;

    @Mock
    TelemetryPayloadProducer telemetryPayloadProducer;

    @InjectMocks
    OrbitalPayloadService orbitalPayloadService;

    @Test
    @Order(1)
    public void activateTelemetryRecorderNotFoundPayloadTest() {
        Assertions.assertThrows(PayloadNotFoundException.class, () -> {
            orbitalPayloadService.activateTelemetryRecorder("5");
        });
    }

    @Test
    @Order(2)
    public void noTelemetryShouldBeSentWithoutPayloadTest() {
        orbitalPayloadService.sendNewTelemetry();

        Mockito.verify(telemetryPayloadProducer, never()).notifyDeployedPayloadEvent(any());
    }

    @Test
    @Order(3)
    public void addPayloadTest() {
        Payload p1 = new Payload().payloadId("1").position(100, 100, 100).missionId("1");
        Payload p2 = new Payload().payloadId("2").position(100, 100, 100).missionId("2");
        Payload p3 = new Payload().payloadId("3").position(100, 100, 100).missionId("3");

        orbitalPayloadService.addPayload(p1);
        orbitalPayloadService.addPayload(p2);
        orbitalPayloadService.addPayload(p3);
    }

    @Test
    @Order(4)
    public void noTelemetryShouldBeSentWithoutDeployedPayloadTest() {
        orbitalPayloadService.sendNewTelemetry();

        Mockito.verify(telemetryPayloadProducer, never()).notifyDeployedPayloadEvent(any());
    }

    @Test
    @Order(5)
    public void TelemetryShouldBeSentOnDeployedPayloadTest() throws PayloadNotFoundException {
        orbitalPayloadService.activateTelemetryRecorder("1");
        orbitalPayloadService.activateTelemetryRecorder("3");

        orbitalPayloadService.sendNewTelemetry();

        Mockito.verify(telemetryPayloadProducer, times(2)).notifyDeployedPayloadEvent(payloadCaptor.capture());

        assertEquals("1", payloadCaptor.getAllValues().get(0).getPayloadId());
        assertEquals("3", payloadCaptor.getAllValues().get(1).getPayloadId());
    }

}
