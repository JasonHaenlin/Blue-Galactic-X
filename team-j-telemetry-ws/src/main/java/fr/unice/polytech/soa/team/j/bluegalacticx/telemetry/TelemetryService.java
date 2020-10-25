package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryBoosterDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryPayloadDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryRocketDataRepository;
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

@Service
public class TelemetryService {

    private Set<Anomaly> anomalies = new HashSet<>();

    @Autowired
    private TelemetryRocketDataRepository telemetryRocketDataRepository;

    @Autowired
    private TelemetryPayloadDataRepository telemetryPayloadDataRepository;

    @Autowired
    private TelemetryBoosterDataRepository telemetryBoosterDataRepository;


    public Set<Anomaly> checkRecordedAnomalies() {
        return anomalies;
    }

    public void createRocketData(TelemetryRocketData rocketData) throws TelemetryDataRocketIdException {
        if (!checkRocketIdExist(rocketData)) {
            throw new TelemetryDataRocketIdException();
        }
        Set<Anomaly> rocketAnomalies = rocketData.checkForAnomalies();
        this.anomalies.addAll(rocketAnomalies);
        telemetryRocketDataRepository.save(rocketData);
    }

    public void createPayloadData(TelemetryPayloadData payloadData) throws BadPayloadIdException {
        if (payloadData.getPayloadId() == null) {
            throw new BadPayloadIdException();
        }
        Set<Anomaly> payloadAnomalies = payloadData.checkForAnomalies();
        this.anomalies.addAll(payloadAnomalies);
        telemetryPayloadDataRepository.save(payloadData);
    }

    public void createBoosterData(TelemetryBoosterData boosterData) throws TelemetryDataBoosterIdException {
        if (!checkBoosterIdExist(boosterData)) {
            throw new TelemetryDataBoosterIdException();
        }
        Set<Anomaly> boosterAnomalies = boosterData.checkForAnomalies();
        this.anomalies.addAll(boosterAnomalies);
        telemetryBoosterDataRepository.save(boosterData);
    }

    public List<TelemetryRocketData> retrieveRocketData(String rocketId) throws NoTelemetryRocketDataException {

        if (!(checkRocketTelemetryDataExist(rocketId))) {
            throw new NoTelemetryRocketDataException();
        }

        return telemetryRocketDataRepository.findByRocketId(rocketId);
    }

    public List<TelemetryBoosterData> retrieveBoosterData(String boosterId) throws NoTelemetryBoosterDataException {

        if (!(checkBoosterTelemetryDataExist(boosterId))) {
            throw new NoTelemetryBoosterDataException();
        }

        return telemetryBoosterDataRepository.findByBoosterId(boosterId);
    }

    public TelemetryPayloadData retrievePayloadData(String payloadId) throws NoTelemetryPayloadDataException {
        TelemetryPayloadData result = telemetryPayloadDataRepository.findById(payloadId).get();

        if (result == null) {
            throw new NoTelemetryPayloadDataException();
        }

        return result;
    }

    private boolean checkRocketTelemetryDataExist(String rocketId) {
        return telemetryRocketDataRepository.findByRocketId(rocketId) != null;
    }

    private boolean checkBoosterTelemetryDataExist(String boosterId) {
        return telemetryBoosterDataRepository.findByBoosterId(boosterId) != null;
    }

    private boolean checkRocketIdExist(TelemetryRocketData rocketData) {
        return rocketData.getRocketId() != null;
    }

    private boolean checkBoosterIdExist(TelemetryBoosterData boosterData) {
        return boosterData.getBoosterId() != null;
    }

}
