package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.OrbitalPayloadService;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;

@Service
public class RocketStatusConsumer {

    @Autowired
    private OrbitalPayloadService payloadService;

    @KafkaListener(topics = "${kafka.topics.payloadstatus}", groupId = "${kafka.group.default}", containerFactory = "payloadStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(PayloadStatusRequest request) {
        String id = request.getPayloadId();
        try {
            switch (request.getEventType()) {
                case IN_ROLLOUT:
                    payloadService.activateTelemetryRecorder(id);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }
        } catch (PayloadNotFoundException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }

    }
}
