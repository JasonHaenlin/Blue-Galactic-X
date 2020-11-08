package fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.kafka.consumers;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.ModuleDestroyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AnomalyConsumer {

    @Autowired
    private ModuleDestroyerService moduleDestroyerService;

    @KafkaListener(topics = "${kafka.topics.anomaly}", groupId = "${kafka.group.default}", containerFactory = "anomalyKafkaListenerContainerFactory")
    public void reactToAnomaly(AnomalyRequest req) {
        moduleDestroyerService.destroyModule(req);
    }
    
}
