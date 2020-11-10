package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.consumers;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketRPCService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DestructionOrderRequest;
import io.grpc.StatusException;
import io.grpc.internal.testing.StreamRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ModuleDestroyerConsumer {

    @Autowired
    private RocketRPCService rocketRPCService;

    @KafkaListener(topics = "${kafka.topics.moduledestruction}", groupId = "${kafka.group.default}", containerFactory = "ModuleDestroyerKafkaListenerContainerFactory")
    public void destroy(DestroyModuleRequest req) {
        if (req.getModuleType().equals(DestroyModuleRequest.ModuleType.ROCKET)){
            DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setRocketId(req.getModuleId()).build();
            StreamRecorder<DesctructionOrderReply> responseObserver = StreamRecorder.create();
            rocketRPCService.destructionOrderOnRocket(request, responseObserver);
            if (responseObserver.getError()!= null){
                StatusException t = (StatusException) responseObserver.getError();
                t.printStackTrace();
            }
        }
    }
}
