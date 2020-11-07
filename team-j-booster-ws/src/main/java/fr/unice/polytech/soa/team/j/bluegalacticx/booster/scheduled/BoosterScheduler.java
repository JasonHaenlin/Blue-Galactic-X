package fr.unice.polytech.soa.team.j.bluegalacticx.booster.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;

@Component
public class BoosterScheduler {

    @Autowired
    private BoosterService boosterService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleBoosterMetricsTask() {
        boosterService.updateAllBoostersState();
        boosterService.updateTelemetryToSend();

    }

}
