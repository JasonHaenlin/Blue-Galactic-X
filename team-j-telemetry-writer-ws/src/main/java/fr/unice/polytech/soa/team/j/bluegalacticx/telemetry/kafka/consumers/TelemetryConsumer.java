package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.TelemetryService;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@Service
public class TelemetryConsumer {

    @Autowired
    private TelemetryService telemetryService;

    @KafkaListener(topics = "${kafka.topics.telemetrybooster}", groupId = "${kafka.group.default}", containerFactory = "telemetryBoosterKafkaListenerContainerFactory")
    public void recordBoosterTelemetry(TelemetryBoosterRequest req) throws TelemetryDataBoosterIdException {
        telemetryService.createBoosterData(req);
    }

    @KafkaListener(topics = "${kafka.topics.telemetrypayload}", groupId = "${kafka.group.default}", containerFactory = "telemetryPayloadKafkaListenerContainerFactory")
    public void recordPayloadTelemetry(TelemetryPayloadRequest req) throws TelemetryDataPayloadIdException {
        telemetryService.createPayloadData(req);
    }

    @KafkaListener(topics = "${kafka.topics.telemetryrocket}", groupId = "${kafka.group.default}", containerFactory = "telemetryRocketKafkaListenerContainerFactory")
    public void recordRocketTelemetry(TelemetryRocketRequest req) throws TelemetryDataRocketIdException {
        telemetryService.createRocketData(req);
    }

}
