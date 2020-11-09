package fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers;

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

    public void notifyDeployedPayloadEvent(String payloadId, String missionId) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(payloadId).setMissionId(missionId)
                .setEventType(PayloadStatus.IN_ROLLOUT).build();

        kafkaTemplate.send(statusTopic0, req);
    }

}
