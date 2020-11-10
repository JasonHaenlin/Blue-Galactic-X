package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterLandingStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetryData;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterNotAvailableException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterLandingStepProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.TelemetryBoosterProducer;

@Service
public class BoosterService {

    private List<Booster> boosters = new ArrayList<Booster>();

    @Autowired
    private TelemetryBoosterProducer telemetryBoosterProd;

    @Autowired
    private BoosterStatusProducer boosterStatusProducer;

    @Autowired
    private BoosterLandingStepProducer boosterLandingStepProducer;

    public void addNewBooster(Booster booster) throws CannotBeNullException {
        if (booster == null) {
            throw new CannotBeNullException("Booster");
        }
        booster.initTelemetry().initStatus();
        boosters.add(booster);
    }

    public void updateBoosterState(String boosterId, BoosterStatus boosterStatus)
            throws BoosterDoesNotExistException {
        retrieveBooster(boosterId).setStatus(boosterStatus);
    }

    public Booster retrieveBooster(String boosterId) throws BoosterDoesNotExistException {
        return boosters.stream().filter(b -> b.getId().equals(boosterId)).findFirst()
                .orElseThrow(() -> new BoosterDoesNotExistException(boosterId));
    }

    public void updateTelemetryToSend() {
        for (Booster b : boosters) {
            b.updateTelemetry();
            if (b.getStatus() == BoosterStatus.RUNNING || b.getStatus() == BoosterStatus.LANDING) {
                telemetryBoosterProd.postTelemetryEvent(new BoosterTelemetryData(b.getFuelLevel(), b.getId(),
                        b.getStatus(), b.getDistanceFromEarth(), b.getSpeed()));
            } else if (b.getStatus() == BoosterStatus.LANDED) {
                telemetryBoosterProd.postTelemetryEvent(new BoosterTelemetryData(b.getFuelLevel(), b.getId(),
                        b.getStatus(), b.getDistanceFromEarth(), b.getSpeed()));
                if (b.getStatus() != BoosterStatus.PENDING) {
                    boosterStatusProducer.notifyBoosterLanded(b.getId());
                }
                b.setStatus(BoosterStatus.PENDING);
            }
        }
    }


    public void updateAllBoostersState() {
        for (Booster b : boosters) {
            BoosterLandingStep step = b.getLandingStep();
            b.updateState();
            if(step != b.getLandingStep()){
                boosterLandingStepProducer.notifyBoosterLandingStepChanged(b.getId(), b.getMissionId(), b.getLandingStep());
            }
        }
    }

    public String getAvailableBoosterID() throws BoosterNotAvailableException {
        for (Booster b : boosters) {
            if (b.getStatus() == BoosterStatus.READY) {
                return b.getId();
            }
        }
        throw new BoosterNotAvailableException();
    }

}
