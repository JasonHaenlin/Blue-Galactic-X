package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

public class SpaceTelemetry {

    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int vibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %mvn te
    private String rocketId;
    private double heatShield; // %
    private double speed; // m/s
    private double distance;
    private double totalDistance;

    public SpaceTelemetry() {
    }

    public SpaceTelemetry(int irradiance, int velocityVariation, int temperature, int vibration, int boosterRGA, int midRocketRGA, String rocketId, double heatShield, double speed, double distance, double totalDistance) {
        this.irradiance = irradiance;
        this.velocityVariation = velocityVariation;
        this.temperature = temperature;
        this.vibration = vibration;
        this.boosterRGA = boosterRGA;
        this.midRocketRGA = midRocketRGA;
        this.rocketId = rocketId;
        this.heatShield = heatShield;
        this.speed = speed;
        this.distance = distance;
        this.totalDistance = totalDistance;
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

    public String getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
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

    public SpaceTelemetry irradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public SpaceTelemetry velocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public SpaceTelemetry temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public SpaceTelemetry vibration(int vibration) {
        this.vibration = vibration;
        return this;
    }

    public SpaceTelemetry boosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public SpaceTelemetry midRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    public SpaceTelemetry rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public SpaceTelemetry heatShield(double heatShield) {
        this.heatShield = heatShield;
        return this;
    }

    public SpaceTelemetry speed(double speed) {
        this.speed = speed;
        return this;
    }

    public SpaceTelemetry distance(double distance) {
        this.distance = distance;
        return this;
    }

    public SpaceTelemetry totalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SpaceTelemetry)) {
            return false;
        }
        SpaceTelemetry rocketTelemetry = (SpaceTelemetry) o;
        return irradiance == rocketTelemetry.irradiance && velocityVariation == rocketTelemetry.velocityVariation && temperature == rocketTelemetry.temperature && vibration == rocketTelemetry.vibration && boosterRGA == rocketTelemetry.boosterRGA && midRocketRGA == rocketTelemetry.midRocketRGA && Objects.equals(rocketId, rocketTelemetry.rocketId) && heatShield == rocketTelemetry.heatShield && speed == rocketTelemetry.speed && distance == rocketTelemetry.distance && totalDistance == rocketTelemetry.totalDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(irradiance, velocityVariation, temperature, vibration, boosterRGA, midRocketRGA, rocketId, heatShield, speed, distance, totalDistance);
    }

    @Override
    public String toString() {
        return "{" +
            " irradiance='" + getIrradiance() + "'" +
            ", velocityVariation='" + getVelocityVariation() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", vibration='" + getVibration() + "'" +
            ", boosterRGA='" + getBoosterRGA() + "'" +
            ", midRocketRGA='" + getMidRocketRGA() + "'" +
            ", rocketId='" + getRocketId() + "'" +
            ", heatShield='" + getHeatShield() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", distance='" + getDistance() + "'" +
            ", totalDistance='" + getTotalDistance() + "'" +
            "}";
    }
  
    @Deprecated
    public String addAsciiArt() {
        return "\t        |\n" + "\t       / \\\n" + "\t      / _ \\\n" + "\t     |.o '.|\n" + "\t     |'._.'|\n"
                + "\t     |     |\n" + "\t   ,'|  |  |`.\n" + "\t  /  |  |  |  \\\n" + "\t  |,-'--|--'-.|\n\n";
    }

}
