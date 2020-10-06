package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

public class RocketReport {
    private int fuelLevel;
    private int remainingEstimatedTime;
    private Engine engine;
    private String overallResult;

    public RocketReport() {
    }

    public RocketReport(int fuelLevel, int remainingEstimatedTime, Engine engine, String overallResult) {
        this.fuelLevel = fuelLevel;
        this.remainingEstimatedTime = remainingEstimatedTime;
        this.engine = engine;
        this.overallResult = overallResult;
    }

    public int getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public int getRemainingEstimatedTime() {
        return this.remainingEstimatedTime;
    }

    public void setRemainingEstimatedTime(int remainingEstimatedTime) {
        this.remainingEstimatedTime = remainingEstimatedTime;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getOverallResult() {
        return this.overallResult;
    }

    public void setOverallResult(String overallResult) {
        this.overallResult = overallResult;
    }

    public RocketReport fuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
        return this;
    }

    public RocketReport remainingEstimatedTime(int remainingEstimatedTime) {
        this.remainingEstimatedTime = remainingEstimatedTime;
        return this;
    }

    public RocketReport engine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public RocketReport overallResult(String overallResult) {
        this.overallResult = overallResult;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RocketReport)) {
            return false;
        }
        RocketReport rocketReport = (RocketReport) o;
        return fuelLevel == rocketReport.fuelLevel && remainingEstimatedTime == rocketReport.remainingEstimatedTime
                && Objects.equals(engine, rocketReport.engine)
                && Objects.equals(overallResult, rocketReport.overallResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuelLevel, remainingEstimatedTime, engine, overallResult);
    }

    @Override
    public String toString() {
        return "{" + " fuelLevel='" + getFuelLevel() + "'" + ", remainingEstimatedTime='" + getRemainingEstimatedTime()
                + "'" + ", engine='" + getEngine() + "'" + ", overallResult='" + getOverallResult() + "'" + "}";
    }

}
