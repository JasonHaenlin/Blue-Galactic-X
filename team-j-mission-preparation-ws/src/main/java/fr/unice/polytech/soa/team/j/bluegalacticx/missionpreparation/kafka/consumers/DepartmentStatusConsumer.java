package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.MissionPreparationService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

@Service
public class DepartmentStatusConsumer {

    @Autowired
    private MissionPreparationService missionService;

    @KafkaListener(topics = "${kafka.topics.departmentstatus}", groupId = "${kafka.group.default}", containerFactory = "DepartmentStatusKafkaListenerContainerFactory")
    public void updateGoNogo(GoNogoRequest req) {
        if (req.getDepartment() == Department.MISSION) {
            return;
        }

        if (req.getIdNull()) {
            missionService.updateMissionGoNogo(req.getDepartment(), req.getStatus());
        } else {
            try {
                missionService.updateMissionGoNogo(req.getDepartment(), req.getStatus(), req.getIdValue());
            } catch (RocketDoesNotExistException e) {
                // TODO handle exception in kafka
                e.printStackTrace();
            }
        }
    }
}
