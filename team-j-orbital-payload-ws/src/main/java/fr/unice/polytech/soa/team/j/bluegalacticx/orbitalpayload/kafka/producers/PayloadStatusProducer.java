package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;

@Service
public class PayloadStatusProducer {

    @Value(value = "${kafka.topics.payloadstatus}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, PayloadStatusRequest> kafkaTemplate;

    public void notifyDeployedPayloadDelivered(String payloadId, String missionId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId)
                .setEventType(PayloadStatus.DELIVERED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

    public void notifyDeployedPayloadNotDelivered(String payloadId, String missionId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId)
                .setEventType(PayloadStatus.NOT_DELIVERED).build();

        kafkaTemplate.send(statusTopic0, req);
    }

}
