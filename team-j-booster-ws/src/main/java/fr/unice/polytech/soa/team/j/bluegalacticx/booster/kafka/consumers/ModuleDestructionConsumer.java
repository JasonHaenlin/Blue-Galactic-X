package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.consumers;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterRPCService;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.DestructionOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ModuleDestructionConsumer {
    private final static Logger LOG = LoggerFactory.getLogger(BoosterRPCService.class);

    @Autowired
    private BoosterRPCService boosterRPCService;

    @KafkaListener(topics = "${kafka.topics.moduledestruction}", groupId = "${kafka.group.default}", containerFactory = "ModuleDestructionKafkaListenerContainerFactory")
    public void destroy(DestroyModuleRequest req) {
        if (req.getModuleType() == DestroyModuleRequest.ModuleType.ROCKET){
            DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setBoosterId(req.getModuleId()).build();
            StreamObserver<DesctructionOrderReply> responseObserver = new StreamObserver<DesctructionOrderReply>() {
                @Override
                public void onNext(DesctructionOrderReply value) {

                }

                @Override
                public void onError(Throwable t) {
                    LOG.error("Could not destroy the booster : " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    LOG.info("Booster destroyed");
                }
            };
            boosterRPCService.destructionOrderOnBooster(request, responseObserver);
        }
    }
}
