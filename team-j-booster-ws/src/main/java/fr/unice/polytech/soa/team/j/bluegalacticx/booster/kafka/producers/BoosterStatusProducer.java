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

    public void notifyBoosterPending(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.PENDING).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void destroyedBoosterEvent(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.DESTROYED).build();
        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyBoosterReady(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.READY).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyBoosterRunning(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.RUNNING).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyBoosterLanding(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.LANDING).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyBoosterDroped(String boosterId) {
        BoosterStatusRequest req = BoosterStatusRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.DROPED).build();

        kafkaTemplate.send(statusTopic0, req);
    }


}
