package fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.PayloadService;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers.PayloadStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;

@Service
public class RocketStatusConsumer {

    @Autowired
    private PayloadService payloadService;

    @Autowired
    private PayloadStatusProducer payloadStatusProducer;

    @KafkaListener(topics = "${kafka.topics.rocketstatus}", groupId = "${kafka.group.default}", containerFactory = "rocketStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(RocketStatusRequest request) {
        String id = request.getRocketId();
        try {
            switch (request.getEventType()) {
                case IN_SERVICE:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.ON_MISSION, id);
                    Payload p = payloadService.retrievePayloadByRocketId(id);
                    payloadStatusProducer.notifyOnMissionPayloadEvent(p.getId(), p.getMissionId());

                    break;
                case DESTROYED:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.DESTROYED, id);
                    break;
                case AT_BASE:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.NOT_DELIVERED, id);
                    break;
                case DONE:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.IN_ROLLOUT, id);
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
