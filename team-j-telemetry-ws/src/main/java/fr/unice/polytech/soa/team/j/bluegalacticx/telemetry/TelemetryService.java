package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryBoosterDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db.TelemetryRocketDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.mocks.AnomaliesMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryBoosterDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;

@Service
public class TelemetryService {

    private List<Anomaly> anomalies = AnomaliesMocked.anomalies;

    @Autowired
    private TelemetryRocketDataRepository telemetryRocketDataRepository;

    @Autowired
    private TelemetryBoosterDataRepository telemetryBoosterDataRepository;

    /**
     * for now, the first call, we do not send back any anomalies but the second
     * time yes
     */
    private static boolean sendAnomalies = false;

    public List<Anomaly> checkRecordedAnomalies() {
        if (sendAnomalies == false) {
            sendAnomalies = true;
            return Collections.emptyList();
        }
        return anomalies;
    }

    public void createRocketData(TelemetryRocketData rocketData) throws TelemetryDataRocketIdException {
        if (!checkRocketIdExist(rocketData)) {
            throw new TelemetryDataRocketIdException();
        }
        telemetryRocketDataRepository.save(rocketData);
    }

    public void createBoosterData(TelemetryBoosterData boosterData) throws TelemetryDataBoosterIdException {
        if (!checkBoosterIdExist(boosterData)) {
            throw new TelemetryDataBoosterIdException();
        }
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
