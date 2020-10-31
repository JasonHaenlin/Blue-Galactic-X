package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RestService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;

@Component
public class RocketScheduler {

    private SpaceTelemetry sm = new SpaceTelemetry();

    @Autowired
    private RestService restService;

    // @Autowired
    // private RocketStatusProducer rocketProducer;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketTelemetryTask() {
        for (Rocket r : RocketsMocked.rockets) {
            sm = r.getLastTelemetry();
            restService.postTelemetry(sm);
            // todo : pass the rocket at arrived
            // if (sm.getDistance() <= 0) {
            // rocketProducer.donedRocketEvent(sm.getRocketId());
            // }
        }
    }

}
