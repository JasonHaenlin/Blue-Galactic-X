package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.Booster;

@Document(collection = "rocketTelemetry")
public class TelemetryRocketData {

    @Id
    private String rocketId;
    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int vibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %
    private double heatShield; // %
    private double speed; // m/s
    private double distance;
    private List<Booster> boosters = new ArrayList<>();

    public TelemetryRocketData() {
    }

    public TelemetryRocketData(String rocketId, int irradiance, int velocityVariation, int temperature, int vibration,
            int boosterRGA, int midRocketRGA, double heatShield, double speed, double distance,
            List<Booster> boosters) {
        this.rocketId = rocketId;
        this.irradiance = irradiance;
        this.velocityVariation = velocityVariation;
        this.temperature = temperature;
        this.vibration = vibration;
        this.boosterRGA = boosterRGA;
        this.midRocketRGA = midRocketRGA;
        this.heatShield = heatShield;
        this.speed = speed;
        this.distance = distance;
        this.boosters = boosters;
    }

    public String getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
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

    public List<Booster> getBoosters() {
        return this.boosters;
    }

    public void setBoosters(List<Booster> boosters) {
        this.boosters = boosters;
    }

    public TelemetryRocketData rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public TelemetryRocketData irradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public TelemetryRocketData velocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public TelemetryRocketData temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public TelemetryRocketData vibration(int vibration) {
        this.vibration = vibration;
        return this;
    }

    public TelemetryRocketData boosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public TelemetryRocketData midRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    public TelemetryRocketData heatShield(double heatShield) {
        this.heatShield = heatShield;
        return this;
    }

    public TelemetryRocketData speed(double speed) {
        this.speed = speed;
        return this;
    }

    public TelemetryRocketData distance(double distance) {
        this.distance = distance;
        return this;
    }

    public TelemetryRocketData boosters(List<Booster> boosters) {
        this.boosters = boosters;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TelemetryRocketData)) {
            return false;
        }
        TelemetryRocketData telemetryRocketData = (TelemetryRocketData) o;
        return Objects.equals(rocketId, telemetryRocketData.rocketId) && irradiance == telemetryRocketData.irradiance
                && velocityVariation == telemetryRocketData.velocityVariation
                && temperature == telemetryRocketData.temperature && vibration == telemetryRocketData.vibration
                && boosterRGA == telemetryRocketData.boosterRGA && midRocketRGA == telemetryRocketData.midRocketRGA
                && heatShield == telemetryRocketData.heatShield && speed == telemetryRocketData.speed
                && distance == telemetryRocketData.distance && Objects.equals(boosters, telemetryRocketData.boosters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketId, irradiance, velocityVariation, temperature, vibration, boosterRGA, midRocketRGA,
                heatShield, speed, distance, boosters);
    }

    @Override
    public String toString() {
        return "{" + " rocketId='" + getRocketId() + "'" + ", irradiance='" + getIrradiance() + "'"
                + ", velocityVariation='" + getVelocityVariation() + "'" + ", temperature='" + getTemperature() + "'"
                + ", vibration='" + getVibration() + "'" + ", boosterRGA='" + getBoosterRGA() + "'" + ", midRocketRGA='"
                + getMidRocketRGA() + "'" + ", heatShield='" + getHeatShield() + "'" + ", speed='" + getSpeed() + "'"
                + ", distance='" + getDistance() + "'" + ", boosters='" + getBoosters() + "'" + "}";
    }

}
