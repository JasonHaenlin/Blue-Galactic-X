package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks;

import java.util.concurrent.ThreadLocalRandom;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;

public class SpaceMetricsMocked {

    static public SpaceMetrics inAir;
    static public SpaceMetrics onGround;

    static {
        preset();
    }

    public static void preset() {
        // @formatter:off
        inAir = (SpaceMetrics) new SpaceMetrics()
                    .heatShield(95)
                    .speed(950)
                    .distance(500)
                    .irradiance(10)
                    .velocityVariation(15)
                    .temperature(200)
                    .vibration(30)
                    .boosterRGA(30)
                    .midRocketRGA(35)
                    .fuelLevel(100);

                    onGround = (SpaceMetrics) new SpaceMetrics()
                    .heatShield(100)
                    .speed(0)
                    .distance(0)
                    .irradiance(10)
                    .velocityVariation(15)
                    .temperature(50)
                    .vibration(40)
                    .boosterRGA(30)
                    .midRocketRGA(35)
                    .fuelLevel(100);
        // @formatter:on
    }

    public static final SpaceMetrics nextMetrics(double distStep, Double fuelStep) {
        double newDistance = inAir.getDistance() - distStep;
        int newFuelLevel = (int) Math.round(inAir.getFuelLevel() - (fuelStep * 0.9));
        // @formatter:off
        return (SpaceMetrics) inAir
                .heatShield(randomDouble(5, inAir.getHeatShield()))
                .distance(newDistance <0 ? 0 : newDistance)
                .speed(randomDouble(20, inAir.getSpeed()))
                .irradiance(randomInt(10, inAir.getIrradiance()))
                .velocityVariation(randomInt(10, inAir.getVelocityVariation()))
                .temperature(randomInt(50, inAir.getTemperature()))
                .vibration(randomInt(5, inAir.getVibration()))
                .boosterRGA(randomInt(10, inAir.getBoosterRGA()))
                .midRocketRGA(randomInt(10, inAir.getMidRocketRGA()))
                .fuelLevel(newFuelLevel < 0 ? 0 : newFuelLevel);
        // @formatter:on
    }

    private static int randomInt(int var, int base) {
        return randomInt(-var, var, base);
    }

    private static int randomInt(int min, int max, int base) {
        return base + ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static double randomDouble(double var, double base) {
        return randomDouble(-var, var, base);
    }

    private static double randomDouble(double min, double max, double base) {
        return base + ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

}