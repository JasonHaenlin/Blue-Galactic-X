package fr.unice.polytech.soa.team.j.bluegalacticx.booster.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.RestService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetryData;

@Component
public class BoosterScheduler {

    @Autowired
    private BoosterService boosterService;

    @Autowired
    private BoosterApi boosterApi;

    @Autowired
    private RestService restService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleBoosterMetricsTask() {
        boosterService.updateAllBoostersState();
        List<Booster> boosters = boosterApi.updateBoosterMetricsAndRetrieve();
        for(Booster b : boosters){
            if(b.getStatus() == BoosterStatus.RUNNING || b.getStatus() == BoosterStatus.LANDING || b.getStatus() == BoosterStatus.LANDED){
                restService.postTelemetry(new BoosterTelemetryData(b.getFuelLevel(), b.getId(), b.getStatus(), b.getDistanceFromEarth(), b.getSpeed()));
            }
        }
    }

}
