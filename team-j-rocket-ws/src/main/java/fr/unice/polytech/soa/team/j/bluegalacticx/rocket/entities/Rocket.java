package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.utils.RandomUtils;

public class Rocket {
    private String id;
    private SpaceTelemetry inAir;
    private SpaceTelemetry inGround;
    private RocketReport report;
    private RocketStatus status;
    private SpaceCoordinate objective;
    private String boosterId;
    private RocketApi rocketApi;

    public Rocket() {
        this.rocketApi = new RocketApi();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public SpaceTelemetry getTelemetryInAir(){
        return this.inAir;
    }

    public SpaceTelemetry getTelemetryInGround(){
        return this.inGround;
    }

    public Rocket(String id, SpaceTelemetry inAir,SpaceTelemetry inGround, RocketReport report, RocketStatus status) {
        this.id = id;
        this.inAir = inAir;
        this.inGround=inGround;
        this.report = report;
        this.status = status;
        this.rocketApi = new RocketApi();
    }

    public String detachNextStage() {
        String boosterId = this.boosterId;
        this.boosterId = "";
        return boosterId;
    }

    public SpaceTelemetry getLastTelemetry() {
        return rocketApi.retrieveLastTelemetry(this.id);
    }

    public Rocket inAir(SpaceTelemetry inAir){
        this.inAir=inAir;
        return this;
    }

    public Rocket inGround(SpaceTelemetry inGround){
        this.inGround=inGround;
        return this;
    }

    public SpaceTelemetry nextTelemetry(double distStep, Double fuelStep) {
        double newDistance = inAir.getDistance();
        newDistance -= distStep;
        this.inAir = new SpaceTelemetry()
        .heatShield(RandomUtils.randomDouble(5, inAir.getHeatShield()))
        .distance(newDistance <1 ? 0 : newDistance)
        .totalDistance(inAir.getTotalDistance())
        .speed(RandomUtils.randomDouble(20, inAir.getSpeed()))
        .irradiance(RandomUtils.randomInt(10, inAir.getIrradiance()))
        .velocityVariation(RandomUtils.randomInt(10, inAir.getVelocityVariation()))
        .temperature(RandomUtils.randomInt(50, inAir.getTemperature()))
        .vibration(RandomUtils.randomInt(5, inAir.getVibration()))
        .boosterRGA(RandomUtils.randomInt(10, inAir.getBoosterRGA()))
        .midRocketRGA(RandomUtils.randomInt(10, inAir.getMidRocketRGA()));

        return this.inAir;
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

    public void prepareLaunch() {
        rocketApi.launchWhenReady(this.objective, this.id);
    }

    public void launchSequenceActivated() throws NoSameStatusException, BoosterDestroyedException {
        if (status == RocketStatus.IN_SERVICE) {
            throw new NoSameStatusException(status.toString());
        }
        this.status = RocketStatus.IN_SERVICE;
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
        return Objects.equals(id, rocket.id) && Objects.equals(inAir, rocket.inAir) && Objects.equals(inGround,rocket.inGround)
                && Objects.equals(report, rocket.report) && Objects.equals(status, rocket.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inAir,inGround, report, status);
    }

}
