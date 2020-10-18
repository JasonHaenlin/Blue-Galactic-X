package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RestService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;

@Component
public class RocketScheduler {

    private final static Logger LOG = LoggerFactory.getLogger(RocketScheduler.class);

    private SpaceMetrics sm = new SpaceMetrics();

    @Autowired
    private RocketApi rocketApi;

    @Autowired
    private RestService restService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketMetricsTask() {
        sm = rocketApi.retrieveLastMetrics();
        //LOG.info("Rocket Telemetry : " + sm);
        restService.postTelemetry(sm);
    }

}
