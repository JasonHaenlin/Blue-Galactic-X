package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.ReportNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;

@Service
public class RocketService {

    private List<Rocket> rockets = RocketsMocked.rockets;

    public RocketMetrics getLastMetrics(String rocketId) throws RocketDestroyedException, RocketDoesNotExistException {
        return retrieveCorrespondingRocket(rocketId).retrieveLastMetrics();
    }

    public void submitNewReport(String rocketId, RocketReport rocketReport)
            throws RocketDoesNotExistException, CannotBeNullException {
        retrieveCorrespondingRocket(rocketId).replaceWithNewReport(rocketReport);
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
}
