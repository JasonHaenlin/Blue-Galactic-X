package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;

@Service
public class PayloadConsumer {

    @Autowired
    private PayloadService payloadService;

    @KafkaListener(topics = "${kafka.topics.rocketstatus}", groupId = "${kafka.group.default}", containerFactory = "rocketStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(RocketStatusRequest request) {
        String id = request.getRocketId();
        try {
            switch (request.getEventType()) {
                case IN_SERVICE:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.ON_MISSION, id);
                    break;
                case DESTROYED:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.DESTROYED, id);
                    break;
                case AT_BASE:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.NOT_DELIVERED, id);
                    break;
                case DONED:
                    payloadService.updatePayloadFromRocketState(PayloadStatus.DELIVERED, id);
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
