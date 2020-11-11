package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;

@Service
public class ModuleDestroyerConsumer {

    @Autowired
    private RocketService rocketService;

    @Autowired
    private RocketStatusProducer rocketProducer;

    @KafkaListener(topics = "${kafka.topics.moduledestruction}", groupId = "${kafka.group.default}", containerFactory = "ModuleDestroyerKafkaListenerContainerFactory")
    public void destroy(DestroyModuleRequest req) {
        if (req.getModuleType() == DestroyModuleRequest.ModuleType.ROCKET) {
            try {
                Rocket r = rocketService.retrieveRocket(req.getModuleId());
                r.initiateTheSelfDestructSequence();
                rocketProducer.destroyedRocketEvent(r.getId());
            } catch (RocketDoesNotExistException | RocketDestroyedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
