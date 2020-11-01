package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

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

    public TelemetryRocketData retrieveRocketData(String rocketId) throws NoTelemetryRocketDataException {
        TelemetryRocketData data = telemetryRocketDataRepository.findByRocketId(rocketId).orElse(null);
        if (data == null) {
            throw new NoTelemetryRocketDataException();
        }
        return data;
    }

    public TelemetryBoosterData retrieveBoosterData(String boosterId) throws NoTelemetryBoosterDataException {
        TelemetryBoosterData data = telemetryBoosterDataRepository.findByBoosterId(boosterId).orElse(null);
        if (data == null) {
            throw new NoTelemetryBoosterDataException();
        }
        return data;
    }

    public TelemetryPayloadData retrievePayloadData(String payloadId) throws NoTelemetryPayloadDataException {
        TelemetryPayloadData data = telemetryPayloadDataRepository.findByPayloadId(payloadId).orElse(null);
        if (data == null) {
            throw new NoTelemetryPayloadDataException();
        }
        return data;
    }

}
