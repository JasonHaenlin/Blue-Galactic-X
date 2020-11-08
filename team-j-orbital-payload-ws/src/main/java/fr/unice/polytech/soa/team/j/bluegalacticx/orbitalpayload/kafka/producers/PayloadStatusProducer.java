package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.EventType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;

@Service
public class PayloadStatusProducer {

    @Value(value = "${kafka.topics.payloadstatus}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, PayloadStatusRequest> kafkaTemplate;

    public void notifyDeployedPayloadDelivered(String payloadId, String rocketId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId).setRocketId(rocketId)
                .setEventType(EventType.DELIVERED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyDeployedPayloadNotDelivered(String payloadId, String rocketId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId).setRocketId(rocketId)
                .setEventType(EventType.NOT_DELIVERED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

}
