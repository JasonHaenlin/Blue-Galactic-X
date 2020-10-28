package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

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

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.RocketStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled.RocketScheduler;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled.ScheduledConfig;

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

    @Test
    @Order(1)
    public void TelemetrySchedulerTest() throws InterruptedException {
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> verify(sch, atLeast(5)).scheduleRocketTelemetryTask());
    }

}
