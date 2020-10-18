package fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities;

import java.util.Objects;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.CannotAssignMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.RocketDestroyedException;

public class Rocket {
    private String id;
    private RocketMetrics metrics;
    private RocketReport report;
    private RocketStatus status;
    private String missionId;
    private SpaceCoordinate objective;
    private String boosterId;

    public Rocket() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Rocket(String id, RocketMetrics metrics, RocketReport report, RocketStatus status, String missionId) {
        this.id = id;
        this.metrics = metrics;
        this.report = report;
        this.status = status;
        this.missionId = missionId;
    }

    public String detachNextStage(){
        String boosterId = this.boosterId;
        this.boosterId = "";
        return boosterId;
    }

    public RocketMetrics retrieveLastMetrics() throws RocketDestroyedException {
        throwIfRocketIsDestroyed();
        return this.metrics;
    }

    public void updateWithRecentMetrics(RocketMetrics metrics) throws CannotBeNullException {
        if (metrics == null) {
            throw new CannotBeNullException("metrics");
        }
        this.metrics = metrics;
    }

    public RocketReport retrieveLastReport() throws RocketDestroyedException {
        throwIfRocketIsDestroyed();
        return this.report;
    }

    public void replaceWithNewReport(RocketReport report) throws CannotBeNullException {
        System.out.println(report);
        if (report.getEngine() == null || report.getOverallResult() == null) {
            throw new CannotBeNullException("report");
        }
        this.report = report;
    }

    public RocketStatus getStatus() {
        return this.status;
    }

    public void changeRocketStatus(RocketStatus status) throws RocketDestroyedException {
        throwIfRocketIsDestroyed("Cannot change the status of a destroyed rocket");
        this.status = status;
    }

    public void assignMission(String missionId) throws CannotAssignMissionException {
        if (status != RocketStatus.AT_BASE) {
            throw new CannotAssignMissionException(status);
        }
        this.missionId = missionId;
    }

    public String getTheCurrentMissionId() {
        return missionId;
    }

    public String getBoosterId(){
        return this.boosterId;
    }

    public void setBoosterId(String id){
        this.boosterId = id;
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

    public Rocket metrics(RocketMetrics metrics) {
        this.metrics = metrics;
        return this;
    }

    public Rocket report(RocketReport report) {
        this.report = report;
        return this;
    }

    public Rocket report(String missionId) {
        this.missionId = missionId;
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
        return Objects.equals(id, rocket.id) && Objects.equals(metrics, rocket.metrics)
                && Objects.equals(report, rocket.report) && Objects.equals(status, rocket.status)
                && Objects.equals(missionId, rocket.missionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metrics, report, status, missionId);
    }

}
