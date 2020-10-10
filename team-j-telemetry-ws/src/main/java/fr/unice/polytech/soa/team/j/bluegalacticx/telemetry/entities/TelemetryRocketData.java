package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rocketTelemetry")
public class TelemetryRocketData {


    @Id
    private String rocketId;
    
    private int irradiance = 0; // sun sensors in nm
    private int velocityVariation = 0; // accelerometers value in mm/s
    private int temperature = 0; // temperature in °C
    private int vibration = 0; // ground vibration in Hz
    private int boosterRGA = 0; // RGA of booster in %
    private int midRocketRGA = 0; // RGA of the middle of the rocket in %
    private int fuelLevel = 100; // Fuel Level in %
    private double heatShield; // %
    private double speed; // m/s
    private int x;
    private int y;
    private int z;

    public TelemetryRocketData(){
        
    }

    public void setRocketId(String rocketId){
        this.rocketId=rocketId;
    }
    public String getRocketId(){
        return rocketId;
    }


    public int getIrradiance() {
        return this.irradiance;
    }

    public void setIrradiance(int irradiance) {
        this.irradiance = irradiance;
    }

    public int getVelocityVariation() {
        return this.velocityVariation;
    }

    public void setVelocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getVibration() {
        return this.vibration;
    }

    public void setVibration(int vibration) {
        this.vibration = vibration;
    }

    public int getBoosterRGA() {
        return this.boosterRGA;
    }

    public void setBoosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
    }

    public int getMidRocketRGA() {
        return this.midRocketRGA;
    }

    public void setMidRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
    }

    public double getHeatShield() {
        return this.heatShield;
    }

    public void setHeatShield(double heatShield) {
        this.heatShield = heatShield;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public TelemetryRocketData irradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public TelemetryRocketData velocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public TelemetryRocketData temperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public TelemetryRocketData vibration(int vibration) {
        this.vibration = vibration;
        return this;
    }

    public TelemetryRocketData boosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public TelemetryRocketData midRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    public TelemetryRocketData fuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
        return this;
    }

    public TelemetryRocketData heatShield(double heatShield) {
        this.heatShield = heatShield;
        return this;
    }

    public TelemetryRocketData speed(double speed) {
        this.speed = speed;
        return this;
    }

    public TelemetryRocketData x(int x) {
        this.x = x;
        return this;
    }

    public TelemetryRocketData y(int y) {
        this.y = y;
        return this;
    }

    public TelemetryRocketData z(int z) {
        this.z = z;
        return this;
    }

    public int getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TelemetryRocketData)) {
            return false;
        }
        TelemetryRocketData telemetryData = (TelemetryRocketData) o;
        return fuelLevel == telemetryData.fuelLevel ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fuelLevel);
    }


 

    @Override
    public String toString() {
        return "{" +
            " irradiance='" + getIrradiance() + "'" +
            ", velocityVariation='" + getVelocityVariation() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", vibration='" + getVibration() + "'" +
            ", boosterRGA='" + getBoosterRGA() + "'" +
            ", midRocketRGA='" + getMidRocketRGA() + "'" +
            ", fuelLevel='" + getFuelLevel() + "'" +
            ", heatShield='" + getHeatShield() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", z='" + getZ() + "'" +
            "}";
    }
   
    
}
