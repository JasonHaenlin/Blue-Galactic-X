package fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Payload {

    private PayloadType type;
    private String rocketId;
    private PayloadStatus status;
    private SpaceCoordinate position;
    private int weight; // kg

    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Payload() {
    }

    public Payload(PayloadType type, String rocketId, PayloadStatus status, SpaceCoordinate position, int weight,
            String id, Date date) {
        this.type = type;
        this.rocketId = rocketId;
        this.status = status;
        this.position = position;
        this.weight = weight;
        this.id = id;
        this.date = date;
    }

    public PayloadType getType() {
        return this.type;
    }

    public void setType(PayloadType type) {
        this.type = type;
    }

    public String getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payload type(PayloadType type) {
        this.type = type;
        return this;
    }

    public Payload rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public Payload status(PayloadStatus status) {
        this.status = status;
        return this;
    }

    public Payload position(SpaceCoordinate position) {
        this.position = position;
        return this;
    }

    public Payload weight(int weight) {
        this.weight = weight;
        return this;
    }

    public Payload id(String id) {
        this.id = id;
        return this;
    }

    public Payload date(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Payload)) {
            return false;
        }
        Payload payload = (Payload) o;
        return Objects.equals(type, payload.type) && Objects.equals(rocketId, payload.rocketId)
                && Objects.equals(status, payload.status) && Objects.equals(position, payload.position)
                && weight == payload.weight && Objects.equals(id, payload.id) && Objects.equals(date, payload.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rocketId, status, position, weight, id, date);
    }

    @Override
    public String toString() {
        return "{" + " type='" + getType() + "'" + ", rocketId='" + getRocketId() + "'" + ", status='" + getStatus()
                + "'" + ", position='" + getPosition() + "'" + ", weight='" + getWeight() + "'" + ", id='" + getId()
                + "'" + ", date='" + getDate() + "'" + "}";
    }

}
