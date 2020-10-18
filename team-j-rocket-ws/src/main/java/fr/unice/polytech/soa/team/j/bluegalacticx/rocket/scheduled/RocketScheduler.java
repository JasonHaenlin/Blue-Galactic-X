package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.graalvm.compiler.lir.CompositeValue.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RestService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;

@Component
public class RocketScheduler {

    private SpaceMetrics sm = new SpaceMetrics();

    @Autowired
    private RocketApi rocketApi;

    @Autowired
    private RestService restService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketMetricsTask() {
        sm = rocketApi.retrieveLastMetrics();
        restService.postTelemetry(sm);
    }

}
