package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities;

public class BoosterMetrics {
    
    private double distanceFromEarth;
    private double speed;
    private int fuelLevel;

    public BoosterMetrics(){
        this.distanceFromEarth = 0;
        this.speed = 0;
    }

    public BoosterMetrics(double distanceFromEarth, double speed){
        this.distanceFromEarth = distanceFromEarth;
        this.speed = speed;
    }

    public double getDistanceFromEarth() {
        return this.distanceFromEarth;
    }

    public void setDistanceFromEarth(double distance) {
        if(distance < 0){
            distance = 0;
        }
        this.distanceFromEarth = distance;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        if (speed < 0) {
            speed = 0;
        }
        this.speed = speed;
    }

    public int getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        if (fuelLevel < 0) {
            fuelLevel = 0;
        }
        this.fuelLevel = fuelLevel;
    }
}
