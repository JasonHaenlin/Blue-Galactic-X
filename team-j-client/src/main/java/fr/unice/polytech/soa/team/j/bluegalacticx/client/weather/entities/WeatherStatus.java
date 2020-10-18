package fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.entities;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WeatherStatus {

    private List<WeatherBeacon> beacons;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar date;

    public WeatherStatus() {
    }

    public WeatherStatus(List<WeatherBeacon> beacons, Calendar date) {
        this.beacons = beacons;
        this.date = date;
    }

    public List<WeatherBeacon> getBeacons() {
        return this.beacons;
    }

    public void setBeacons(List<WeatherBeacon> beacons) {
        this.beacons = beacons;
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public WeatherStatus beacons(List<WeatherBeacon> beacons) {
        this.beacons = beacons;
        return this;
    }

    public WeatherStatus date(Calendar date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WeatherStatus)) {
            return false;
        }
        WeatherStatus weatherStatus = (WeatherStatus) o;
        return Objects.equals(beacons, weatherStatus.beacons) && Objects.equals(date, weatherStatus.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beacons, date);
    }

    @Override
    public String toString() {
        return "{" + " beacons='" + getBeacons() + "'" + ", date='" + getDate() + "'" + "}";
    }

}
