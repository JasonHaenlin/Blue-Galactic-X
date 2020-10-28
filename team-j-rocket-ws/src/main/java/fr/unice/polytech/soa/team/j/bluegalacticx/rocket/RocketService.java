package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.ReportNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.DepartmentStatusProducer;

@Service
public class RocketService {

    @Autowired
    private DepartmentStatusProducer departmentStatusProducer;

    private List<Rocket> rockets = RocketsMocked.rockets;

    public void addNewRocket(Rocket rocket) throws CannotBeNullException {
        if (rocket == null) {
            throw new CannotBeNullException("rocket");
        }
        rocket.initStatus();
        rockets.add(rocket);
    }

    public SpaceTelemetry getLastTelemetry(String rocketId) throws RocketDestroyedException, RocketDoesNotExistException {
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
}
