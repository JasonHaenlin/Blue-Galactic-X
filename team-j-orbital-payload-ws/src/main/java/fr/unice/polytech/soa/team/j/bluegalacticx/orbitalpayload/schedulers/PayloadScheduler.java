package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.OrbitalPayloadService;

@Component
public class PayloadScheduler {

    @Autowired
    OrbitalPayloadService orbitalPayloadService;

    @Scheduled(fixedDelay = 1000)
    public void schedulePayloadTelemetryTask() {
        orbitalPayloadService.sendNewTelemetry();
    }
}
