package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.consumers;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;

@Service
public class RocketStatusConsumer {

    @Autowired
    private BoosterService boosterService;

    @KafkaListener(topics = "${kafka.topics.rocketstatus}", groupId = "${kafka.group.default}", containerFactory = "rocketStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(RocketStatusRequest request) {
        String id = request.getBoosterId();
        try {
            switch (request.getEventType()) {
                case READY_TO_LAUNCH:
                    boosterService.updateBoosterState(id, BoosterStatus.READY);
                    break;
                case IN_SERVICE:
                    boosterService.updateBoosterState(id, BoosterStatus.RUNNING);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }

        } catch (BoosterDoesNotExistException e) {
            e.printStackTrace();
        }
    }

}
