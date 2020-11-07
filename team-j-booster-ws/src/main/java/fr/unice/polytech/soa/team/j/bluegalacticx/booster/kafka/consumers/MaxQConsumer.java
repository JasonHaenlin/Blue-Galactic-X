package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.SpeedChange;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MaxQRequest;

@Service
public class MaxQConsumer {

    @Autowired
    BoosterService boosterService;

    @KafkaListener(topics = "${kafka.topics.maxq}", groupId = "${kafka.group.default}", containerFactory = "maxQKafkaListenerContainerFactory")
    public void maxQEvent(MaxQRequest request) throws BoosterDoesNotExistException {
        String id = request.getBoosterId();
        switch (request.getEventType()) {
            case ENTER_MAXQ:
                boosterService.retrieveBooster(id).updateSpeed(SpeedChange.DECREASE);
                break;
            case QUIT_MAXQ:
                boosterService.retrieveBooster(id).updateSpeed(SpeedChange.INCREASE);
                break;
            default:
                break;
        }

    }
}
