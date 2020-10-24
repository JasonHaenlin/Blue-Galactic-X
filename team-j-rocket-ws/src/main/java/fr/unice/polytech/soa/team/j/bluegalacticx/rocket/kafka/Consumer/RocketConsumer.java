package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketApi;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;

@Service
public class RocketConsumer {

    @Autowired
    private RocketApi rocketApi;

    @KafkaListener(topics = "teamj.booster-speed-update.0", groupId = "blue-origin-rocket", containerFactory = "maxQkafkaListenerContainerFactory")
    public void updateSpeed(double pourcentageIncreaseOrDecrease) {
        SpaceMetrics rocketM = rocketApi.retrieveLastMetrics();
        double newSpeed = rocketM.getSpeed() + (rocketM.getSpeed() * pourcentageIncreaseOrDecrease);
        rocketM.setSpeed(newSpeed);
    }

}
