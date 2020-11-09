package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

public class Mission {

    private String id;
    private String rocketId;
    private String[] boosterIds;
    private String payloadId;
    private SpaceCoordinate destination;
    private DepartmentGoNg goNg = new DepartmentGoNg();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Mission() {
    }

    public Mission(String id, String rocketId, String[] boosterIds, String payloadId, SpaceCoordinate destination,
            DepartmentGoNg goNg, Date date) {
        this.id = id;
        this.rocketId = rocketId;
        this.boosterIds = boosterIds;
        this.payloadId = payloadId;
        this.destination = destination;
        this.goNg = goNg;
        this.date = date;
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

    public String[] getBoosterIds() {
        return this.boosterIds;
    }

    public void setBoosterIds(String[] boosterIds) {
        this.boosterIds = boosterIds;
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

    public void setGoNg(DepartmentGoNg goNg) {
        this.goNg = goNg;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mission id(String id) {
        this.id = id;
        return this;
    }

    public Mission rocketId(String rocketId) {
        this.rocketId = rocketId;
        return this;
    }

    public Mission boosterIds(String[] boosterId) {
        this.boosterIds = boosterId;
        return this;
    }

    public void updateGoNogo(Department department, boolean status) {
        this.goNg.updateGoNogo(department, status);
    }

    public DepartmentGoNg getGoNg() {
        return goNg;
    }

    public Mission payloadId(String payloadId) {
        this.payloadId = payloadId;
        return this;
    }

    public Mission destination(SpaceCoordinate destination) {
        this.destination = destination;
        return this;
    }

    public Mission goNg(DepartmentGoNg goNg) {
        this.goNg = goNg;
        return this;
    }

    public Mission date(Date date) {
        this.date = date;
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
                && Objects.equals(boosterIds, mission.boosterIds) && Objects.equals(payloadId, mission.payloadId)
                && Objects.equals(destination, mission.destination) && Objects.equals(goNg, mission.goNg)
                && Objects.equals(date, mission.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rocketId, boosterIds, payloadId, destination, goNg, date);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", rocketId='" + getRocketId() + "'" + ", boosterId='" + getBoosterIds()
                + "'" + ", payloadId='" + getPayloadId() + "'" + ", destination='" + getDestination() + "'" + ", goNg='"
                + getGoNg() + "'" + ", date='" + getDate() + "'" + "}"; 
    }

}
