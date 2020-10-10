package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.SpaceMetricsMocked;

@Service
public class RocketApi {
    // mocked data
    private int iteration = 10;
    private Double mockFuelStep = null;
    private Double mockDistStep = null;
    private SpaceCoordinate origin = new SpaceCoordinate(0, 0, 0);

    public RocketApi withOriginCoordinate(SpaceCoordinate origin) {
        this.origin = origin;
        return this;
    }

    public RocketApi withNumberOfIteration(int number) {
        this.iteration = number;
        return this;
    }

    public SpaceMetrics launchWhenReady(SpaceCoordinate objectiveCoordinates, String rocketId) {
        double distance = computeDistance(origin, objectiveCoordinates);
        this.mockDistStep = distance / this.iteration;
        this.mockFuelStep = 100.0 / this.iteration;
        return SpaceMetricsMocked.inAir.distance(distance).rocketId(rocketId);
    }

    public SpaceMetrics retrieveLastMetrics() {
        if (this.mockDistStep == null) {
            return SpaceMetricsMocked.onGround;
        }
        return SpaceMetricsMocked.nextMetrics(this.mockDistStep, this.mockFuelStep);
    }

    private int computeDistance(SpaceCoordinate from, SpaceCoordinate to) {
        return (int) Math.round(
                Math.sqrt(pow2(from.getX(), to.getX()) + pow2(from.getY(), to.getY()) + pow2(from.getZ(), to.getZ())));
    }

    private double pow2(int a1, int a2) {
        return Math.pow(a1 - a2, 2);
    }

}
