package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.SpeedChange;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks.BoostersMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MaxQRequest;

@Service
public class MaxQConsumer {

    @KafkaListener(topics = "${kafka.topics.maxq}", groupId = "${kafka.group.default}", containerFactory = "maxQKafkaListenerContainerFactory")
    public void maxQEvent(MaxQRequest request) {
        String id = request.getBoosterId();
        switch (request.getEventType()) {
            case ENTER_MAXQ:
                BoostersMocked.find(id).get().updateSpeed(SpeedChange.DECREASE);
                break;
            case QUIT_MAXQ:
                BoostersMocked.find(id).get().updateSpeed(SpeedChange.INCREASE);
                break;
            default:
                break;
        }

    }
}
