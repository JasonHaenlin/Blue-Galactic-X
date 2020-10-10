package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

public class SpaceMetrics extends RocketMetrics {
    private String rocketId;
    private double heatShield; // %
    private double speed; // m/s
    private double distance;

    public SpaceMetrics() {
    }

    public SpaceMetrics(String rocketId, double heatShield, double speed, double distance) {
        this.rocketId = rocketId;
        this.heatShield = heatShield;
        this.speed = speed;
        this.distance = distance;
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

    public SpaceMetrics rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public SpaceMetrics heatShield(double heatShield) {
        this.heatShield = heatShield;
        return this;
    }

    public SpaceMetrics speed(double speed) {
        this.speed = speed;
        return this;
    }

    public SpaceMetrics distance(double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SpaceMetrics)) {
            return false;
        }
        SpaceMetrics spaceMetrics = (SpaceMetrics) o;
        return Objects.equals(rocketId, spaceMetrics.rocketId) && heatShield == spaceMetrics.heatShield
                && speed == spaceMetrics.speed && distance == spaceMetrics.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketId, heatShield, speed, distance);
    }

    @Override
    public String toString() {
        return "{" + " rocketId='" + getRocketId() + "'" + ", heatShield='" + getHeatShield() + "'" + ", speed='"
                + getSpeed() + "'" + ", distance='" + getDistance() + "'" + "}";
    }

}
