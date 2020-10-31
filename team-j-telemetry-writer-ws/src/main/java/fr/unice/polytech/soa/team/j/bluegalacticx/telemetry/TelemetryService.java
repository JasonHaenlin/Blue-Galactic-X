package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryBoosterDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryPayloadDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.NoTelemetryRocketDataException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryBoosterDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryPayloadDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryRocketDataRepository;

@Service
public class TelemetryService {

    @Autowired
    private TelemetryRocketDataRepository telemetryRocketDataRepository;

    @Autowired
    private TelemetryPayloadDataRepository telemetryPayloadDataRepository;

    @Autowired
    private TelemetryBoosterDataRepository telemetryBoosterDataRepository;


    public void createRocketData(TelemetryRocketData rocketData) throws TelemetryDataRocketIdException {
        if (!checkRocketIdExist(rocketData)) {
            throw new TelemetryDataRocketIdException();
        }
        telemetryRocketDataRepository.save(rocketData);
    }

    public void createPayloadData(TelemetryPayloadData payloadData) throws TelemetryDataPayloadIdException {
        if (payloadData.getPayloadId() == null) {
            throw new TelemetryDataPayloadIdException();
        }
        telemetryPayloadDataRepository.save(payloadData);
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
