package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

public class RocketStatus { 

    private int irradiance = 0; //sun sensors in nm
    private int velocityVariation = 0; //accelerometers value in mm/s
    private int temperature = 0; //temperature in °C
    private int groundVibration = 0; //ground vibration in Hz
    private int boosterRGA = 0; //RGA of booster in %
    private int midRocketRGA = 0; //RGA of the middle of the rocket in %
        
    public RocketStatus setIrradiance(int irradiance) {
        this.irradiance = irradiance;
        return this;
    }

    public int getIrradiance() {
        return this.irradiance;
    }

    public RocketStatus setVelocityVariation(int velocityVariation) {
        this.velocityVariation = velocityVariation;
        return this;
    }

    public int getVelocityVariation() {
        return this.velocityVariation;
    }

    public RocketStatus setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public RocketStatus setGroundVibration(int groundVibration)  {
        this.groundVibration = groundVibration;
        return this;
    }

    public int getGroundVibration() {
        return this.groundVibration;
    }

    public RocketStatus setBoosterRGA(int boosterRGA) {
        this.boosterRGA = boosterRGA;
        return this;
    }

    public int getBoosterRGA() {
        return this.boosterRGA;
    }

    public RocketStatus setMidRocketRGA(int midRocketRGA) {
        this.midRocketRGA = midRocketRGA;
        return this;
    }

    public int getMidRocketRGA() {
        return this.midRocketRGA;
    }

    @Override
    public String toString() {
        return addAsciiArt() +
                "********** ROCKET STATUS **********\n" +
                "\tIrradiance: " + this.irradiance + " nm\n" +
                "\tVelocity variation: " + this.velocityVariation + " mm/s\n" +
                "\tTemperature: " + this.temperature + " °C\n" +
                "\tGround vibration: " + this.groundVibration + " Hz\n" +
                "\tBooster RGA : " + this.boosterRGA + " %\n" +
                "\tMid-rocket RGA : " + this.midRocketRGA + " %\n";
    }

    private String addAsciiArt() {
        return 
            "\t        |\n"+
            "\t       / \\\n"+
            "\t      / _ \\\n"+
            "\t     |.o '.|\n"+
            "\t     |'._.'|\n"+
            "\t     |     |\n"+
            "\t   ,'|  |  |`.\n"+
            "\t  /  |  |  |  \\\n"+
            "\t  |,-'--|--'-.|\n\n";
    }

}