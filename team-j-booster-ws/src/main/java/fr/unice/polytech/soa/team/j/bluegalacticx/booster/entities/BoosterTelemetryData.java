package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities;

import java.util.Objects;

public class BoosterTelemetryData {
    private int fuel;
    private String boosterId;
    private BoosterStatus boosterStatus;
    private double distanceFromEarth;
    private double speed;


    public BoosterTelemetryData() {
    }

    public BoosterTelemetryData(int fuel, String boosterId, BoosterStatus boosterStatus, double distanceFromEarth, double speed) {
        this.fuel = fuel;
        this.boosterId = boosterId;
        this.boosterStatus = boosterStatus;
        this.distanceFromEarth = distanceFromEarth;
        this.speed = speed;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public String getBoosterId() {
        return this.boosterId;
    }

    public void setBoosterId(String boosterId) {
        this.boosterId = boosterId;
    }

    public BoosterStatus getBoosterStatus() {
        return this.boosterStatus;
    }

    public void setBoosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
    }

    public double getDistanceFromEarth() {
        return this.distanceFromEarth;
    }

    public void setDistanceFromEarth(double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public BoosterTelemetryData fuel(int fuel) {
        this.fuel = fuel;
        return this;
    }

    public BoosterTelemetryData boosterId(String boosterId) {
        this.boosterId = boosterId;
        return this;
    }

    public BoosterTelemetryData boosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
        return this;
    }

    public BoosterTelemetryData distanceFromEarth(double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
        return this;
    }

    public BoosterTelemetryData speed(double speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoosterTelemetryData)) {
            return false;
        }
        BoosterTelemetryData boosterTelemetryData = (BoosterTelemetryData) o;
        return fuel == boosterTelemetryData.fuel && Objects.equals(boosterId, boosterTelemetryData.boosterId) && Objects.equals(boosterStatus, boosterTelemetryData.boosterStatus) && distanceFromEarth == boosterTelemetryData.distanceFromEarth && speed == boosterTelemetryData.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuel, boosterId, boosterStatus, distanceFromEarth, speed);
    }

    @Override
    public String toString() {
        return "{" +
            " fuel='" + getFuel() + "'" +
            ", boosterId='" + getBoosterId() + "'" +
            ", boosterStatus='" + getBoosterStatus() + "'" +
            ", distanceFromEarth='" + getDistanceFromEarth() + "'" +
            ", speed='" + getSpeed() + "'" +
            "}";
    }

}
