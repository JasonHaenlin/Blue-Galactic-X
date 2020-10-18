package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionStatusRequest.MissionStatus;

public class Mission {

    private String id;
    private String rocketId;
    private String payloadId;
    private SpaceCoordinate destination;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private MissionStatus status;

    public Mission() {
    }

    public Mission(String id, String rocketId, String payloadId, SpaceCoordinate destination, Date date,
            MissionStatus status) {
        this.id = id;
        this.rocketId = rocketId;
        this.payloadId = payloadId;
        this.destination = destination;
        this.date = date;
        this.status = status;
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

    public String getPayloadId() {
        return this.payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public SpaceCoordinate getDestination() {
        return this.destination;
    }

    public void setDestination(SpaceCoordinate destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MissionStatus getStatus() {
        return this.status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Mission id(String id) {
        this.id = id;
        return this;
    }

    public Mission rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public Mission payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public Mission destination(SpaceCoordinate destination) {
        this.destination = destination;
        return this;
    }

    public Mission date(Date date) {
        this.date = date;
        return this;
    }

    public Mission status(MissionStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Mission)) {
            return false;
        }
        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id) && Objects.equals(rocketId, mission.rocketId)
                && Objects.equals(payloadId, mission.payloadId) && Objects.equals(destination, mission.destination)
                && Objects.equals(date, mission.date) && Objects.equals(status, mission.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rocketId, payloadId, destination, date, status);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", rocketId='" + getRocketId() + "'" + ", payloadId='" + getPayloadId()
                + "'" + ", destination='" + getDestination() + "'" + ", date='" + getDate() + "'" + ", status='"
                + getStatus() + "'" + "}";
    }

}
