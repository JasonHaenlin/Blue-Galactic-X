package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.consumers;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketRPCService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DestructionOrderRequest;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ModuleDestroyerConsumer {
    private final static Logger LOG = LoggerFactory.getLogger(RocketRPCService.class);

    @Autowired
    private RocketRPCService rocketRPCService;

    @KafkaListener(topics = "${kafka.topics.moduledestruction}", groupId = "${kafka.group.default}", containerFactory = "ModuleDestroyerKafkaListenerContainerFactory")
    public void destroy(DestroyModuleRequest req) {
        if (req.getModuleType() == DestroyModuleRequest.ModuleType.ROCKET){
            DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setRocketId(req.getModuleId()).build();
            StreamObserver<DesctructionOrderReply> responseObserver = new StreamObserver<DesctructionOrderReply>() {
                @Override
                public void onNext(DesctructionOrderReply value) {

                }

                @Override
                public void onError(Throwable t) {
                    LOG.error("Could not destroy the rocket : " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    LOG.info("Rocket destroyed");
                }
            };
            rocketRPCService.destructionOrderOnRocket(request, responseObserver);
        }
    }
}
