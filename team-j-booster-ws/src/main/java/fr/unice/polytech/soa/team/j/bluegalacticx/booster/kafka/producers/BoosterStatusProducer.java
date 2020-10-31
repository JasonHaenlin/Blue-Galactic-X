package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterStatusRequest.EventType;

@Service
public class BoosterStatusProducer {

    @Value(value = "${kafka.topics.boosterstatus}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, BoosterStatusRequest> kafkaTemplate;

    public void notifyBoosterLanded(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.LANDED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

}
