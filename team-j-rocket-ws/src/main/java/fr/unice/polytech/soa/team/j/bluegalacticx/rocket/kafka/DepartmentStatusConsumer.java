package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

@Service
public class DepartmentStatusConsumer {

    @Autowired
    private RocketService rocketService;

    @KafkaListener(topics = "${kafka.topics.departmentstatus}", groupId = "${kafka.group.default}", containerFactory = "DepartmentStatusKafkaListenerContainerFactory")
    public void updateGoNogo(GoNogoRequest req) {
        if (req.getDepartment() == Department.MISSION) {
            try {
                rocketService.updateGoNogoForRocket(req.getIdValue(), req.getStatus());
            } catch (RocketDestroyedException | RocketDoesNotExistException e) {
                // TODO handle by kafka
                e.printStackTrace();
            }
        }
    }
}
