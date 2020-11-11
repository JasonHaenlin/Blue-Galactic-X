package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;

public class Rocket {
    private String id;
    private RocketReport report;
    private RocketStatus status;
    private RocketLaunchStep launchStep;
    private SpaceCoordinate objective;
    private String boosterId;
    private String boosterId2;
    private RocketApi rocketApi;
    private int launchTimer = 0;
    // Increase or decrease speed by 20% (maxQ)
    private static final double MAXQ_SPEED_UPDATE = 0.2;
    private final static Logger LOG = LoggerFactory.getLogger(Rocket.class);

    public Rocket() {
        this.rocketApi = new RocketApi();
        this.launchStep = RocketLaunchStep.NOT_STARTED;
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

    public Rocket boosterId2(String boosterId2) {
        this.boosterId2 = boosterId2;
        return this;
    }

    public String getBoosterId2() {
        return boosterId2;
    }

    public void setBoosterId2(String boosterId2) {
        this.boosterId2 = boosterId2;
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

    public RocketLaunchStep getLaunchStep() {
        return this.launchStep;
    }

    public void setLaunchStep(RocketLaunchStep rocketLaunchStep) {
        this.launchStep = rocketLaunchStep;
    }

    public void updateState() {
        if (this.status == RocketStatus.STARTING) {
            handleLaunchTimer();
        } else if (this.status == RocketStatus.IN_SERVICE) {
            switch (this.launchStep) {
                case LIFTOFF:
                case ENTER_MAXQ:
                    handleMaxQLaunchStep();
                    break;
                case MAXQ_PASSED:
                    handleIncomingMainEngineCutoff();
                    break;
                case MAIN_ENGINE_CUTOFF:
                    handleStageSeparation();
                    break;
                case STAGE_SEPARATION:
                    handleStartSecondEngine();
                    break;
                case SECOND_ENGINE_RUNNING:
                    handleFairingSeparation();
                    break;
                case FAIRING_SEPARATION:
                    handleSecondEngineCutoff();
                    break;
                case SECOND_ENGINE_CUTOFF:
                    handlePayloadSeparation();
                    break;
                case PAYLOAD_SEPARATION:
                    setLaunchStep(RocketLaunchStep.FINISHED);
                    setStatus(RocketStatus.ARRIVED);
                    LOG.info("Launch complete !");
                    break;
                default:
                    break;
            }
        }

    }

    private void handleLaunchTimer() {
        this.launchTimer++;
        if (this.launchTimer > 6) {
            setLaunchStep(RocketLaunchStep.LIFTOFF);
            LOG.info("Liftoff !");
            setStatus(RocketStatus.IN_SERVICE);
        } else if (this.launchTimer > 3) {
            setLaunchStep(RocketLaunchStep.MAIN_ENGINE_START);
            LOG.info("Main engine starting ...");
        }
    }

    private void handleMaxQLaunchStep() {
        if (checkRocketInMaxQ() && this.launchStep != RocketLaunchStep.ENTER_MAXQ) {
            setLaunchStep(RocketLaunchStep.ENTER_MAXQ);
            LOG.info("Entering MaxQ, decreasing speed");
            updateSpeed(SpeedChange.DECREASE);
        } else if (!checkRocketInMaxQ() && this.launchStep == RocketLaunchStep.ENTER_MAXQ) {
            setLaunchStep(RocketLaunchStep.MAXQ_PASSED);
            LOG.info("MaxQ passed successfully, increasing speed");
            updateSpeed(SpeedChange.INCREASE);
        }
    }

    private void handleIncomingMainEngineCutoff() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (telemetry.getDistance() < (telemetry.getTotalDistance() * 0.45)) {
            setLaunchStep(RocketLaunchStep.MAIN_ENGINE_CUTOFF);
            LOG.info("Main engine cutoff to prepare Stage separation");
        }
    }

    private void handleStageSeparation() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (telemetry.getDistance() < (telemetry.getTotalDistance() * 0.35)) {
            setLaunchStep(RocketLaunchStep.STAGE_SEPARATION);
            LOG.info("Separated from stage");
        }
    }

    private void handleStartSecondEngine() {
        setLaunchStep(RocketLaunchStep.SECOND_ENGINE_RUNNING);
        LOG.info("Second engine is now running");
    }

    private void handleFairingSeparation() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (telemetry.getDistance() < (telemetry.getTotalDistance() * 0.1)) {
            setLaunchStep(RocketLaunchStep.FAIRING_SEPARATION);
            LOG.info("Fairing separation");
        }
    }

    private void handleSecondEngineCutoff() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (telemetry.getDistance() < (telemetry.getTotalDistance() * 0.01)) {
            setLaunchStep(RocketLaunchStep.SECOND_ENGINE_CUTOFF);
            LOG.info("Second engine cutoff, to prepare for payload separation");
        }
    }

    private void handlePayloadSeparation() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (telemetry.getDistance() < (telemetry.getTotalDistance() * 0.001)) {
            setLaunchStep(RocketLaunchStep.PAYLOAD_SEPARATION);
            LOG.info("Separated from Payload");
        }
    }

    public void setStatus(RocketStatus rocketStatus) {
        this.status = rocketStatus;
    }

    public boolean checkRocketInMaxQ() {
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
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
        SpaceTelemetry telemetry = rocketApi.getCurrentTelemetry();
        if (speedChange == (SpeedChange.INCREASE)) {
            speed = telemetry.getSpeed() + (telemetry.getSpeed() * (MAXQ_SPEED_UPDATE));
        } else {
            speed = telemetry.getSpeed() + (telemetry.getSpeed() * (-MAXQ_SPEED_UPDATE));
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

    public SpaceCoordinate getObjective() {
        return objective;
    }

    public static double getSpeedUpdate() {
        return MAXQ_SPEED_UPDATE;
    }

    public RocketReport getReport() {
        return report;
    }

    public String getBoosterId() {
        return this.boosterId;
    }

    public void setBoosterId(String id) {
        this.boosterId = id;
    }

    public void prepareLaunch() throws NoObjectiveSettedException {
        setLaunchStep(RocketLaunchStep.PREPARATION);
        rocketApi.launchWhenReady(this.objective, this.id);
    }

    public void arrivedAtDestination() {
        this.status = RocketStatus.ARRIVED;
    }

    public double distanceFromEarth() {
        SpaceTelemetry t = rocketApi.getCurrentTelemetry();
        return t.getTotalDistance() - t.getDistance();
    }

    public double currentSpeed() {
        return rocketApi.getCurrentTelemetry().getSpeed();
    }

    public void launchSequenceActivated() throws NoSameStatusException, BoosterDestroyedException {
        if (status == RocketStatus.STARTING) {
            throw new NoSameStatusException(status.toString());
        }
        this.status = RocketStatus.STARTING;
        setLaunchStep(RocketLaunchStep.STARTUP);
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
