package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities;

import java.util.Date;
import java.util.Objects;

public class Log {
    
    private String text;
    private Date date;

    public Log() {
    }

    public Log(String text) {
        this.text = text;
        this.date = new Date();
    }

    public Log(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Log)) {
            return false;
        }
        Log log = (Log) o;
        return Objects.equals(text, log.text) && Objects.equals(date, log.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, date);
    }

    @Override
    public String toString() {
        return "{" +
            " text='" + getText() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
    

}
