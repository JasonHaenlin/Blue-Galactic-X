package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.BoosterDoesNotExistException;

@Service
public class BoosterStatusConsumer {

    @Autowired
    private MissionControlService missionControlService;

    @KafkaListener(topics = "${kafka.topics.boosterstatus}", groupId = "${kafka.group.default}", containerFactory = "boosterStatusKafkaListenerContainerFactory")
    public void boosterStatusEvent(BoosterStatusRequest request) {
        String id = request.getBoosterId();
        try {
            switch (request.getEventType()) {
                case RUNNING:
                    missionControlService.storeBoosterStatus(id, BoosterStatus.RUNNING);
                    missionControlService.updateMissionFromBoosterState(id);
                    break;
                case DESTROYED:
                    missionControlService.storeBoosterStatus(id, BoosterStatus.DESTROYED);
                    missionControlService.updateMissionFromBoosterState(id);
                    break;
                case LANDED:
                    missionControlService.storeBoosterStatus(id, BoosterStatus.LANDED);
                    missionControlService.updateMissionFromBoosterState(id);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }

            
        } catch (BoosterDoesNotExistException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }

    }

}
