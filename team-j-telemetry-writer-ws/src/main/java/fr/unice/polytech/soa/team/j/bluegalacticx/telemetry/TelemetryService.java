package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryBoosterDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryPayloadDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories.TelemetryRocketDataRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@Service
public class TelemetryService {

    @Autowired
    private TelemetryRocketDataRepository telemetryRocketDataRepository;

    @Autowired
    private TelemetryPayloadDataRepository telemetryPayloadDataRepository;

    @Autowired
    private TelemetryBoosterDataRepository telemetryBoosterDataRepository;

    public void createRocketData(TelemetryRocketRequest rocketData) throws TelemetryDataRocketIdException {
        String rocketId = rocketData.getRocketId();
        if (rocketId == null) {
            throw new TelemetryDataRocketIdException();
        }
        TelemetryRocketData rocketSavedData = telemetryRocketDataRepository.findByRocketId(rocketId)
                .orElse(new TelemetryRocketData(rocketId));
        rocketSavedData.addMeasurementFromRequest(rocketData);
        telemetryRocketDataRepository.save(rocketSavedData);
    }

    public void createPayloadData(TelemetryPayloadRequest payloadData) throws TelemetryDataPayloadIdException {
        String payloadId = payloadData.getPayloadId();
        if (payloadId == null) {
            throw new TelemetryDataPayloadIdException();
        }
        TelemetryPayloadData payloadSavedData = telemetryPayloadDataRepository.findByPayloadId(payloadId)
                .orElse(new TelemetryPayloadData(payloadId));
        payloadSavedData.addMeasurementFromRequest(payloadData);
        telemetryPayloadDataRepository.save(payloadSavedData);
    }

    public void createBoosterData(TelemetryBoosterRequest boosterData) throws TelemetryDataBoosterIdException {
        String boosterId = boosterData.getBoosterId();
        if (boosterId == null) {
            throw new TelemetryDataBoosterIdException();
        }
        TelemetryBoosterData boosterSavedData = telemetryBoosterDataRepository.findByBoosterId(boosterId)
                .orElse(new TelemetryBoosterData(boosterId));
        boosterSavedData.addMeasurementFromRequest(boosterData);
        telemetryBoosterDataRepository.save(boosterSavedData);
    }

}
