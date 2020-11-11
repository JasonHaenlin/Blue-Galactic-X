package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.MaxQ;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;

@Tags(value = { @Tag("api"), @Tag("api-rocket") })
@TestInstance(Lifecycle.PER_METHOD)
public class RocketApiTest {

    private RocketApi api;
    private int numberIterations;

    @BeforeEach
    public void init() {
        numberIterations = 30;
        api = new RocketApi().withNumberOfIteration(numberIterations).withOriginCoordinate(new SpaceCoordinate(0, 0, 0))
                .withBasedTelemetry();
    }

    @Test
    public void iterationsNotSupposedToChangeAsRocketNotLaunchedTest() {
        SpaceTelemetry ms = api.retrieveLastTelemetry();
        assertEquals(ms, api.retrieveLastTelemetry());
        assertEquals(ms, api.retrieveLastTelemetry());
        assertEquals(ms, api.retrieveLastTelemetry());
    }

    // @Test
    // public void shouldNotBeZeroOfDistanceAfterIterationsTest()
    // throws BoosterDestroyedException, NoObjectiveSettedException {
    // SpaceTelemetry ms;
    // ms = api.launchWhenReady(new SpaceCoordinate(100, 100, 100), "1");
    // api.getCurrentTelemetry().setSpeed(2);
    // for (int i = 0; i < numberIterations; i++) {
    // ms = api.retrieveLastTelemetry();
    // }
    // assertNotEquals(0.0, ms.getDistance());
    // }

    @Test
    public void shouldDetectMaxQWithAnyDistance() throws NoObjectiveSettedException {

        int nbTest = 2;

        for (int i = 0; i < nbTest; i++) {
            init();
            SpaceTelemetry ms;
            int maxDistance = 10000;
            int minDistance = 100;

            int x = new Random().nextInt(maxDistance) + minDistance;
            int y = new Random().nextInt(maxDistance) + minDistance;
            int z = new Random().nextInt(maxDistance) + minDistance;

            ms = api.launchWhenReady(new SpaceCoordinate(x, y, z), "1");

            boolean inMaxQ = false;

            for (int j = 0; j < numberIterations; j++) {
                ms = api.retrieveLastTelemetry();
                if (ms.getDistance() <= ms.getTotalDistance() - MaxQ.MIN
                        && ms.getDistance() >= ms.getTotalDistance() - MaxQ.MAX) {
                    inMaxQ = true;
                }
            }
            assertEquals(inMaxQ, true);

        }

    }

}
