package fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Payload {

    private PayloadType type;
    private PayloadStatus status;
    private SpaceCoordinate destination;
    private SpaceCoordinate position;
    private int weight; //kg

    //TODO autogenerate id and date when using database based persistence
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;


    public Payload() {
    }

    public Payload(PayloadType type, PayloadStatus status, SpaceCoordinate destination, SpaceCoordinate position, int weight, String id, Date date) {
        this.type = type;
        this.status = status;
        this.destination = destination;
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

    public PayloadStatus getStatus() {
        return this.status;
    }

    public void setStatus(PayloadStatus status) {
        this.status = status;
    }

    public SpaceCoordinate getDestination() {
        return this.destination;
    }

    public void setDestination(SpaceCoordinate destination) {
        this.destination = destination;
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

    public Payload status(PayloadStatus status) {
        this.status = status;
        return this;
    }

    public Payload destination(SpaceCoordinate destination) {
        this.destination = destination;
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
        return Objects.equals(type, payload.type) && Objects.equals(status, payload.status) && Objects.equals(destination, payload.destination) && Objects.equals(position, payload.position) && weight == payload.weight && Objects.equals(id, payload.id) && Objects.equals(date, payload.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, status, destination, position, weight, id, date);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", destination='" + getDestination() + "'" +
            ", position='" + getPosition() + "'" +
            ", weight='" + getWeight() + "'" +
            ", id='" + getId() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
    
}
