package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
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
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterLandingStepProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.TelemetryBoosterProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.DestructionOrderRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@ContextConfiguration(classes = { BoosterRPCService.class, BoosterService.class, TelemetryBoosterProducer.class,
        BoosterStatusProducer.class, BoosterLandingStepProducer.class })
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Tags(value = { @Tag("grpc"), @Tag("grpc-booster") })
public class BoosterTest {

    @Autowired
    private BoosterRPCService boosterRpcService;

    @MockBean
    TelemetryBoosterProducer telemetryBoosterProducer;

    @MockBean
    BoosterLandingStepProducer boosterLandingStepProducer;

    @MockBean
    BoosterStatusProducer boosterStatusProducer;

    @Autowired
    BoosterService boosterService;

    Booster boosterTest;

    @BeforeAll
    public void init() throws CannotBeNullException {
        boosterTest = new Booster().status(BoosterStatus.READY).fuelLevel(100);
        boosterService.addNewBooster(boosterTest);

    }

    @Test
    @Order(1)
    public void initiateLandingSequenceTest() {
        BoosterRequest request = BoosterRequest.newBuilder().setBoosterId(boosterTest.getId()).setDistanceFromEarth(500)
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

    @Test
    @Order(3)
    public void testUpdateState() throws BoosterDoesNotExistException {

        boosterService.updateBoosterState(boosterTest.getId(), BoosterStatus.READY);

        assertTrue(boosterService.retrieveBooster(boosterTest.getId()).getStatus() == BoosterStatus.READY);

    }

    @Test
    @Order(4)
    public void destroyRocketNotDestroyedIsOkTest() throws Exception {
        DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setBoosterId(boosterTest.getId()).build();
        StreamRecorder<DesctructionOrderReply> responseObserver = StreamRecorder.create();
        boosterRpcService.destructionOrderOnBooster(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNull(responseObserver.getError());

        List<DesctructionOrderReply> results = responseObserver.getValues();
        DesctructionOrderReply response = results.get(0);
        assertEquals("DESTROYED! KNOCK-DOWNED! SHATTERED!", response.getDestructionBooster());
    }

    @Test
    @Order(5)
    public void destroyRocketAlreadyDestroyedIsKoTest() throws Exception {
        DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setBoosterId(boosterTest.getId()).build();
        StreamRecorder<DesctructionOrderReply> responseObserver = StreamRecorder.create();
        boosterRpcService.destructionOrderOnBooster(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNotNull(responseObserver.getError());

        StatusException t = (StatusException) responseObserver.getError();
        assertEquals(Status.ABORTED.getCode(), t.getStatus().getCode());
    }

}
