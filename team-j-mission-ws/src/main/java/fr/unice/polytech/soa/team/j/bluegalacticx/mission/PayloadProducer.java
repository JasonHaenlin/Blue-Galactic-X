package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.PayloadStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.PayloadStatusRequest.PayloadStatus;

@Service
public class PayloadProducer {

    @Autowired
    private KafkaTemplate<String, PayloadStatusRequest> kafkaTemplate;

    public void updatePayloadStatus(String id, PayloadStatus status) {
        PayloadStatusRequest req = PayloadStatusRequest.newBuilder().setPayloadId(id).setStatus(status).build();
        kafkaTemplate.send("payload", req);
    }

}
