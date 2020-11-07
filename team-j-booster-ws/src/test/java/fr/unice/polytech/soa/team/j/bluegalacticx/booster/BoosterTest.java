package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.Empty;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterLandingStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.TelemetryBoosterProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.LandingRequest;
import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@ContextConfiguration(classes = { BoosterRPCService.class, BoosterService.class, TelemetryBoosterProducer.class,
        BoosterStatusProducer.class })
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Tags(value = { @Tag("grpc"), @Tag("grpc-booster") })
public class BoosterTest {

    @Autowired
    private BoosterRPCService boosterRpcService;

    @MockBean
    TelemetryBoosterProducer telemetryBoosterProducer;

    @MockBean
    BoosterStatusProducer boosterStatusProducer;

    @Autowired
    BoosterService boosterService;

    Booster boosterTest;

    @BeforeAll
    public void init() throws CannotBeNullException {
        boosterTest = new Booster().id("1").status(BoosterStatus.READY).fuelLevel(100);
        boosterService.addNewBooster(boosterTest);
    }

    @Test
    @Order(1)
    public void initiateLandingSequenceTest() {
        LandingRequest request = LandingRequest.newBuilder().setBoosterId(boosterTest.getId()).setDistanceFromEarth(500)
                .setSpeed(200).build();

        StreamRecorder<Empty> responseObserver = StreamRecorder.create();
        boosterRpcService.initiateLandingSequence(request, responseObserver);

        try {
            if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
                fail("The call did not terminate in time");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        assertNull(responseObserver.getError());

        assertNull(responseObserver.getValues().get(0));
    }

    @Test
    @Order(2)
    public void updateBoosterStateTest() {
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.NOT_LANDING, boosterTest.getLandingStep());
        boosterTest.setDistanceFromEarth(400);
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.FLIPPING, boosterTest.getLandingStep());
        boosterTest.setDistanceFromEarth(250);
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.ENTRY_BURN, boosterTest.getLandingStep());
        boosterTest.setDistanceFromEarth(80);
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.LANDING_BURN, boosterTest.getLandingStep());
        boosterTest.setDistanceFromEarth(5);
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.LEGS_DEPLOYED, boosterTest.getLandingStep());
        boosterTest.setDistanceFromEarth(0);
        boosterTest.updateState();
        assertEquals(BoosterLandingStep.LANDED, boosterTest.getLandingStep());
        assertEquals(BoosterStatus.LANDED, boosterTest.getStatus());
    }
}
