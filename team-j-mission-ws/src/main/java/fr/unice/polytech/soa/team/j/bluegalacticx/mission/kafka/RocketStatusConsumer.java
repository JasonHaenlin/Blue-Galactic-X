package fr.unice.polytech.soa.team.j.bluegalacticx.mission.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.MissionService;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;

@Service
public class RocketStatusConsumer {

    @Autowired
    private MissionService missionService;

    @KafkaListener(topics = "${kafka.topics.rocketstatus}", groupId = "${kafka.group.default}", containerFactory = "rocketStatusKafkaListenerContainerFactory")
    public void rocketStatusEvent(RocketStatusRequest request) {
        String id = request.getRocketId();
        try {
            switch (request.getEventType()) {
                case IN_SERVICE:
                    missionService.updateMissionFromRocketState(MissionStatus.STARTED, id);
                    break;
                case DESTROYED:
                    missionService.updateMissionFromRocketState(MissionStatus.FAILED, id);
                    break;
                case AT_BASE:
                    missionService.updateMissionFromRocketState(MissionStatus.PENDING, id);
                    break;
                case DONED:
                    missionService.updateMissionFromRocketState(MissionStatus.SUCCESSFUL, id);
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
