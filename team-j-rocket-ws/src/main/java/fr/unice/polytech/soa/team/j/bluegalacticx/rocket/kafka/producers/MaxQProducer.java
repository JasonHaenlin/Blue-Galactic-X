package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MaxQRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MaxQRequest.EventType;

@Service
public class MaxQProducer {

    @Value(value = "${kafka.topics.maxq}")
    private String MaxQTopic0;

    @Autowired
    private KafkaTemplate<String, MaxQRequest> kafkaTemplate;

    public void sendInMaxQ(String boosterId) {
        MaxQRequest req = MaxQRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.ENTER_MAXQ).build();

        kafkaTemplate.send(MaxQTopic0, req);
    }

    public void sendQuitMaxQ(String boosterId) {
        MaxQRequest req = MaxQRequest.newBuilder().setBoosterId(boosterId)
                .setEventType(EventType.QUIT_MAXQ).build();

        kafkaTemplate.send(MaxQTopic0, req);
    }

   
}