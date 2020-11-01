package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rocketTelemetry")
public class TelemetryRocketData {

    @Id
    private String id;

    private String rocketId;
    private int transactionCount;
    private List<RocketMeasurements> measurements = new ArrayList<>();

    public TelemetryRocketData() {
    }

    public TelemetryRocketData(String id, String rocketId, int transactionCount,
            List<RocketMeasurements> measurements) {
        this.id = id;
        this.rocketId = rocketId;
        this.transactionCount = transactionCount;
        this.measurements = measurements;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<RocketMeasurements> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<RocketMeasurements> measurements) {
        this.measurements = measurements;
    }

    public TelemetryRocketData id(String id) {
        this.id = id;
        return this;
    }

    public TelemetryRocketData rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public TelemetryRocketData transactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
        return this;
    }

    public TelemetryRocketData measurements(List<RocketMeasurements> measurements) {
        this.measurements = measurements;
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
        return Objects.equals(id, telemetryRocketData.id) && Objects.equals(rocketId, telemetryRocketData.rocketId)
                && transactionCount == telemetryRocketData.transactionCount
                && Objects.equals(measurements, telemetryRocketData.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rocketId, transactionCount, measurements);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", rocketId='" + getRocketId() + "'" + ", transactionCount='"
                + getTransactionCount() + "'" + ", measurements='" + getMeasurements() + "'" + "}";
    }

}
