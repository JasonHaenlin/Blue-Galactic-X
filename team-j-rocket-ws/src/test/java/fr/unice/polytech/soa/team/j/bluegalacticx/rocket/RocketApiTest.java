package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.SpaceMetricsMocked;

@Tags(value = { @Tag("api"), @Tag("api-rocket") })
@TestInstance(Lifecycle.PER_METHOD)
public class RocketApiTest {

    private RocketApi api;

    @BeforeEach
    public void init() {
        SpaceMetricsMocked.preset();
        api = new RocketApi().withNumberOfIteration(10).withOriginCoordinate(new SpaceCoordinate(0, 0, 0));
    }

    @Test
    public void iterationsNotSupposedToChangeOnGroundTest() {
        SpaceMetrics ms;
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);
    }

    @Test
    public void iterationsOnAirSupposedToChangeTest() {
        SpaceMetrics ms;
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);

        ms = api.launchWhenReady(new SpaceCoordinate(10, 10, 0), "1");
        assertEquals(SpaceMetricsMocked.inAir.getDistance(), ms.getDistance());

        double lDist;

        for (int i = 0; i < 5; i++) {
            lDist = SpaceMetricsMocked.inAir.getDistance();
            ms = api.retrieveLastMetrics();
            assertTrue(lDist > ms.getDistance());
        }
    }

    @Test
    public void shouldBeZeroOfDistanceAfterIterationsTest() throws BoosterDestroyedException {
        SpaceMetrics ms;
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);

        ms = api.launchWhenReady(new SpaceCoordinate(10, 10, 0), "");

        for (int i = 0; i < 5; i++) {
            ms = api.retrieveLastMetrics();
        }

        assertEquals(7.0, ms.getDistance(), 0.1);

        for (int i = 0; i < 5; i++) {
            ms = api.retrieveLastMetrics();
        }

        assertEquals(0.0, ms.getDistance(), 0.1);

    }


}
