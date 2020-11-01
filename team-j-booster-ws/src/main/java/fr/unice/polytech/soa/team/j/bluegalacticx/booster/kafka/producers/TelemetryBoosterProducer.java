package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetryData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;

@Service
public class TelemetryBoosterProducer {

    @Value(value = "${kafka.topics.telemetrybooster}")
    private String statusTopic0;

    @Autowired
    private KafkaTemplate<String, TelemetryBoosterRequest> kafkaTemplate;

    public void postTelemetryEvent(BoosterTelemetryData data) {
        // @formatter:off
        TelemetryBoosterRequest req = TelemetryBoosterRequest.newBuilder()
                        .setBoosterId(data.getBoosterId())
                        .setBoosterStatus(data.getBoosterStatus().toString())
                        .setSpeed(data.getSpeed())
                        .setFuel(data.getFuel())
                        .setDistanceFromEarth(data.getDistanceFromEarth())
                        .build();
        // @formatter:on
        kafkaTemplate.send(statusTopic0, req);
    }

}
