package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.entities;

import java.util.Calendar;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WeatherReport {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar date;
    /**
     * average in %
     */
    private int avgRainfall;
    /**
     * average in %
     */
    private int avgHumidity;
    /**
     * average in km/h
     */
    private int avgWind;
    /**
     * average in %
     */
    private int avgVisibility;
    /**
     * average in °C
     */
    private int avgTemperature;
    private WeatherType weatherType;
    private String overallResult;

    public WeatherReport() {
    }

    public WeatherReport(Calendar date, int avgRainfall, int avgHumidity, int avgWind, int avgVisibility,
            int avgTemperature, WeatherType weatherType, String overallResult) {
        this.date = date;
        this.avgRainfall = avgRainfall;
        this.avgHumidity = avgHumidity;
        this.avgWind = avgWind;
        this.avgVisibility = avgVisibility;
        this.avgTemperature = avgTemperature;
        this.weatherType = weatherType;
        this.overallResult = overallResult;
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getAvgRainfall() {
        return this.avgRainfall;
    }

    public void setAvgRainfall(int avgRainfall) {
        this.avgRainfall = avgRainfall;
    }

    public int getAvgHumidity() {
        return this.avgHumidity;
    }

    public void setAvgHumidity(int avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public int getAvgWind() {
        return this.avgWind;
    }

    public void setAvgWind(int avgWind) {
        this.avgWind = avgWind;
    }

    public int getAvgVisibility() {
        return this.avgVisibility;
    }

    public void setAvgVisibility(int avgVisibility) {
        this.avgVisibility = avgVisibility;
    }

    public int getAvgTemperature() {
        return this.avgTemperature;
    }

    public void setAvgTemperature(int avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public WeatherType getWeatherType() {
        return this.weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public String isOverallResult() {
        return this.overallResult;
    }

    public String getOverallResult() {
        return this.overallResult;
    }

    public void setOverallResult(String overallResult) {
        this.overallResult = overallResult;
    }

    public WeatherReport date(Calendar date) {
        this.date = date;
        return this;
    }

    public WeatherReport avgRainfall(int avgRainfall) {
        this.avgRainfall = avgRainfall;
        return this;
    }

    public WeatherReport avgHumidity(int avgHumidity) {
        this.avgHumidity = avgHumidity;
        return this;
    }

    public WeatherReport avgWind(int avgWind) {
        this.avgWind = avgWind;
        return this;
    }

    public WeatherReport avgVisibility(int avgVisibility) {
        this.avgVisibility = avgVisibility;
        return this;
    }

    public WeatherReport avgTemperature(int avgTemperature) {
        this.avgTemperature = avgTemperature;
        return this;
    }

    public WeatherReport weatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
        return this;
    }

    public WeatherReport overallResult(String overallResult) {
        this.overallResult = overallResult;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WeatherReport)) {
            return false;
        }
        WeatherReport weatherReport = (WeatherReport) o;
        return Objects.equals(date, weatherReport.date) && avgRainfall == weatherReport.avgRainfall
                && avgHumidity == weatherReport.avgHumidity && avgWind == weatherReport.avgWind
                && avgVisibility == weatherReport.avgVisibility && avgTemperature == weatherReport.avgTemperature
                && Objects.equals(weatherType, weatherReport.weatherType)
                && Objects.equals(overallResult, weatherReport.overallResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, avgRainfall, avgHumidity, avgWind, avgVisibility, avgTemperature, weatherType,
                overallResult);
    }

    @Override
    public String toString() {
        return "{" + " date='" + getDate() + "'" + ", avgRainfall='" + getAvgRainfall() + "'" + ", avgHumidity='"
                + getAvgHumidity() + "'" + ", avgWind='" + getAvgWind() + "'" + ", avgVisibility='" + getAvgVisibility()
                + "'" + ", avgTemperature='" + getAvgTemperature() + "'" + ", weatherType='" + getWeatherType() + "'"
                + ", overallResult='" + isOverallResult() + "'" + "}";
    }

}
