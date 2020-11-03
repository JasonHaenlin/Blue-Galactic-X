package fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.kafka.producers.ModuleDestructionProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest.ModuleType;

@Service
public class ModuleDestroyerService {

    @Autowired
    ModuleDestructionProducer moduleDestructionProducer;

    void destroyModule() {
        moduleDestructionProducer.notifyModuleEmergencyDestruction("1", ModuleType.ROCKET);
    }

}
