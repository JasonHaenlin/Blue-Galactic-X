package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities;

import java.util.Objects;

public class RulesMissionStatus {

    private BoosterStatus boosterStatus;
    private PayloadStatus payloadStatus;
    private RocketStatus rocketStatus;

    public RulesMissionStatus() {
    }

    public RulesMissionStatus(BoosterStatus boosterStatus, PayloadStatus payloadStatus, RocketStatus rocketStatus) {
        this.boosterStatus = boosterStatus;
        this.payloadStatus = payloadStatus;
        this.rocketStatus = rocketStatus;
    }

    public void updateMissionStatus(Mission mission) {
        if(rocketStatus == RocketStatus.DAMAGED){
            mission.setStatus(MissionStatus.ABORTED);
        }else if (boosterStatus == BoosterStatus.LANDED && payloadStatus == PayloadStatus.DELIVERED
                && rocketStatus == RocketStatus.ARRIVED) {
            mission.setStatus(MissionStatus.SUCCESSFUL);
        } else if (boosterStatus == BoosterStatus.PENDING && payloadStatus == PayloadStatus.WAITING_FOR_MISSION
                && (rocketStatus == RocketStatus.NOT_READY_FOR_LAUNCH || rocketStatus == RocketStatus.AT_BASE)) {
            mission.setStatus(MissionStatus.PENDING);
        } else if (boosterStatus == BoosterStatus.DESTROYED || payloadStatus == PayloadStatus.DESTROYED
                || payloadStatus == PayloadStatus.NOT_DELIVERED || rocketStatus == RocketStatus.DESTROYED) {
            mission.setStatus(MissionStatus.FAILED);
        } else if (boosterStatus == BoosterStatus.RUNNING && payloadStatus == PayloadStatus.ON_MISSION
                && rocketStatus == RocketStatus.IN_SERVICE) {
            mission.setStatus(MissionStatus.STARTED);
        } else if (boosterStatus == BoosterStatus.READY && rocketStatus == RocketStatus.READY_FOR_LAUNCH) {
            mission.setStatus(MissionStatus.READY);
        }
    }

    public BoosterStatus getBoosterStatus() {
        return this.boosterStatus;
    }

    public void setBoosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
    }

    public PayloadStatus getPayloadStatus() {
        return this.payloadStatus;
    }

    public void setPayloadStatus(PayloadStatus payloadStatus) {
        this.payloadStatus = payloadStatus;
    }

    public RocketStatus getRocketStatus() {
        return this.rocketStatus;
    }

    public void setRocketStatus(RocketStatus rocketStatus) {
        this.rocketStatus = rocketStatus;
    }

    public RulesMissionStatus boosterStatus(BoosterStatus boosterStatus) {
        this.boosterStatus = boosterStatus;
        return this;
    }

    public RulesMissionStatus payloadStatus(PayloadStatus payloadStatus) {
        this.payloadStatus = payloadStatus;
        return this;
    }

    public RulesMissionStatus rocketStatus(RocketStatus rocketStatus) {
        this.rocketStatus = rocketStatus;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RulesMissionStatus)) {
            return false;
        }
        RulesMissionStatus rulesMissionStatus = (RulesMissionStatus) o;
        return Objects.equals(boosterStatus, rulesMissionStatus.boosterStatus)
                && Objects.equals(payloadStatus, rulesMissionStatus.payloadStatus)
                && Objects.equals(rocketStatus, rulesMissionStatus.rocketStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boosterStatus, payloadStatus, rocketStatus);
    }

    @Override
    public String toString() {
        return "{" + " boosterStatus='" + getBoosterStatus() + "'" + ", payloadStatus='" + getPayloadStatus() + "'"
                + ", rocketStatus='" + getRocketStatus() + "'" + "}";
    }
}