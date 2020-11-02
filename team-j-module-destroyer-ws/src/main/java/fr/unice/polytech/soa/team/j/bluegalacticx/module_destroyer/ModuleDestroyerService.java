package fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer.kafka.producers.ModuleDestructionProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.module_destroyer.proto.DestroyModuleRequest.ModuleType;

@Service
public class ModuleDestroyerService {

    @Autowired
    ModuleDestructionProducer moduleDestructionProducer;

    // @Autowired

    void destroyModule() {
        moduleDestructionProducer.notifyModuleEmergencyDestruction("1", ModuleType.ROCKET);
    }

}
