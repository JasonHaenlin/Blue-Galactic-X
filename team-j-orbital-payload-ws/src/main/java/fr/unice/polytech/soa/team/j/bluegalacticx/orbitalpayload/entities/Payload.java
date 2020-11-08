package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities;

import java.util.Objects;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadRequest.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;

public class Payload {

    private String payloadId;
    private String missionId;
    private PayloadStatus status;
    private PayloadAPI api;
    private boolean deployed = false;

    public Payload() {
    }

    public Payload(String payloadId, String missionId, PayloadStatus status, PayloadAPI api, boolean deployed) {
        this.payloadId = payloadId;
        this.missionId = missionId;
        this.status = status;
        this.api = api;
        this.deployed = deployed;
    }

    public int getX() {
        return this.api.getPosition().getX();
    }

    public int getY() {
        return this.api.getPosition().getY();
    }

    public int getZ() {
        return this.api.getPosition().getZ();
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

    public PayloadAPI getApi() {
        return this.api;
    }

    public void setApi(PayloadAPI api) {
        this.api = api;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public boolean getDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
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
        this.api = new PayloadAPI(position);
        return this;
    }

    public Payload position(int x, int y, int z) {
        this.api = new PayloadAPI(SpaceCoordinate.newBuilder().setX(x).setY(y).setZ(z).build());
        return this;
    }

    public Payload api(PayloadAPI api) {
        this.api = api;
        return this;
    }

    public Payload deployed(boolean deployed) {
        this.deployed = deployed;
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
                && Objects.equals(status, payload.status) && Objects.equals(api, payload.api)
                && deployed == payload.deployed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(payloadId, missionId, status, api, deployed);
    }

    @Override
    public String toString() {
        return "{" + " payloadId='" + getPayloadId() + "'" + ", missionId='" + getMissionId() + "'" + ", status='"
                + getStatus() + "'" + ", api='" + getApi() + "'" + ", deployed='" + isDeployed() + "'" + "}";
    }

}
