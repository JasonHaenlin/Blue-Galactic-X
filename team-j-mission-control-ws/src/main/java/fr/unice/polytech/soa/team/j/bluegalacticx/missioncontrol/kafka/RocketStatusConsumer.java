package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;

@Service
public class RocketStatusConsumer {

    @Autowired
    private MissionControlService missionControlService;

    @KafkaListener(topics = "${kafka.topics.rocketstatus}", groupId = "${kafka.group.default}", containerFactory = "rocketStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(RocketStatusRequest request) {
        String id = request.getRocketId();
        try {
            switch (request.getEventType()) {
                case IN_SERVICE:    
                    missionControlService.storeRocketStatus(id, RocketStatus.IN_SERVICE);
                    missionControlService.updateMissionFromRocketState(id);
                    break;
                case DESTROYED:
                    missionControlService.storeRocketStatus(id, RocketStatus.DESTROYED);
                    missionControlService.updateMissionFromRocketState(id);
                    break;
                case DONED:
                    missionControlService.storeRocketStatus(id, RocketStatus.ARRIVED);
                    missionControlService.updateMissionFromRocketState(id);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }
            
        } catch (RocketDoesNotExistException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }
    }

}
