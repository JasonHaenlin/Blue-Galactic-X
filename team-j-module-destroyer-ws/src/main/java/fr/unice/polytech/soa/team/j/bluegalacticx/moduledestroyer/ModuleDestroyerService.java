package fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.kafka.producers.ModuleDestructionProducer;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.kafka.consumers.AnomalyConsumer;

@Service
public class ModuleDestroyerService {

    @Autowired
    ModuleDestructionProducer moduleDestructionProducer;

    @Autowired
    AnomalyConsumer anomalyConsumer;

    public void destroyModule(AnomalyRequest req) {
        System.out.println("HELLOOOO");

        String moduleId = req.getModuleId();
        AnomalyRequest.ModuleType moduleType = req.getModuleType();
        AnomalyRequest.AnomalyType anomalyType = req.getAnomalyType();

        if( anomalyType == AnomalyRequest.AnomalyType.AWAY_FROM_TRAJECTORY ||
            anomalyType == AnomalyRequest.AnomalyType.FAILED_TO_ENTRY_BURN) {
            System.out.println("WORLDDDDDDD");
            moduleDestructionProducer.notifyModuleEmergencyDestruction(moduleId,
                    moduleType == AnomalyRequest.ModuleType.ROCKET ?
                            DestroyModuleRequest.ModuleType.ROCKET: DestroyModuleRequest.ModuleType.BOOSTER );
        }
        
    }

}
