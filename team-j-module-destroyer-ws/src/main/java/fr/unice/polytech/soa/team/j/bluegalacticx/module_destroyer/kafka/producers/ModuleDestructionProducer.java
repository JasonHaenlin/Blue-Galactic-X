package fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer.proto.DestroyModuleRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer.proto.DestroyModuleRequest.ModuleType;

@Service
public class ModuleDestructionProducer {

    @Value(value = "${kafka.topics.moduledestruction}")
    private String moduleDestructionTopic0;

    @Autowired
    private KafkaTemplate<String, DestroyModuleRequest> KafkaTemplate;

    public void notifyModuleEmergencyDestruction(String moduleId, ModuleType moduleType) {
        DestroyModuleRequest req = DestroyModuleRequest.newBuilder().setModuleType(moduleType).setModuleId(moduleId)
                .build();
        KafkaTemplate.send(moduleDestructionTopic0, req);
    }
}
