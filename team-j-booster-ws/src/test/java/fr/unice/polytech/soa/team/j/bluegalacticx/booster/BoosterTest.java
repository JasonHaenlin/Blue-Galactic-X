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
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterLandingStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks.BoostersMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.LandingRequest;
import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@ContextConfiguration(classes = { BoosterRPCService.class })
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Tags(value = { @Tag("grpc"), @Tag("grpc-booster") })
public class BoosterTest {

    @Autowired
    private BoosterRPCService boosterRpcService;

    String boosterId;

    @BeforeAll
    public void init() {
        boosterId = "1";
        BoostersMocked.reset();
    }

    @Test
    @Order(1)
    public void initiateLandingSequenceTest() {
        LandingRequest request = LandingRequest.newBuilder().setBoosterId(boosterId).setDistanceFromEarth(500)
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
        Booster b = BoostersMocked.find(boosterId).get();
        b.updateState();
        assertEquals(BoosterLandingStep.NOT_LANDING, b.getLandingStep());
        b.setDistanceFromEarth(400);
        b.updateState();
        assertEquals(BoosterLandingStep.FLIPPING, b.getLandingStep());
        b.setDistanceFromEarth(250);
        b.updateState();
        assertEquals(BoosterLandingStep.ENTRY_BURN, b.getLandingStep());
        b.setDistanceFromEarth(80);
        b.updateState();
        assertEquals(BoosterLandingStep.LANDING_BURN, b.getLandingStep());
        b.setDistanceFromEarth(5);
        b.updateState();
        assertEquals(BoosterLandingStep.LEGS_DEPLOYED, b.getLandingStep());
        b.setDistanceFromEarth(0);
        b.updateState();
        assertEquals(BoosterLandingStep.LANDED, b.getLandingStep());
        assertEquals(BoosterStatus.LANDED, b.getStatus());
    }
}
