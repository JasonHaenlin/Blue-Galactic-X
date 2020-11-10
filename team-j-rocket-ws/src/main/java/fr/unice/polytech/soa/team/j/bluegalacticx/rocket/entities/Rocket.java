package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;

public class Rocket {
    private String id;
    private RocketReport report;
    private RocketStatus status;
    private SpaceCoordinate objective;
    private String boosterId;
    private String boosterId2;
    private RocketApi rocketApi;
    // Increase or decrease speed by 20% (maxQ)
    private static final double SPEED_UPDATE = 0.2;

    public Rocket() {
        this.rocketApi = new RocketApi();
    }

    public void setId(String id) {
        this.id = id;
    }

    public Rocket withBaseTelemetry() {
        this.rocketApi.withBasedTelemetry();
        return this;
    }

    public Rocket withRocketApi(RocketApi api) {
        this.rocketApi = api;
        return this;
    }

    public Rocket boosterId2(String boosterId2){
        this.boosterId2=boosterId2;
        return this;
    }

    public String getBoosterId2(){
        return boosterId2;
    }

    
    public void setBoosterId2(String boosterId2){
        this.boosterId2 =  boosterId2;
    }

    public String getId() {
        return id;
    }

    public Rocket(String id, RocketReport report, RocketStatus status) {
        this.id = id;
        this.report = report;
        this.status = status;
        this.rocketApi = new RocketApi();
    }

    public String detachNextStage() {
        String boosterId = this.boosterId;
        this.boosterId = "";
        return boosterId;
    }

    public void setStatus(RocketStatus rocketStatus){
        this.status=rocketStatus;
    }
    public boolean checkRocketInMaxQ() {
        SpaceTelemetry telemetry = rocketApi.getAirTelemetry();
        if (telemetry.getDistance() <= telemetry.getTotalDistance() - MaxQ.MIN
                && telemetry.getDistance() >= telemetry.getTotalDistance() - MaxQ.MAX) {
            return true;
        }
        return false;
    }

    public RocketApi getRocketApi() {
        return rocketApi;
    }

    public void updateSpeed(SpeedChange speedChange) {
        double speed = 0.0;
        SpaceTelemetry telemetry = rocketApi.getAirTelemetry();
        if (speedChange == (SpeedChange.INCREASE)) {
            speed = telemetry.getSpeed() + (telemetry.getSpeed() * (SPEED_UPDATE));
        } else {
            speed = telemetry.getSpeed() + (telemetry.getSpeed() * (-SPEED_UPDATE));
        }
        telemetry.setSpeed(speed);
    }

    public SpaceTelemetry getLastTelemetry() {
        return rocketApi.retrieveLastTelemetry();
    }

    public RocketReport retrieveLastReport() throws RocketDestroyedException {
        throwIfRocketIsDestroyed();
        return this.report;
    }

    public void replaceWithNewReport(RocketReport report) throws CannotBeNullException {
        if (report.getEngine() == null || report.getOverallResult() == null) {
            throw new CannotBeNullException("report");
        }
        this.report = report;
    }

    public void initStatus() {
        this.status = RocketStatus.AT_BASE;
    }

    public RocketStatus getStatus() {
        return this.status;
    }

    public void changeRocketStatus(RocketStatus status) throws RocketDestroyedException {
        throwIfRocketIsDestroyed("Cannot change the status of a destroyed rocket");
        this.status = status;
    }

    public String getBoosterId() {
        return this.boosterId;
    }

    public void setBoosterId(String id) {
        this.boosterId = id;
    }

    public void prepareLaunch() throws NoObjectiveSettedException {
        rocketApi.launchWhenReady(this.objective, this.id);
    }

    public void arrivedAtDestination() {
        this.status = RocketStatus.ARRIVED;
    }

    public double distanceFromEarth() {
        SpaceTelemetry t = rocketApi.getAirTelemetry();
        return t.getTotalDistance() - t.getDistance();
    }

    public double currentSpeed() {
        return rocketApi.getAirTelemetry().getSpeed();
    }

    public void launchSequenceActivated() throws NoSameStatusException, BoosterDestroyedException {
        if (status == RocketStatus.IN_SERVICE) {
            throw new NoSameStatusException(status.toString());
        }
        this.status = RocketStatus.IN_SERVICE;
    }
    public void readyToLaunchActivated() throws NoSameStatusException {
        if (status == RocketStatus.READY_FOR_LAUNCH) {
            throw new NoSameStatusException(status.toString());
        }
        
        this.status = RocketStatus.READY_FOR_LAUNCH;
    }
    public void initiateTheSelfDestructSequence() throws RocketDestroyedException {
        if (status == RocketStatus.DESTROYED) {
            throw new RocketDestroyedException();
        }
        this.status = RocketStatus.DESTROYED;
    }

    public void setMissionObjective(SpaceCoordinate coordinatesFromMission) {
        this.objective = coordinatesFromMission;
    }

    public SpaceCoordinate retrieveObjectiveCoordinates() {
        return objective;
    }

    public Rocket id(String id) {
        this.id = id;
        return this;
    }

    public Rocket report(RocketReport report) {
        this.report = report;
        return this;
    }

    public Rocket status(RocketStatus status) throws RocketDestroyedException {
        throwIfRocketIsDestroyed("Cannot change the status of a destroyed rocket");
        this.status = status;
        return this;
    }

    public Rocket spaceCoordinate(SpaceCoordinate objective) {
        this.objective = objective;
        return this;
    }

    public Rocket boosterId(String boosterId) {
        this.boosterId = boosterId;
        return this;
    }

    private void throwIfRocketIsDestroyed() throws RocketDestroyedException {
        if (status == RocketStatus.DESTROYED) {
            throw new RocketDestroyedException();
        }
    }

    private void throwIfRocketIsDestroyed(String msg) throws RocketDestroyedException {
        if (status == RocketStatus.DESTROYED) {
            throw new RocketDestroyedException(msg);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Rocket)) {
            return false;
        }
        Rocket rocket = (Rocket) o;
        return Objects.equals(id, rocket.id) && Objects.equals(report, rocket.report)
                && Objects.equals(status, rocket.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, report, status);
    }

}
