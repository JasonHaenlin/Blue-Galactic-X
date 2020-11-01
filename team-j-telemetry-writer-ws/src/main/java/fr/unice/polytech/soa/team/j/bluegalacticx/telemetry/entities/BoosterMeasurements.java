package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.time.ZonedDateTime;
import java.util.Objects;

public class BoosterMeasurements {

    private int fuel;
    private String rocketId;
    private String boosterStatus;
    private double distanceFromEarth;
    private double speed;
    private ZonedDateTime time;

    public BoosterMeasurements() {
    }

    public BoosterMeasurements(int fuel, String rocketId, String boosterStatus, double distanceFromEarth, double speed,
            ZonedDateTime time) {
        this.fuel = fuel;
        this.rocketId = rocketId;
        this.boosterStatus = boosterStatus;
        this.distanceFromEarth = distanceFromEarth;
        this.speed = speed;
        this.time = time;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public String getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public String getBoosterStatus() {
        return this.boosterStatus;
    }

    public void setBoosterStatus(String boosterStatus) {
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

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public BoosterMeasurements fuel(int fuel) {
        this.fuel = fuel;
        return this;
    }

    public BoosterMeasurements rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public BoosterMeasurements boosterStatus(String boosterStatus) {
        this.boosterStatus = boosterStatus;
        return this;
    }

    public BoosterMeasurements distanceFromEarth(double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
        return this;
    }

    public BoosterMeasurements speed(double speed) {
        this.speed = speed;
        return this;
    }

    public BoosterMeasurements time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoosterMeasurements)) {
            return false;
        }
        BoosterMeasurements boosterMeasurements = (BoosterMeasurements) o;
        return fuel == boosterMeasurements.fuel && Objects.equals(rocketId, boosterMeasurements.rocketId)
                && Objects.equals(boosterStatus, boosterMeasurements.boosterStatus)
                && distanceFromEarth == boosterMeasurements.distanceFromEarth && speed == boosterMeasurements.speed
                && Objects.equals(time, boosterMeasurements.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuel, rocketId, boosterStatus, distanceFromEarth, speed, time);
    }

    @Override
    public String toString() {
        return "{" + " fuel='" + getFuel() + "'" + ", rocketId='" + getRocketId() + "'" + ", boosterStatus='"
                + getBoosterStatus() + "'" + ", distanceFromEarth='" + getDistanceFromEarth() + "'" + ", speed='"
                + getSpeed() + "'" + ", time='" + getTime() + "'" + "}";
    }

}
