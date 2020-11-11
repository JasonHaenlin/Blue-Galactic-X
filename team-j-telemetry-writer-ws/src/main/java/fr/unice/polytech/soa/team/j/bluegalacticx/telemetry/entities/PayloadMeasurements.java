package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.time.ZonedDateTime;
import java.util.Objects;

public class PayloadMeasurements {

    private String type;
    private String status;
    private SpaceCoordinate position;
    private int weight; // kg
    private ZonedDateTime time;

    public PayloadMeasurements() {
    }

    public PayloadMeasurements(String type, String status, SpaceCoordinate position, int weight, ZonedDateTime time) {
        this.type = type;
        this.status = status;
        this.position = position;
        this.weight = weight;
        this.time = time;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
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

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public PayloadMeasurements type(String type) {
        this.type = type;
        return this;
    }

    public PayloadMeasurements status(String status) {
        this.status = status;
        return this;
    }

    public PayloadMeasurements position(SpaceCoordinate position) {
        this.position = position;
        return this;
    }

    public PayloadMeasurements weight(int weight) {
        this.weight = weight;
        return this;
    }

    public PayloadMeasurements time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PayloadMeasurements)) {
            return false;
        }
        PayloadMeasurements payloadMeasurements = (PayloadMeasurements) o;
        return Objects.equals(type, payloadMeasurements.type) && Objects.equals(status, payloadMeasurements.status)
                && Objects.equals(position, payloadMeasurements.position) && weight == payloadMeasurements.weight
                && Objects.equals(time, payloadMeasurements.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, status, position, weight, time);
    }

    @Override
    public String toString() {
        return "{" + " type='" + getType() + "'" + ", status='" + getStatus() + "'" + ", position='" + getPosition()
                + "'" + ", weight='" + getWeight() + "'" + ", time='" + getTime() + "'" + "}";
    }

}
