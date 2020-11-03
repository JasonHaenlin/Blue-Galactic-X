package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@Service
public class TelemetryRocketProducer {

    @Value(value = "${kafka.topics.telemetryrocket}")
    private String RocketStatusTopic0;

    @Autowired
    private KafkaTemplate<String, TelemetryRocketRequest> kafkaTemplate;

    public void sendTelemetryRocketEvent(SpaceTelemetry st, String rocketId) {
        // @formatter:off
        TelemetryRocketRequest req = TelemetryRocketRequest.newBuilder()
                                            .setRocketId(rocketId)
                                            .setIrradiance(st.getIrradiance())
                                            .setVelocityVariation(st.getVelocityVariation())
                                            .setTemperature(st.getTemperature())
                                            .setVibration(st.getVibration())
                                            .setBoosterRGA(st.getBoosterRGA())
                                            .setMidRocketRGA(st.getMidRocketRGA())
                                            .setHeatShield(st.getHeatShield())
                                            .setSpeed(st.getSpeed())
                                            .setDistance(st.getDistance())
                                            .setTotalDistance(st.getTotalDistance())
                                            .build();
        // @formatter:on
        kafkaTemplate.send(RocketStatusTopic0, req);
    }

}
