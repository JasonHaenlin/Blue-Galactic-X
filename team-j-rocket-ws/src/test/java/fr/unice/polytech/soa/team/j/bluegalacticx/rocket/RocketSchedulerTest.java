package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketLaunchStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.MaxQProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.TelemetryRocketProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled.RocketScheduler;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled.ScheduledConfig;

@SpringJUnitConfig(value = { ScheduledConfig.class, RocketApi.class, RocketService.class, RestTemplate.class,
        RocketStatusProducer.class, TelemetryRocketProducer.class, DepartmentStatusProducer.class, MaxQProducer.class })
@Tags(value = { @Tag("scheduler"), @Tag("scheduler-rocket") })
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class RocketSchedulerTest {

    @Autowired
    private RocketService rocketService;

    @SpyBean
    private RocketScheduler sch;

    @MockBean
    private TelemetryRocketProducer telemetryRocketProducer;

    @MockBean
    private DepartmentStatusProducer departmentStatusProducedStatusProducer;

    @MockBean
    private BoosterRPCClient boosterRpcClient;

    @MockBean
    private RocketStatusProducer rocketStatusProducer;

    @MockBean
    private MaxQProducer maxQProducer;

    private int numberIterations = 10;
    private Rocket rocket;
    private Rocket rocket2;

    private void fakeScheduler() throws RocketDestroyedException {
        rocketService.updateLaunchProcedure();
    }

    private void waitForSeconds(int seconds) throws RocketDestroyedException {
        for (int i = 0; i < seconds; i++) {
            this.fakeScheduler();
        }
    }

    @BeforeEach
    public void init() throws CannotBeNullException {

        RocketApi api = new RocketApi().withNumberOfIteration(numberIterations)
                .withOriginCoordinate(new SpaceCoordinate(0, 0, 0)).withBasedTelemetry();
        rocket = new Rocket().id("1").withRocketApi(api);
        rocketService.addNewRocket(rocket);

        RocketApi api2 = new RocketApi().withNumberOfIteration(numberIterations)
                .withOriginCoordinate(new SpaceCoordinate(0, 0, 0)).withBasedTelemetry();
        rocket2 = new Rocket().id("5").withRocketApi(api2);
        rocketService.addNewRocket(rocket2);

    }

    @Test
    @Order(1)
    public void rocketSpeedChangingTest() throws InterruptedException, NoObjectiveSettedException, 
            RocketDestroyedException {
        rocket.getRocketApi().launchWhenReady(new SpaceCoordinate(1000, 2000, 3000), "1");
        List<Double> variationSpeed = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            rocket.getLastTelemetry();
            waitForSeconds(1);
            variationSpeed.add(rocket.getRocketApi().getCurrentTelemetry().getSpeed());
        }

        // the speed is generated randomly, so the variation of speed when it enter or
        // quit maxQ is between 15-20%
        double variationDecreasePourcentageSpeedChange = -0.15;
        boolean speedHasDecreased = false;

        for (int i = 0; i < variationSpeed.size() - 1; i++) {
            double variation = (variationSpeed.get(i) - variationSpeed.get(i + 1)) / variationSpeed.get(i);
            if (variation <= variationDecreasePourcentageSpeedChange) {
                speedHasDecreased = true;
            }
        }

        assertTrue(speedHasDecreased);

    }

    @Test
    @Order(2)
    public void rocketChangeLaunchStep()
            throws InterruptedException, NoObjectiveSettedException, CannotBeNullException, RocketDestroyedException {
        rocket2.getRocketApi().launchWhenReady(new SpaceCoordinate(600, 600, 600), "5");
        rocket2.setStatus(RocketStatus.STARTING);
        rocket2.setLaunchStep(RocketLaunchStep.STARTUP);
        waitForSeconds(1);
        assertEquals(RocketLaunchStep.STARTUP, rocket2.getLaunchStep());
        waitForSeconds(3);
        assertEquals(RocketLaunchStep.MAIN_ENGINE_START, rocket2.getLaunchStep());
        waitForSeconds(3);
        assertEquals(RocketLaunchStep.LIFTOFF, rocket2.getLaunchStep());
        waitForSeconds(5);
        assertEquals(RocketLaunchStep.ENTER_MAXQ, rocket2.getLaunchStep());
        waitForSeconds(5);
        assertEquals(RocketLaunchStep.MAXQ_PASSED, rocket2.getLaunchStep());
        waitForSeconds(100);
        assertEquals(RocketLaunchStep.FINISHED, rocket2.getLaunchStep());
    }

}
