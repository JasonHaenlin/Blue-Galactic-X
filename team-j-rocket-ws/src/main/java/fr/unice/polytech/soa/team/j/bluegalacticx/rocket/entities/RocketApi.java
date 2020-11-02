package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.utils.RandomUtils;

@Service
public class RocketApi {

    // mocked data
    private int iteration = 40;
    private Double mockDistStepFix = 2.0;
    private Double mockDistStep = null;
    private SpaceCoordinate origin = new SpaceCoordinate(0, 0, 0);
    private double distance;

    private SpaceTelemetry airTelemetry;
    private SpaceTelemetry groundTelemetry;

    public RocketApi withBasedTelemetry() {
        airTelemetry = new SpaceTelemetry().heatShield(95).speed(950).distance(500).irradiance(10).velocityVariation(15)
                .temperature(200).vibration(30).boosterRGA(30).midRocketRGA(35);
        groundTelemetry = new SpaceTelemetry().heatShield(100).speed(0).distance(0).irradiance(10).velocityVariation(15)
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

    public SpaceTelemetry launchWhenReady(SpaceCoordinate objectiveCoordinates) {
        this.distance = computeDistance(origin, objectiveCoordinates);
        this.mockDistStep = mockDistStepFix;
        return airTelemetry.totalDistance(distance).distance(distance);
    }

    private boolean isRocketNotPassedMaxQ() {
        if (airTelemetry.getDistance() >= airTelemetry.getTotalDistance() - MaxQ.MAX) {
            return true;
        }
        return false;
    }

    public SpaceTelemetry retrieveLastTelemetry() {
        if (this.mockDistStep == null) {
            return groundTelemetry;
        }
        if (isRocketNotPassedMaxQ()) {
            this.mockDistStep = this.mockDistStepFix;
        } else {
            this.mockDistStep = distance / this.iteration;
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
        double newDistance = airTelemetry.getDistance();
        newDistance -= mockDistStep;
        airTelemetry = new SpaceTelemetry().heatShield(RandomUtils.randomDouble(5, airTelemetry.getHeatShield()))
                .distance(newDistance < 1 ? 0 : newDistance).totalDistance(airTelemetry.getTotalDistance())
                .speed(RandomUtils.randomDouble(20, airTelemetry.getSpeed()))
                .irradiance(RandomUtils.randomInt(10, airTelemetry.getIrradiance()))
                .velocityVariation(RandomUtils.randomInt(10, airTelemetry.getVelocityVariation()))
                .temperature(RandomUtils.randomInt(50, airTelemetry.getTemperature()))
                .vibration(RandomUtils.randomInt(5, airTelemetry.getVibration()))
                .boosterRGA(RandomUtils.randomInt(10, airTelemetry.getBoosterRGA()))
                .midRocketRGA(RandomUtils.randomInt(10, airTelemetry.getMidRocketRGA()));

        return this.airTelemetry;
    }

    public SpaceTelemetry getAirTelemetry() {
        return airTelemetry;
    }

}
