package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest.SpaceCoordinate;

@Service
public class TelemetryPayloadProducer {

    @Value(value = "${kafka.topics.telemetrypayload}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, TelemetryPayloadRequest> kafkaTemplate;

    public void notifyDeployedPayloadEvent(Payload p) {
        // Need to retrieve real telemetry from the payload
        // @formatter:off
        TelemetryPayloadRequest req = TelemetryPayloadRequest.newBuilder()
                                .setPayloadId(p.getPayloadId())
                                .setPayloadStatus(p.getStatus().toString())
                                .setPosition(SpaceCoordinate.newBuilder()
                                    .setX(p.getPosition().getX())
                                    .setY(p.getPosition().getY())
                                    .setZ(p.getPosition().getZ())
                                    .build())
                                .build();
        // @formatter:on
        kafkaTemplate.send(statusTopic0, req);
    }

}
