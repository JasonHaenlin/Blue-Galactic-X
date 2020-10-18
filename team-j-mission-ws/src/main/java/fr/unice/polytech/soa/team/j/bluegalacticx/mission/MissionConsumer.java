package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionStatusRequest.MissionStatus;

@Service
public class MissionConsumer {

    @Autowired
    private MissionService missionService;

    @KafkaListener(topics = "mission", groupId = "blue-origin-mission", containerFactory = "missionStatuskafkaListenerContainerFactory")
    public void mission(MissionStatusRequest request) {
        String id = request.getMissionId();
        MissionStatus status = request.getStatus();
        try {
            missionService.setMissionStatus(status, id);
        } catch (MissionDoesNotExistException | BadPayloadIdException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }
    }

}
