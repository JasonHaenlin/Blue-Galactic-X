package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.payload.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.payload.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.payload.SpaceCoordinate;

@Document(collection = "payloadTelemetry")
public class TelemetryPayloadData {

    @Id
    private String payloadId;
    private PayloadType type;
    private PayloadStatus status;
    private SpaceCoordinate position;
    private int weight; // kg
    private Date date;

    public TelemetryPayloadData() {
    }

    public TelemetryPayloadData(String payloadId, PayloadType type, PayloadStatus status, SpaceCoordinate position,
            int weight, Date date) {
        this.payloadId = payloadId;
        this.type = type;
        this.status = status;
        this.position = position;
        this.weight = weight;
        this.date = date;
    }

    public String getPayloadId() {
        return this.payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public PayloadType getType() {
        return this.type;
    }

    public void setType(PayloadType type) {
        this.type = type;
    }

    public PayloadStatus getStatus() {
        return this.status;
    }

    public void setStatus(PayloadStatus status) {
        this.status = status;
    }

    public SpaceCoordinate getPosition() {
        return this.position;
    }

    public void setPosition(SpaceCoordinate position) {
        this.position = position;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TelemetryPayloadData payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public TelemetryPayloadData type(PayloadType type) {
        this.type = type;
        return this;
    }

    public TelemetryPayloadData status(PayloadStatus status) {
        this.status = status;
        return this;
    }

    public TelemetryPayloadData position(SpaceCoordinate position) {
        this.position = position;
        return this;
    }

    public TelemetryPayloadData weight(int weight) {
        this.weight = weight;
        return this;
    }

    public TelemetryPayloadData date(Date date) {
        this.date = date;
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
        return Objects.equals(payloadId, telemetryPayloadData.payloadId)
                && Objects.equals(type, telemetryPayloadData.type)
                && Objects.equals(status, telemetryPayloadData.status)
                && Objects.equals(position, telemetryPayloadData.position) && weight == telemetryPayloadData.weight
                && Objects.equals(date, telemetryPayloadData.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payloadId, type, status, position, weight, date);
    }

    @Override
    public String toString() {
        return "{" + " payloadId='" + getPayloadId() + "'" + ", type='" + getType() + "'" + ", status='" + getStatus()
                + "'" + ", position='" + getPosition() + "'" + ", weight='" + getWeight() + "'" + ", date='" + getDate()
                + "'" + "}";
    }

}
