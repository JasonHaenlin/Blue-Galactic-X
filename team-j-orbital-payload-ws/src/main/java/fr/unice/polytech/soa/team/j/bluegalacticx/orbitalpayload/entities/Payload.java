package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities;

import java.util.Objects;

public class Payload {

    private String payloadId;
    private String missionId;
    private PayloadStatus status;
    private SpaceCoordinate position;

    public Payload() {
    }

    public Payload(String payloadId, String missionId, PayloadStatus status, SpaceCoordinate position) {
        this.payloadId = payloadId;
        this.missionId = missionId;
        this.status = status;
        this.position = position;
    }

    public String getPayloadId() {
        return this.payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getMissionId() {
        return this.missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
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

    public Payload payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public Payload missionId(String missionId) {
        this.missionId = missionId;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Payload)) {
            return false;
        }
        Payload payload = (Payload) o;
        return Objects.equals(payloadId, payload.payloadId) && Objects.equals(missionId, payload.missionId)
                && Objects.equals(status, payload.status) && Objects.equals(position, payload.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payloadId, missionId, status, position);
    }

    @Override
    public String toString() {
        return "{" + " payloadId='" + getPayloadId() + "'" + ", missionId='" + getMissionId() + "'" + ", status='"
                + getStatus() + "'" + ", position='" + getPosition() + "'" + "}";
    }

}
