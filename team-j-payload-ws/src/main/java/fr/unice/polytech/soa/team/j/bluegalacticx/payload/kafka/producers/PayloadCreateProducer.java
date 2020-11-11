package fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadRequest.SpaceCoordinate;

@Service
public class PayloadCreateProducer {

    @Value(value = "${kafka.topics.newpayload}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, PayloadRequest> kafkaTemplate;

    public void notifyNewPayload(Payload p) {
        PayloadRequest req = PayloadRequest.newBuilder().setPayloadId(p.getId()).setPayloadStatus(p.getStatus())
                .setPosition(SpaceCoordinate.newBuilder().setX(p.getPosition().getX()).setY(p.getPosition().getY())
                        .setZ(p.getPosition().getZ()).build())
                .build();

        kafkaTemplate.send(statusTopic0, req);
    }
}
