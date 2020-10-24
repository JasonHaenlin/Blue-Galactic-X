package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.Producer.BoosterProducer;

@Service
public class BoosterConsumer {

    private boolean throttleDown=false;
    // Reduce or increase power by 20%
    private static final double POWER=0.2;
    @Autowired 
    private BoosterService boosterService;
    @Autowired 
    private BoosterProducer boosterProducer;

    @KafkaListener(topics = "maxQ", groupId = "blue-origin-booster", containerFactory = "maxQkafkaListenerContainerFactory")
    public void inMaxQ(Integer inMaxQ) {
        if(inMaxQ==1 && throttleDown==false){
            throttleDown=true;
            boosterService.updateBoostersSpeed(POWER);
            boosterProducer.updateSpeedRocket(POWER);
            System.out.println("Decrease the power of the boosters....");
            
        }

        if(throttleDown==true && inMaxQ==0){
            throttleDown=false;
            boosterService.updateBoostersSpeed(-POWER);
            boosterProducer.updateSpeedRocket(-POWER);
            System.out.println("Increase the power of the boosters....");
        }

    }
    
}

