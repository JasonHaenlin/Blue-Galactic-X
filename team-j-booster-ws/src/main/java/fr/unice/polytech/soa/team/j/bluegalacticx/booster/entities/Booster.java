package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Booster {
    private String id;
    private BoosterStatus status;
    private BoosterLandingStep landingStep;
    private BoosterMetrics metrics;

    private double previousDistance;

    private final static Logger LOG = LoggerFactory.getLogger(Booster.class);

    public Booster() {
        this.landingStep = BoosterLandingStep.NOT_LANDING;
        this.metrics = new BoosterMetrics();
    }

    public Booster(String id, BoosterStatus status, int fuelLevel) {
        this.id = id;
        this.status = status;
        this.landingStep = BoosterLandingStep.NOT_LANDING;
        this.metrics = new BoosterMetrics();
        this.metrics.setFuelLevel(fuelLevel);
    }

    public void updateState(){
        if(status == BoosterStatus.LANDING){
            double currDistance = this.metrics.getDistanceFromEarth();
            switch(landingStep){
                case NOT_LANDING:
                    this.handleFlipManeuver(currDistance);
                    break;
                case FLIPPING:
                    this.handleEntryBurn(currDistance);
                    break;
                case ENTRY_BURN:
                    this.handleGuidance(currDistance);
                    break;
                case LANDING_BURN:
                    this.handleLegsDeployment(currDistance);
                    break;
                case LEGS_DEPLOYED:
                    this.handleLanding(currDistance);
                    break;
                default:
                    break;
            }
            this.previousDistance = this.metrics.getDistanceFromEarth();
        }
    }

    private void handleFlipManeuver(double currentDistance) {
        if (currentDistance < this.previousDistance) {
            this.landingStep = BoosterLandingStep.FLIPPING;
            LOG.info("booster now flipping");
        }
    }

    private void handleEntryBurn(double currentDistance) {
        if (currentDistance <= 300) {
            this.landingStep = BoosterLandingStep.ENTRY_BURN;
            LOG.info("booster entering Entry Burn phase");
        }
    }

    private void handleGuidance(double currentDistance) {
        if (currentDistance <= 100) {
            this.landingStep = BoosterLandingStep.LANDING_BURN;
            LOG.info("booster entering Guidance phase");
        }
    }

    private void handleLegsDeployment(double currentDistance) {
        if (currentDistance <= 10) {
            this.landingStep = BoosterLandingStep.LEGS_DEPLOYED;
            LOG.info("Booster's Legs deployed");
        }
    }

    private void handleLanding(double currentDistance) {
        if (currentDistance == 0) {
            this.landingStep = BoosterLandingStep.LANDED;
            this.status = BoosterStatus.LANDED;
            LOG.info("Booster landed properlly ! Everything is norminal.");
        }
    }

    public BoosterLandingStep getLandingStep(){
        return this.landingStep;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BoosterStatus getStatus() {
        return this.status;
    }

    public void setStatus(BoosterStatus status) {
        this.status = status;
    }

    public BoosterMetrics getMetrics() {
        return this.metrics;
    }

    public void setMetrics(BoosterMetrics metrics) {
        this.metrics = metrics;
    }

    public double getDistanceFromEarth() {
        return this.metrics.getDistanceFromEarth();
    }

    public void setDistanceFromEarth(double distance) {
        this.metrics.setDistanceFromEarth(distance);
    }

    public double getSpeed() {
        return this.metrics.getSpeed();
    }

    public void setSpeed(double speed) {
        this.metrics.setSpeed(speed);
    }

    public int getFuelLevel() {
        return this.metrics.getFuelLevel();
    }

    public void setFuelLevel(int fuelLevel) {
        this.metrics.setFuelLevel(fuelLevel);
    }

    public Booster id(String id) {
        this.id = id;
        return this;
    }

    public Booster status(BoosterStatus status) {
        this.status = status;
        return this;
    }

    public Booster fuelLevel(int fuelLevel) {
        this.setFuelLevel(fuelLevel);
        return this;
    }

    public Booster metrics(BoosterMetrics metrics){
        this.metrics = metrics;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booster)) {
            return false;
        }
        Booster booster = (Booster) o;
        return Objects.equals(status, booster.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "{" + "id='" + getId() + "'" + " status='" + getStatus() + "'" + ", fuelLevel='" + getFuelLevel() + "'" + "}";
    }

}
