package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpeedChange;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.ReportNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.MaxQProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;

@Service
public class RocketService {

    @Autowired
    private DepartmentStatusProducer departmentStatusProducer;

    @Autowired
    private RocketStatusProducer rocketProducer;

    @Autowired
    private MaxQProducer maxQProducer;
    private List<Rocket> rockets = new ArrayList<>();

    public Rocket addNewRocket(Rocket rocket) throws CannotBeNullException {
        if (rocket == null) {
            throw new CannotBeNullException("rocket");
        }
        rocket.withBaseTelemetry().initStatus();
        rocket.setId(UUID.randomUUID().toString());
        rockets.add(rocket);
        return rocket;
    }

    public void updateTelemetryToSend() throws RocketDestroyedException {
        for (Rocket r : rockets) {
            if (r.getStatus() != RocketStatus.DESTROYED) {
                SpaceTelemetry st = r.getLastTelemetry();
                // telemetryRocketProducer.sendTelemetryRocketEvent(st);

                if (r.checkRocketInMaxQ() && r.getStatus() != RocketStatus.ENTER_MAXQ) {
                    r.changeRocketStatus(RocketStatus.ENTER_MAXQ);
                    r.updateSpeed(SpeedChange.DECREASE);
                    maxQProducer.sendInMaxQ(r.getBoosterId());
                } else if (!r.checkRocketInMaxQ() && r.getStatus() == RocketStatus.ENTER_MAXQ) {
                    r.changeRocketStatus(RocketStatus.QUIT_MAXQ);
                    r.updateSpeed(SpeedChange.INCREASE);
                    maxQProducer.sendQuitMaxQ(r.getBoosterId());
                }

                if (st.getDistance() <= 0 && r.getStatus() != RocketStatus.ARRIVED) {
                    r.arrivedAtDestination();
                    rocketProducer.donedRocketEvent(r.getId());
                }
            }

        }
    }

    public SpaceTelemetry getLastTelemetry(String rocketId)
            throws RocketDestroyedException, RocketDoesNotExistException {
        return retrieveCorrespondingRocket(rocketId).getLastTelemetry();
    }

    public RocketStatus getRocketStatus(String rocketId) throws RocketDoesNotExistException {
        return retrieveCorrespondingRocket(rocketId).getStatus();
    }

    public RocketStatus getRocketDepartmentStatus(String rocketId) throws RocketDoesNotExistException {
        return getRocketStatus(rocketId);
    }

    public void submitNewReport(String rocketId, RocketReport rocketReport)
            throws RocketDoesNotExistException, CannotBeNullException {
        retrieveCorrespondingRocket(rocketId).replaceWithNewReport(rocketReport);
    }

    public void updateGoNogoForRocket(String idValue, boolean status)
            throws RocketDestroyedException, RocketDoesNotExistException {
        retrieveCorrespondingRocket(idValue)
                .status(status ? RocketStatus.READY_FOR_LAUNCH : RocketStatus.NOT_READY_FOR_LAUNCH);
    }

    public RocketReport retrieveLastReport(String rocketId)
            throws ReportNotFoundException, RocketDestroyedException, RocketDoesNotExistException {
        RocketReport report = retrieveCorrespondingRocket(rocketId).retrieveLastReport();
        if (report == null) {
            throw new ReportNotFoundException();
        }
        return report;
    }

    private Rocket retrieveCorrespondingRocket(String id) throws RocketDoesNotExistException {
        return rockets.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RocketDoesNotExistException(id));
    }

    public void setRocketDepartmentStatus(String rocketId, boolean go) {
        departmentStatusProducer.notifyDepartmentStatus(rocketId, go);
    }

    public Rocket retrieveRocket(String id) throws RocketDoesNotExistException {
        return retrieveCorrespondingRocket(id);
    }
}
