package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BoosterProducer {

    @Autowired
    private KafkaTemplate<String, Double> kafkaTemplate;

    public void updateSpeedRocket(double newSpeed) {
        kafkaTemplate.send("teamj.booster-speed-update.0", newSpeed);
    }

}
