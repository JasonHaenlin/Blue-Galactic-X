package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;

@RestController
@RequestMapping("/telemetry")
@EnableMongoRepositories(basePackages = { "fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db" })
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @GetMapping("/anomalies")
    public List<Anomaly> checkForAnomalies() {
        return telemetryService.checkRecordedAnomalies();
    }

    @PostMapping("/rocket")
    public void createTelemetryData(@RequestBody TelemetryRocketData rocketData) {
        try {
            telemetryService.createRocketData(rocketData);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (TelemetryDataRocketIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/rocket/{rocketId}")
    public TelemetryRocketData retrieveTelemetryData(@RequestParam String rocketId) {
        try {
            return telemetryService.retrieveRocketData(rocketId);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (NoTelemetryRocketDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

}
