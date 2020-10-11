package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public Mission(String id, String rocketId, String payloadId, SpaceCoordinate destination, Date date) {
        this.id = id;
        this.rocketId = rocketId;
        this.payloadId = payloadId;
        this.destination = destination;
        this.date = date;
        this.status = MissionStatus.PENDING;
    }

    public SpaceCoordinate getDestination() {
        return this.destination;
    }

    public void setDestination(SpaceCoordinate destination) {
        this.destination = destination;
    }

    public Mission destination(SpaceCoordinate destination) {
        this.destination = destination;
        return this;
    }

    public String getPayloadId() {
        return this.payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public MissionStatus getStatus() {
        return this.status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Mission payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public Mission id(String id) {
        this.id = id;
        return this;
    }

    public Mission date(Date date) {
        this.date = date;
        return this;
    }

    public Mission rocketId(String id) {
        this.rocketId = id;
        return this;
    }

    public Mission status(MissionStatus status) {
        this.status = status;
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setRocketId(String rocketId) {
        this.rocketId = rocketId;
    }

    public String getRocketId() {
        return this.rocketId;
    }

    public void setMissionStatus(MissionStatus status) {
        this.status = status;
    }

    public MissionStatus getMissionStatus() {
        return this.status;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(rocketId).append(payloadId).append(payloadId).append(date)
                .append(status).append(destination).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Mission)) {
            return false;
        }
        Mission mission = (Mission) obj;

        return mission.getDate().equals(this.date) && mission.getRocketId().equals(this.rocketId)
                && mission.getMissionStatus() == this.status && mission.getPayloadId() == this.payloadId
                && mission.getDestination() == this.destination;
    }

    @Override
    public String toString() {
        StringBuilder missionText = new StringBuilder();
        missionText.append("rocketId : " + rocketId + ", ");
        missionText.append("payloadId : " + payloadId + ", ");
        missionText.append("Date : " + date.toString());
        missionText.append("Destination: " + destination);
        missionText.append("MissionStatus : " + status.toString());

        return missionText.toString();
    }

}
