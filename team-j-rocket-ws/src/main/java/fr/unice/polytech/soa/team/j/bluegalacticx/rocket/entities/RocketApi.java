package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.utils.RandomUtils;

@Service
public class RocketApi {

    // mocked data
    private int iteration = 40;
    private Double mockDistStepFix = 2.0;
    private Double mockDistStep = null;
    private SpaceCoordinate origin = new SpaceCoordinate(0, 0, 0);
    private double distance;

    private SpaceTelemetry currentTelemetry;

    public RocketApi withBasedTelemetry() {
        currentTelemetry = new SpaceTelemetry().heatShield(100).speed(0).distance(0).irradiance(10).velocityVariation(15)
                .temperature(50).vibration(40).boosterRGA(30).midRocketRGA(35);
        return this;
    }

    public RocketApi withOriginCoordinate(SpaceCoordinate origin) {
        this.origin = origin;
        return this;
    }

    public RocketApi withNumberOfIteration(int number) {
        this.iteration = number;
        return this;
    }

    public SpaceTelemetry launchWhenReady(SpaceCoordinate objectiveCoordinates, String id)
            throws NoObjectiveSettedException {
        if (objectiveCoordinates == null) {
            throw new NoObjectiveSettedException(id);
        }
        this.distance = computeDistance(origin, objectiveCoordinates);
        this.mockDistStep = mockDistStepFix;
        return currentTelemetry.totalDistance(distance).distance(distance);
    }

    private boolean isRocketNotPassedMaxQ() {
        if (currentTelemetry.getDistance() >= currentTelemetry.getTotalDistance() - MaxQ.MAX) {
            return true;
        }
        return false;
    }

    public SpaceTelemetry retrieveLastTelemetry() {
        if (this.mockDistStep == null) {
            return currentTelemetry;
        }
        if (isRocketNotPassedMaxQ()) {
            this.mockDistStep = this.mockDistStepFix;
        } else {
            this.mockDistStep = currentTelemetry.getSpeed();
        }
        return nextTelemetry();
    }

    private int computeDistance(SpaceCoordinate from, SpaceCoordinate to) {
        return (int) Math.round(
                Math.sqrt(pow2(from.getX(), to.getX()) + pow2(from.getY(), to.getY()) + pow2(from.getZ(), to.getZ())));
    }

    private double pow2(int a1, int a2) {
        return Math.pow(a1 - a2, 2);
    }

    public SpaceTelemetry nextTelemetry() {
        double newDistance = currentTelemetry.getDistance();
        newDistance -= mockDistStep;
        currentTelemetry = new SpaceTelemetry().heatShield(RandomUtils.randomDouble(5, currentTelemetry.getHeatShield()))
                .distance(newDistance < 1 ? 0 : newDistance).totalDistance(currentTelemetry.getTotalDistance())
                .speed(RandomUtils.randomDouble(4, currentTelemetry.getSpeed()))
                .irradiance(RandomUtils.randomInt(10, currentTelemetry.getIrradiance()))
                .velocityVariation(RandomUtils.randomInt(10, currentTelemetry.getVelocityVariation()))
                .temperature(RandomUtils.randomInt(50, currentTelemetry.getTemperature()))
                .vibration(RandomUtils.randomInt(5, currentTelemetry.getVibration()))
                .boosterRGA(RandomUtils.randomInt(10, currentTelemetry.getBoosterRGA()))
                .midRocketRGA(RandomUtils.randomInt(10, currentTelemetry.getMidRocketRGA()));

        return this.currentTelemetry;
    }

    public SpaceTelemetry getCurrentTelemetry() {
        return currentTelemetry;
    }

}
