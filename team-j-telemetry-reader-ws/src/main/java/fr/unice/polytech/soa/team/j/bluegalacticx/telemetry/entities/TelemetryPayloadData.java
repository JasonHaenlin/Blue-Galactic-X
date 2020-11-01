package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payloadTelemetry")
public class TelemetryPayloadData {

    @Id
    private String id;

    private String payloadId;
    private int transactionCount;
    private List<PayloadMeasurements> measurements = new ArrayList<>();


    public TelemetryPayloadData() {
    }

    public TelemetryPayloadData(String id, String payloadId, int transactionCount, List<PayloadMeasurements> measurements) {
        this.id = id;
        this.payloadId = payloadId;
        this.transactionCount = transactionCount;
        this.measurements = measurements;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayloadId() {
        return this.payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<PayloadMeasurements> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<PayloadMeasurements> measurements) {
        this.measurements = measurements;
    }

    public TelemetryPayloadData id(String id) {
        this.id = id;
        return this;
    }

    public TelemetryPayloadData payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public TelemetryPayloadData transactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
        return this;
    }

    public TelemetryPayloadData measurements(List<PayloadMeasurements> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TelemetryPayloadData)) {
            return false;
        }
        TelemetryPayloadData telemetryPayloadData = (TelemetryPayloadData) o;
        return Objects.equals(id, telemetryPayloadData.id) && Objects.equals(payloadId, telemetryPayloadData.payloadId) && transactionCount == telemetryPayloadData.transactionCount && Objects.equals(measurements, telemetryPayloadData.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payloadId, transactionCount, measurements);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", payloadId='" + getPayloadId() + "'" +
            ", transactionCount='" + getTransactionCount() + "'" +
            ", measurements='" + getMeasurements() + "'" +
            "}";
    }

}
