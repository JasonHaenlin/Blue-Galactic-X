package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;
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
        assertEquals(SpaceMetricsMocked.inAir.getFuelLevel(), ms.getFuelLevel());

        double lDist;
        double lFuel;

        for (int i = 0; i < 5; i++) {
            lDist = SpaceMetricsMocked.inAir.getDistance();
            lFuel = SpaceMetricsMocked.inAir.getFuelLevel();
            ms = api.retrieveLastMetrics();
            assertTrue(lDist > ms.getDistance());
            assertTrue(lFuel > ms.getFuelLevel());
        }
        System.out.println("stop");
    }

    @Test
    public void shouldBeZeroOfDistanceAfterIterationsTest() {
        SpaceMetrics ms;
        ms = api.retrieveLastMetrics();
        assertEquals(SpaceMetricsMocked.onGround, ms);

        ms = api.launchWhenReady(new SpaceCoordinate(10, 10, 0), "");

        for (int i = 0; i < 10; i++) {
            ms = api.retrieveLastMetrics();
        }

        assertTrue(0.0 == ms.getDistance());
        assertTrue(10.0 == ms.getFuelLevel());
    }

}
