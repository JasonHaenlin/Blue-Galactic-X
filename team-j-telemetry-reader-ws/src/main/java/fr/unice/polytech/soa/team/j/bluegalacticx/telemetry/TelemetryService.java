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

    public List<TelemetryRocketData> retrieveRocketData(String rocketId) throws NoTelemetryRocketDataException {
        List<TelemetryRocketData> data = telemetryRocketDataRepository.findAllByRocketId(rocketId);
        if (data.size() <= 0) {
            throw new NoTelemetryRocketDataException();
        }
        return data;
    }

    public List<TelemetryBoosterData> retrieveBoosterData(String boosterId) throws NoTelemetryBoosterDataException {
        List<TelemetryBoosterData> data = telemetryBoosterDataRepository.findAllByBoosterId(boosterId);
        if (data.size() <= 0) {
            throw new NoTelemetryBoosterDataException();
        }
        return data;
    }

    public List<TelemetryPayloadData> retrievePayloadData(String payloadId) throws NoTelemetryPayloadDataException {
        List<TelemetryPayloadData> data = telemetryPayloadDataRepository.findAllByPayloadId(payloadId);
        if (data.size() <= 0) {
            throw new NoTelemetryPayloadDataException();
        }
        return data;
    }

}
