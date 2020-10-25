package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryBoosterDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryPayloadDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;

@RestController
@RequestMapping("/telemetry")
@EnableMongoRepositories(basePackages = { "fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db" })
public class TelemetryController {

    private final static Logger LOG = LoggerFactory.getLogger(TelemetryController.class);

    @Autowired
    private TelemetryService telemetryService;

    @GetMapping("/anomalies")
    public Set<Anomaly> checkForAnomalies() {
        return telemetryService.checkRecordedAnomalies();
    }

    @PostMapping("/rocket")
    public void createRocketTelemetryData(@RequestBody TelemetryRocketData rocketData) {
        try {
            LOG.info(rocketData.toString());
            telemetryService.createRocketData(rocketData);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (TelemetryDataRocketIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/payload")
    public void createPayloadTelemetryData(@RequestBody TelemetryPayloadData payloadData) {
        try {
            LOG.info(payloadData.toString());
            telemetryService.createPayloadData(payloadData);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (BadPayloadIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/booster")
    public void createBoosterTelemetryData(@RequestBody TelemetryBoosterData boosterData) {
        LOG.info(boosterData.toString());
        try {
            LOG.info(boosterData.toString());
            telemetryService.createBoosterData(boosterData);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (TelemetryDataBoosterIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/rocket/{rocketId}")
    public List<TelemetryRocketData> retrieveRocketTelemetryData(@PathVariable String rocketId) {
        try {
            return telemetryService.retrieveRocketData(rocketId);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (NoTelemetryRocketDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/payload/{payloadId}")
    public TelemetryPayloadData retrievePayloadTelemetryData(@PathVariable String payloadId) {
        try {
            return telemetryService.retrievePayloadData(payloadId);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (NoTelemetryPayloadDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/booster/{boosterId}")
    public List<TelemetryBoosterData> retrieveBoosterData(@PathVariable String boosterId) {
        try {
            return telemetryService.retrieveBoosterData(boosterId);
        } catch (DataAccessResourceFailureException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        } catch (NoTelemetryBoosterDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
