package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

public class RocketMetrics {

    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int groundVibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %

    public RocketMetrics() {
    }

    public RocketMetrics(int irradiance, int velocityVariation, int temperature, int groundVibration, int boosterRGA,
            int midRocketRGA) {
        this.irradiance = irradiance;
        this.velocityVariation = velocityVariation;
        this.temperature = temperature;
        this.groundVibration = groundVibration;
        this.boosterRGA = boosterRGA;
        this.midRocketRGA = midRocketRGA;
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

    public int getGroundVibration() {
        return this.groundVibration;
    }

    public void setGroundVibration(int groundVibration) {
        this.groundVibration = groundVibration;
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

    public RocketMetrics irradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public RocketMetrics velocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public RocketMetrics temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public RocketMetrics groundVibration(int groundVibration) {
        this.groundVibration = groundVibration;
        return this;
    }

    public RocketMetrics boosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public RocketMetrics midRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RocketMetrics)) {
            return false;
        }
        RocketMetrics rocketMetrics = (RocketMetrics) o;
        return irradiance == rocketMetrics.irradiance && velocityVariation == rocketMetrics.velocityVariation
                && temperature == rocketMetrics.temperature && groundVibration == rocketMetrics.groundVibration
                && boosterRGA == rocketMetrics.boosterRGA && midRocketRGA == rocketMetrics.midRocketRGA;
    }

    @Override
    public int hashCode() {
        return Objects.hash(irradiance, velocityVariation, temperature, groundVibration, boosterRGA, midRocketRGA);
    }

    @Override
    public String toString() {
        return "{" + " irradiance='" + getIrradiance() + "'" + ", velocityVariation='" + getVelocityVariation() + "'"
                + ", temperature='" + getTemperature() + "'" + ", groundVibration='" + getGroundVibration() + "'"
                + ", boosterRGA='" + getBoosterRGA() + "'" + ", midRocketRGA='" + getMidRocketRGA() + "'" + "}";
    }

    @Deprecated
    public String addAsciiArt() {
        return "\t        |\n" + "\t       / \\\n" + "\t      / _ \\\n" + "\t     |.o '.|\n" + "\t     |'._.'|\n"
                + "\t     |     |\n" + "\t   ,'|  |  |`.\n" + "\t  /  |  |  |  \\\n" + "\t  |,-'--|--'-.|\n\n";
    }

}
