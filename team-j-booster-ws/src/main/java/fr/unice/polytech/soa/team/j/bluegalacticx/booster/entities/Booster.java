package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterApi;

public class Booster {
    private String id;
    private String missionId;
    private BoosterStatus status;
    private BoosterLandingStep landingStep;
    private BoosterTelemetry telemetry;
    private BoosterApi boosterApi;
    private static final double SPEED_UPDATE = 0.2;

    private double previousDistance;

    private final static Logger LOG = LoggerFactory.getLogger(Booster.class);

    public Booster() {
        this.status = BoosterStatus.PENDING;
        this.landingStep = BoosterLandingStep.NOT_LANDING;
        this.telemetry = new BoosterTelemetry();
        this.missionId = "";
    }

    public Booster(String id, String missionId, BoosterStatus status, int fuelLevel) {
        this.id = id;
        this.status = status;
        if (missionId == null || missionId == "") {
            this.missionId = "";
        } else {
            this.missionId = missionId;
        }
        this.landingStep = BoosterLandingStep.NOT_LANDING;
        this.telemetry = new BoosterTelemetry();
        this.telemetry.setFuelLevel(fuelLevel);
    }

    public void initStatus() {
        this.status = BoosterStatus.PENDING;
    }

    public Booster initTelemetry() {
        boosterApi = new BoosterApi().initTelemetry();
        return this;
    }

    public void updateTelemetry() {
        this.boosterApi.nextTelemetry(this);
    }

    public void updateSpeed(SpeedChange speedChange) {
        double speed = 0.0;
        if (speedChange == (SpeedChange.INCREASE)) {
            speed = this.telemetry.getSpeed() + (this.telemetry.getSpeed() * (SPEED_UPDATE));
        } else {
            speed = this.telemetry.getSpeed() + (this.telemetry.getSpeed() * (-SPEED_UPDATE));
        }
        this.telemetry.setSpeed(speed);
    }

    public void updateState() {
        if (status == BoosterStatus.LANDING) {
            double currDistance = this.telemetry.getDistanceFromEarth();
            switch (landingStep) {
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
            this.previousDistance = this.telemetry.getDistanceFromEarth();
        }
    }

    private void handleFlipManeuver(double currentDistance) {
        if (currentDistance < this.previousDistance) {
            setLandingStep(BoosterLandingStep.FLIPPING);
            LOG.info("booster now flipping");
        }
    }

    private void handleEntryBurn(double currentDistance) {
        if (currentDistance <= 300) {
            setLandingStep(BoosterLandingStep.ENTRY_BURN);
            LOG.info("booster entering Entry Burn phase");
        }
    }

    private void handleGuidance(double currentDistance) {
        if (currentDistance <= 100) {
            setLandingStep(BoosterLandingStep.LANDING_BURN);
            LOG.info("booster entering Guidance phase");
        }
    }

    private void handleLegsDeployment(double currentDistance) {
        if (currentDistance <= 10) {
            setLandingStep(BoosterLandingStep.LEGS_DEPLOYED);
            LOG.info("Booster's Legs deployed");
        }
    }

    private void handleLanding(double currentDistance) {
        if (currentDistance == 0) {
            setLandingStep(BoosterLandingStep.LANDED);
            this.status = BoosterStatus.LANDED;
            LOG.info("Booster landed properlly ! Everything is norminal.");
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMissionId() {
        return this.missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public BoosterStatus getStatus() {
        return this.status;
    }

    public void setStatus(BoosterStatus status) {
        this.status = status;
    }

    public BoosterLandingStep getLandingStep() {
        return this.landingStep;
    }

    public void setLandingStep(BoosterLandingStep landingStep) {
        this.landingStep = landingStep;
    }

    public BoosterTelemetry getMetrics() {
        return this.telemetry;
    }

    public void setMetrics(BoosterTelemetry telemetry) {
        this.telemetry = telemetry;
    }

    public double getDistanceFromEarth() {
        return this.telemetry.getDistanceFromEarth();
    }

    public void setDistanceFromEarth(double distance) {
        this.telemetry.setDistanceFromEarth(distance);
    }

    public double getSpeed() {
        return this.telemetry.getSpeed();
    }

    public void setSpeed(double speed) {
        this.telemetry.setSpeed(speed);
    }

    public int getFuelLevel() {
        return this.telemetry.getFuelLevel();
    }

    public void setFuelLevel(int fuelLevel) {
        this.telemetry.setFuelLevel(fuelLevel);
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

    public Booster telemetry(BoosterTelemetry telemetry) {
        this.telemetry = telemetry;
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
        return "{" + "id='" + getId() + "'" + " status='" + getStatus() + "'" + ", fuelLevel='" + getFuelLevel() + "'"
                + "}";
    }

}
