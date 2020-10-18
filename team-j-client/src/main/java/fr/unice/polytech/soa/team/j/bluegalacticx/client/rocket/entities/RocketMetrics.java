package fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RocketMetrics {

    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int vibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %mvn te
    private List<Booster> boosters = new ArrayList<>();

    public RocketMetrics() {
    }

    public RocketMetrics(int irradiance, int velocityVariation, int temperature, int vibration, int boosterRGA,
            int midRocketRGA, List<Booster> boosters) {
        this.irradiance = irradiance;
        this.velocityVariation = velocityVariation;
        this.temperature = temperature;
        this.vibration = vibration;
        this.boosterRGA = boosterRGA;
        this.midRocketRGA = midRocketRGA;
        this.boosters = boosters;
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

    public List<Booster> getBoosters() {
        return this.boosters;
    }

    public void setBoosters(List<Booster> boosters) {
        this.boosters = boosters;
    }

    public RocketMetrics addBooster(Booster booster) {
        this.boosters.add(booster);
        return this;
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

    public RocketMetrics vibration(int vibration) {
        this.vibration = vibration;
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

    public RocketMetrics boosters(List<Booster> boosters) {
        this.boosters = boosters;
        return this;
    }

    public Booster retrieveActiveBooster() {
        for (Booster booster : boosters) {
            if (booster.getFuelLevel() > 0 && booster.getStatus() == BoosterStatus.RUNNING) {
                return booster;
            }
        }
        return null;
    }

    public Booster retrieveLastBooster() {
        for (Booster booster : boosters) {
            if (booster.getStatus() == BoosterStatus.RUNNING) {
                return booster;
            }
        }
        return null;
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
                && temperature == rocketMetrics.temperature && vibration == rocketMetrics.vibration
                && boosterRGA == rocketMetrics.boosterRGA && midRocketRGA == rocketMetrics.midRocketRGA
                && Objects.equals(boosters, rocketMetrics.boosters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(irradiance, velocityVariation, temperature, vibration, boosterRGA, midRocketRGA, boosters);
    }

    @Override
    public String toString() {
        return "{" + " irradiance='" + getIrradiance() + "'" + ", velocityVariation='" + getVelocityVariation() + "'"
                + ", temperature='" + getTemperature() + "'" + ", vibration='" + getVibration() + "'" + ", boosterRGA='"
                + getBoosterRGA() + "'" + ", midRocketRGA='" + getMidRocketRGA() + "'" + ", boosters='" + getBoosters()
                + "'" + "}";
    }

    @Deprecated
    public String addAsciiArt() {
        return "\t        |\n" + "\t       / \\\n" + "\t      / _ \\\n" + "\t     |.o '.|\n" + "\t     |'._.'|\n"
                + "\t     |     |\n" + "\t   ,'|  |  |`.\n" + "\t  /  |  |  |  \\\n" + "\t  |,-'--|--'-.|\n\n";
    }

}
