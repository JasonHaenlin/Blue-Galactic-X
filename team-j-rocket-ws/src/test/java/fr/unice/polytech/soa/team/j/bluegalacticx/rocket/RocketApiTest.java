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
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;

@Tags(value = { @Tag("api"), @Tag("api-rocket") })
@TestInstance(Lifecycle.PER_METHOD)
public class RocketApiTest {

    private RocketApi api;

    @BeforeEach
    public void init() {
        RocketsMocked.reset();
        api = new RocketApi().withNumberOfIteration(10).withOriginCoordinate(new SpaceCoordinate(0, 0, 0));
    }

    @Test
    public void iterationsNotSupposedToChangeOnGroundTest() {
        SpaceTelemetry ms;
        ms = api.retrieveLastTelemetry("1");
        assertEquals(RocketsMocked.findSpaceTelemetryInGround("1").get(), ms);
        ms = api.retrieveLastTelemetry("1");
        assertEquals(RocketsMocked.findSpaceTelemetryInGround("1").get(), ms);
        ms = api.retrieveLastTelemetry("1");
        assertEquals(RocketsMocked.findSpaceTelemetryInGround("1").get(), ms);
    }

    @Test
    public void iterationsOnAirSupposedToChangeTest() {
        SpaceTelemetry ms;
        ms = api.retrieveLastTelemetry("1");
        assertEquals(RocketsMocked.findSpaceTelemetryInGround("1").get(), ms);

        ms = api.launchWhenReady(new SpaceCoordinate(10, 10, 0), "1");
        
        assertEquals(RocketsMocked.findSpaceTelemetryInAir("1").get(), ms);
        assertEquals(RocketsMocked.findSpaceTelemetryInAir("1").get().getDistance(), ms.getDistance());

        double lDist;

        for (int i = 0; i < 5; i++) {
            lDist = RocketsMocked.findSpaceTelemetryInAir("1").get().getDistance();
            ms = api.retrieveLastTelemetry("1");
            assertTrue(lDist > ms.getDistance());
        }
    }

    @Test
    public void shouldBeZeroOfDistanceAfterIterationsTest() throws BoosterDestroyedException {
        SpaceTelemetry ms;
        ms = api.retrieveLastTelemetry("1");
        assertEquals(RocketsMocked.findSpaceTelemetryInGround("1").get(), ms);

        ms = api.launchWhenReady(new SpaceCoordinate(10, 10, 0), "1");

        for (int i = 0; i < 5; i++) {
            ms = api.retrieveLastTelemetry("1");
        }

        for (int i = 0; i < 5; i++) {
            ms = api.retrieveLastTelemetry("1");
        }

        assertEquals(0.0, ms.getDistance(), 0.1);

    }

}
