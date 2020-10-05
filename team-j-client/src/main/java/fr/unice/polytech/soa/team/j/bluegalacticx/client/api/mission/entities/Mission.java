package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mission {

    private int rocketId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Mission() {
    }

    public Mission(int rocketId, Date date) {
        this.rocketId = rocketId;
        this.date = date;
    }

    public int getRocketId() {
        return this.rocketId;
    }

    public void setRocketId(int rocketId) {
        this.rocketId = rocketId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mission rocketId(int rocketId) {
        this.rocketId = rocketId;
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
        return rocketId == mission.rocketId && Objects.equals(date, mission.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rocketId, date);
    }

    @Override
    public String toString() {
        return "{" + " rocketId='" + getRocketId() + "'" + ", date='" + getDate() + "'" + "}";
    }

}
