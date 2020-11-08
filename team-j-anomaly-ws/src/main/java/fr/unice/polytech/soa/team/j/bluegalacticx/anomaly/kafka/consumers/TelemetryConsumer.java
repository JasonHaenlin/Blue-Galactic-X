package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.AnomaliesCentreService;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.exceptions.NoBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.exceptions.NoPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.exceptions.NoRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@Service
public class TelemetryConsumer {

    @Autowired
    private AnomaliesCentreService anomaliesCentreService;

    @KafkaListener(topics = "${kafka.topics.telemetrybooster}", groupId = "${kafka.group.default}", containerFactory = "telemetryBoosterKafkaListenerContainerFactory")
    public void recordBoosterTelemetry(TelemetryBoosterRequest req) throws NoBoosterIdException {
        anomaliesCentreService.checkBoosterTelemetry(req);
    }

    @KafkaListener(topics = "${kafka.topics.telemetrypayload}", groupId = "${kafka.group.default}", containerFactory = "telemetryPayloadKafkaListenerContainerFactory")
    public void recordPayloadTelemetry(TelemetryPayloadRequest req) throws NoPayloadIdException {
        anomaliesCentreService.checkPayloadTelemetry(req);
    }

    @KafkaListener(topics = "${kafka.topics.telemetryrocket}", groupId = "${kafka.group.default}", containerFactory = "telemetryRocketKafkaListenerContainerFactory")
    public void recordRocketTelemetry(TelemetryRocketRequest req) throws NoRocketIdException {
        anomaliesCentreService.checkRocketTelemetry(req);
    }

}
