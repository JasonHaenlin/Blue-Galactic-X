package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "boosterTelemetry")
public class TelemetryBoosterData {

    private int fuel;
    private String boosterId;
    private String rocketId;
    private BoosterStatus boosterStatus;
    private double distanceFromEarth;
    private double speed;

    public TelemetryBoosterData() {
    }

    public TelemetryBoosterData(int fuel, String boosterId, String rocketId, BoosterStatus boosterStatus,
            double distanceFromEarth, double speed) {
        this.fuel = fuel;
        this.boosterId = boosterId;
        this.rocketId = rocketId;
        this.boosterStatus = boosterStatus;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public double getDistanceFromEarth() {
        return this.distanceFromEarth;
    }

    public void setDistanceFromEarth(double distance) {
        this.distanceFromEarth = distance;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getBoosterId() {
        return this.boosterId;
    }

    public void setBoosterId(String boosterId) {
        this.boosterId = boosterId;
    }

    public String getRocketID() {
        return this.rocketId;
    }

    public void setRocketID(String rocketId) {
        this.rocketId = rocketId;
    }

    public BoosterStatus getBoosterStatus() {
        return this.boosterStatus;
    }

    public void setBoosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
    }

    public TelemetryBoosterData fuel(int fuel) {
        this.fuel = fuel;
        return this;
    }

    public TelemetryBoosterData boosterId(String boosterId) {
        this.boosterId = boosterId;
        return this;
    }

    public TelemetryBoosterData rocketID(String rocketID) {
        this.rocketId = rocketID;
        return this;
    }

    public TelemetryBoosterData boosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TelemetryBoosterData)) {
            return false;
        }
        TelemetryBoosterData telemetryBoosterData = (TelemetryBoosterData) o;
        return fuel == telemetryBoosterData.fuel && Objects.equals(boosterId, telemetryBoosterData.boosterId)
                && Objects.equals(rocketId, telemetryBoosterData.rocketId)
                && Objects.equals(boosterStatus, telemetryBoosterData.boosterStatus)
                && Objects.equals(distanceFromEarth, telemetryBoosterData.distanceFromEarth)
                && Objects.equals(speed, telemetryBoosterData.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuel, boosterId, rocketId, boosterStatus, distanceFromEarth, speed);
    }

    @Override
    public String toString() {
        return "{" + " fuel='" + getFuel() + "'" + ", boosterId='" + getBoosterId() + "'" + ", rocketId='"
                + getRocketID() + "'" + ", boosterStatus='" + getBoosterStatus() + "'" + ", distanceFromEarth='"
                + getDistanceFromEarth() + "'" + ", speed='" + getSpeed() + "'" + "}";
    }

}
