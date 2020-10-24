package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.Producer.BoosterProducer;

@Service
public class BoosterConsumer {

    @Autowired
    private BoosterService boosterService;
    @Autowired
    private BoosterProducer boosterProducer;
    private boolean throttleDown = false;

    @KafkaListener(topics = "teamj.maxq.0", groupId = "blue-origin-booster", containerFactory = "maxQkafkaListenerContainerFactory")
    public void inMaxQ(Integer inMaxQ) {
        if (inMaxQ == 1 && throttleDown == false) {
            throttleDown = true;
            boosterService.updateBoostersSpeed(-Booster.POWER_UPDATE);
            boosterProducer.updateSpeedRocket(-Booster.POWER_UPDATE);

        }

        if (throttleDown == true && inMaxQ == 0) {
            throttleDown = false;
            boosterService.updateBoostersSpeed(Booster.POWER_UPDATE);
            boosterProducer.updateSpeedRocket(Booster.POWER_UPDATE);
        }

    }

}
