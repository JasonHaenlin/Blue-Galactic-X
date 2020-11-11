package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterLandingStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterLandingStepRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterLandingStepRequest.EventType;

@Service
public class BoosterLandingStepProducer {

    @Value(value = "${kafka.topics.boosterlandingstep}")
    private String landingStepTopic0;

    @Autowired
    private KafkaTemplate<String, BoosterLandingStepRequest> kafkaTemplate;

    public void notifyBoosterLandingStepChanged(String boosterId, String missionId, BoosterLandingStep step) {
       BoosterLandingStepRequest req = BoosterLandingStepRequest.newBuilder().setBoosterId(boosterId).setMissionId(missionId)
               .setEventType(EventType.values()[step.ordinal()]).build();

       kafkaTemplate.send(landingStepTopic0, req);
    }
    
}
