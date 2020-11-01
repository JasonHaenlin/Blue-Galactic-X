package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "boosterTelemetry")
public class TelemetryBoosterData {

    @Id
    private String id;

    private String boosterId;
    private int transactionCount;
    private List<BoosterMeasurements> measurements = new ArrayList<>();

    public TelemetryBoosterData() {
    }

    public TelemetryBoosterData(String boosterId) {
        this.boosterId = boosterId;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoosterId() {
        return this.boosterId;
    }

    public void setBoosterId(String boosterId) {
        this.boosterId = boosterId;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<BoosterMeasurements> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<BoosterMeasurements> measurements) {
        this.measurements = measurements;
    }

    public TelemetryBoosterData id(String id) {
        this.id = id;
        return this;
    }

    public TelemetryBoosterData boosterId(String boosterId) {
        this.boosterId = boosterId;
        return this;
    }

    public TelemetryBoosterData transactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
        return this;
    }

    public TelemetryBoosterData measurements(List<BoosterMeasurements> measurements) {
        this.measurements = measurements;
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
        return Objects.equals(id, telemetryBoosterData.id) && Objects.equals(boosterId, telemetryBoosterData.boosterId)
                && transactionCount == telemetryBoosterData.transactionCount
                && Objects.equals(measurements, telemetryBoosterData.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boosterId, transactionCount, measurements);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", boosterId='" + getBoosterId() + "'" + ", transactionCount='"
                + getTransactionCount() + "'" + ", measurements='" + getMeasurements().toString() + "'" + "}";
    }

}
