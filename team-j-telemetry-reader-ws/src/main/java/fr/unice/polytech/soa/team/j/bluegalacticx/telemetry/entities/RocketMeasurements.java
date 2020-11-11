package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.time.ZonedDateTime;
import java.util.Objects;

public class RocketMeasurements {
    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int vibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %
    private double heatShield; // %
    private double speed; // m/s
    private double distance;
    private double totalDistance;
    private ZonedDateTime time;

    public RocketMeasurements() {
    }

    public RocketMeasurements(int irradiance, int velocityVariation, int temperature, int vibration, int boosterRGA,
            int midRocketRGA, double heatShield, double speed, double distance, double totalDistance,
            ZonedDateTime time) {
        this.irradiance = irradiance;
        this.velocityVariation = velocityVariation;
        this.temperature = temperature;
        this.vibration = vibration;
        this.boosterRGA = boosterRGA;
        this.midRocketRGA = midRocketRGA;
        this.heatShield = heatShield;
        this.speed = speed;
        this.distance = distance;
        this.totalDistance = totalDistance;
        this.time = time;
    }

    public int getIrradiance() {
        return this.irradiance;
    }

    public void setIrradiance(int irradiance) {
        this.irradiance = irradiance;
    }

    public int getVelocityVariation() {
        return this.velocityVariation;
    }

    public void setVelocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getVibration() {
        return this.vibration;
    }

    public void setVibration(int vibration) {
        this.vibration = vibration;
    }

    public int getBoosterRGA() {
        return this.boosterRGA;
    }

    public void setBoosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
    }

    public int getMidRocketRGA() {
        return this.midRocketRGA;
    }

    public void setMidRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
    }

    public double getHeatShield() {
        return this.heatShield;
    }

    public void setHeatShield(double heatShield) {
        this.heatShield = heatShield;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTotalDistance() {
        return this.totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public RocketMeasurements irradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public RocketMeasurements velocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public RocketMeasurements temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public RocketMeasurements vibration(int vibration) {
        this.vibration = vibration;
        return this;
    }

    public RocketMeasurements boosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public RocketMeasurements midRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    public RocketMeasurements heatShield(double heatShield) {
        this.heatShield = heatShield;
        return this;
    }

    public RocketMeasurements speed(double speed) {
        this.speed = speed;
        return this;
    }

    public RocketMeasurements distance(double distance) {
        this.distance = distance;
        return this;
    }

    public RocketMeasurements totalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
        return this;
    }

    public RocketMeasurements time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RocketMeasurements)) {
            return false;
        }
        RocketMeasurements rocketMeasurements = (RocketMeasurements) o;
        return irradiance == rocketMeasurements.irradiance && velocityVariation == rocketMeasurements.velocityVariation
                && temperature == rocketMeasurements.temperature && vibration == rocketMeasurements.vibration
                && boosterRGA == rocketMeasurements.boosterRGA && midRocketRGA == rocketMeasurements.midRocketRGA
                && heatShield == rocketMeasurements.heatShield && speed == rocketMeasurements.speed
                && distance == rocketMeasurements.distance && totalDistance == rocketMeasurements.totalDistance
                && Objects.equals(time, rocketMeasurements.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(irradiance, velocityVariation, temperature, vibration, boosterRGA, midRocketRGA, heatShield,
                speed, distance, totalDistance, time);
    }

    @Override
    public String toString() {
        return "{" + " irradiance='" + getIrradiance() + "'" + ", velocityVariation='" + getVelocityVariation() + "'"
                + ", temperature='" + getTemperature() + "'" + ", vibration='" + getVibration() + "'" + ", boosterRGA='"
                + getBoosterRGA() + "'" + ", midRocketRGA='" + getMidRocketRGA() + "'" + ", heatShield='"
                + getHeatShield() + "'" + ", speed='" + getSpeed() + "'" + ", distance='" + getDistance() + "'"
                + ", totalDistance='" + getTotalDistance() + "'" + ", time='" + getTime() + "'" + "}";
    }

}
