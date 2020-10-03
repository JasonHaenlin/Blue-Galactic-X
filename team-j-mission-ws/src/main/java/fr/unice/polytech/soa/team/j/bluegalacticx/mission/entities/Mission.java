package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.HashCodeBuilder;


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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setRocketId(int rocketId) {
        this.rocketId = rocketId;
    }

    public int getRocketId() {
        return rocketId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
        append(rocketId).
        append(date).
        toHashCode();
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
        
        return mission.getDate().equals(this.date) && mission.getRocketId()==this.rocketId;
    }

    

    @Override
    public String toString() {
        StringBuilder missionText = new StringBuilder();
        missionText.append("rocketId : " + rocketId + ", ");
        missionText.append("Date : " + date.toString());

        return missionText.toString();
    }

}
