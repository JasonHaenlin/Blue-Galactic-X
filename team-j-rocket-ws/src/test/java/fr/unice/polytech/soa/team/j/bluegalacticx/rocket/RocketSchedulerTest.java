package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;

@SpringJUnitConfig(value = { ScheduledConfig.class, RocketApi.class, RestService.class, RestTemplate.class,
        RocketStatusProducer.class })
@Tags(value = { @Tag("scheduler"), @Tag("scheduler-rocket") })
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class RocketSchedulerTest {

    @SpyBean
    private RocketScheduler sch;

    @MockBean
    private RestService restService;

    @MockBean
    private RocketStatusProducer rocketStatusProducer;

    private int numberIterations;

    private RocketApi api;

    @BeforeEach
    public void init() {
        RocketsMocked.reset();

        numberIterations = 10;
        api = new RocketApi().withNumberOfIteration(numberIterations)
                .withOriginCoordinate(new SpaceCoordinate(0, 0, 0));
    }

    @Test
    @Order(1)
    public void TelemetrySchedulerTest() throws InterruptedException {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> verify(sch, atLeast(5)).scheduleRocketTelemetryTask());
    }

    @Test
    @Order(2)
    public void rocketChangeStatusTest() throws InterruptedException {
        SpaceTelemetry ms;
        Rocket r = RocketsMocked.find("1").get();

        ms = r.getRocketApi().launchWhenReady(new SpaceCoordinate(1000, 2000, 3000), "1");
        List<Double> variationSpeed = new ArrayList<>();
        for (int i = 0; i < numberIterations; i++) {
            ms = r.getLastTelemetry();
            await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
                sch.scheduleRocketTelemetryTask();
            });
            variationSpeed.add(r.getTelemetryInAir().getSpeed());
            if (r.isRocketInMaxQ()) {
                assertEquals(r.getStatus() == RocketStatus.ENTER_MAXQ, true);
            } else if (!r.isRocketInMaxQ() && (r.getStatus() != RocketStatus.AT_BASE && r.getStatus() != RocketStatus.ARRIVED) ) {
                assertEquals(RocketsMocked.find("1").get().getStatus() == RocketStatus.QUIT_MAXQ, true);
            }

        }

        // the speed is generated randomly, so the variation of speed when it enter or quit maxQ is between 15-20%
        double variationDecreasePourcentageSpeedChange = -0.15;
        double variationIncreasePourcentageSpeedChange = 0.15;
        boolean speedHasIncreased = false;
        boolean speedHasDecreased = false;

        for (int i = 0; i < variationSpeed.size() - 1; i++) {
            double variation = (variationSpeed.get(i) - variationSpeed.get(i + 1)) / variationSpeed.get(i);
            if (variation <= variationDecreasePourcentageSpeedChange) {
                speedHasDecreased = true;
            }
            if (variation >= variationIncreasePourcentageSpeedChange) {
                speedHasIncreased = true;
            }
        }

        assertEquals(speedHasDecreased, true);
        assertEquals(speedHasIncreased, true);

    }

}
