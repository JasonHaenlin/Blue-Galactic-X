package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RestService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.MaxQ;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.Producer.RocketProducer;

@Component
public class RocketScheduler {

    private SpaceMetrics sm = new SpaceMetrics();

    @Autowired
    private RocketApi rocketApi;

    @Autowired
    private RestService restService;

    @Autowired 
    private RocketProducer rocketProducer;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketMetricsTask() {
        sm = rocketApi.retrieveLastMetrics();
        sendMaxQ(sm);
        restService.postTelemetry(sm);
    }


    private void sendMaxQ(SpaceMetrics sm){
        if(sm.getSpeed()>0){
            if((sm.getDistance() >= MaxQ.MIN && sm.getDistance() <= MaxQ.MAX) ){
                rocketProducer.sendInMaxQ();
            }else{
                rocketProducer.sendNotInMaxQ();
            }
        }
    }

}
