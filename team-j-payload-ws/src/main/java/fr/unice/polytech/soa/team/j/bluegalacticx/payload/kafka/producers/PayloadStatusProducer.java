package fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest.EventType;

@Service
public class PayloadStatusProducer {

    @Value(value = "${kafka.topics.payloadstatus}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, PayloadStatusRequest> kafkaTemplate;

    public void notifyDeployedPayloadEvent(String payloadId, String rocketId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId).setRocketId(rocketId)
                .setEventType(EventType.DELIVERED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

}
