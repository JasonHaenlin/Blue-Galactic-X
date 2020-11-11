package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoObjectiveSettedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DestructionOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc.RocketImplBase;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

@GRpcService
public class RocketRPCService extends RocketImplBase {

    private final static Logger LOG = LoggerFactory.getLogger(RocketRPCService.class);

    @Autowired
    private RestService service;

    @Autowired
    private RocketStatusProducer rocketProducer;

    @Autowired
    private BoosterRPCClient boosterRpcClient;

    @Autowired
    private RocketService rocketService;

    @Override
    public void setReadyToLaunch(MissionRequest request, StreamObserver<Empty> responseObserver) {
        String rId = request.getRocketId();
        try {
            Rocket r = rocketService.retrieveRocket(rId);
            service.getCoordinatesFromMission(request.getMissionId()).subscribe(coor -> {
                r.setMissionObjective(coor);
                rocketProducer.readyToLaunchRocketEvent(r.getId(), r.getBoosterId());
                try {
                    r.readyToLaunchActivated();
                    r.prepareLaunch();
                } catch (NoObjectiveSettedException e) {
                    e.printStackTrace();
                }
            });

            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

    @Override
    public void destructionOrderOnRocket(DestructionOrderRequest request,
            StreamObserver<DesctructionOrderReply> responseObserver) {
        try {
            Rocket r = rocketService.retrieveRocket(request.getRocketId());
            r.initiateTheSelfDestructSequence();
            responseObserver
                    .onNext(DesctructionOrderReply.newBuilder().setDestructionRocket("DESTROYED MOUHAHAH !!").build());
            rocketProducer.destroyedRocketEvent(r.getId());
            responseObserver.onCompleted();

        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        } catch (RocketDestroyedException e) {
            responseObserver.onError(new StatusException(Status.ABORTED.withDescription(e.getMessage())));
        }
    }

    @Override
    public void launchOrderRocket(LaunchOrderRequest request, StreamObserver<LaunchOrderReply> responseObserver) {
        try {
            Rocket r = rocketService.retrieveRocket(request.getRocketId());
            String message = "";
            if (request.getLaunchRocket()) {
                r.launchSequenceActivated();
                rocketProducer.launchRocketEvent(r.getId(), r.getBoosterId());
                boosterRpcClient.initiateLaunchSequence(r.getBoosterId(), r.distanceFromEarth(), r.currentSpeed());
                message = "Launch approved !";
            }
            LaunchOrderReply launchOrderReply = LaunchOrderReply.newBuilder().setReply(message).build();
            responseObserver.onNext(launchOrderReply);
            responseObserver.onCompleted();

        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        } catch (NoSameStatusException | BoosterDestroyedException e) {
            responseObserver.onError(new StatusException(Status.ABORTED.withDescription(e.getMessage())));
        }

    }

    @Override
    public void nextStage(NextStageRequest request, StreamObserver<NextStageReply> responseObserver) {
        try {
            LOG.info("Rocket proceeding to next stage");
            String rId = request.getRocketId();
            Rocket r = rocketService.retrieveRocket(rId);
            String detachedBoosterId = r.detachNextStage();

            boosterRpcClient.initiateLandingSequence(detachedBoosterId, r.distanceFromEarth(), r.currentSpeed());
            boosterRpcClient.initiateLaunchSequence(r.getBoosterId2(), r.distanceFromEarth(), r.currentSpeed());

            NextStageReply nextStageReply = NextStageReply.newBuilder().setMovedToNextStage(true).build();
            responseObserver.onNext(nextStageReply);
            responseObserver.onCompleted();
        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

}
