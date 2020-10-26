package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.NoSameStatusException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.SpaceMetricsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;
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
    private RocketApi rocketApi;

    @Autowired
    private RocketStatusProducer rocketProducer;

    @Autowired
    private BoosterRPCClient boosterRpcClient;

    @Override
    public void setReadyToLaunch(MissionRequest request, StreamObserver<Empty> responseObserver) {
        String rId = request.getRocketId();
        try {
            Rocket r = RocketsMocked.find(rId).orElseThrow(() -> new RocketDoesNotExistException(rId));
            service.getCoordinatesFromMission(request.getMissionId()).subscribe(coor -> {
                r.setMissionObjective(coor);
            });
            service.getAvailableRocketID().subscribe(id -> {
                r.setBoosterId(id);
            });
            rocketProducer.atBaseRocketEvent(r.getId());
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
            Rocket r = findRocketOrThrow(request.getRocketId());
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
            Rocket r = findRocketOrThrow(request.getRocketId());

            String message = "";
            if (request.getLaunchRocket()) {
                r.prepareLaunch();
                message = "Launch approved !";
                r.launchSequenceActivated();
                rocketProducer.launchRocketEvent(r.getId());
            }

            LaunchOrderReply launchOrderReply = LaunchOrderReply.newBuilder().setReply(message).build();
            responseObserver.onNext(launchOrderReply);
            responseObserver.onCompleted();

        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        } catch (NoSameStatusException e) {
            responseObserver.onError(new StatusException(Status.ABORTED.withDescription(e.getMessage())));
        } catch (BoosterDestroyedException e) {
            responseObserver.onError(new StatusException(Status.ABORTED.withDescription(e.getMessage())));
        }

    }

    @Override
    public void nextStage(NextStageRequest request, StreamObserver<NextStageReply> responseObserver) {
        try {
            LOG.info("Rocket proceeding to next stage");
            String rocketId = request.getRocketId();
            Rocket r = findRocketOrThrow(rocketId);
            String detachedBoosterId = r.detachNextStage();
            double distanceFromEarth = (SpaceMetricsMocked.inAir.getTotalDistance()
                    - SpaceMetricsMocked.inAir.getDistance());
            boosterRpcClient.initiateLandingSequence(detachedBoosterId, distanceFromEarth,
                    SpaceMetricsMocked.inAir.getSpeed());
            NextStageReply nextStageReply = NextStageReply.newBuilder().setMovedToNextStage(true).build();
            responseObserver.onNext(nextStageReply);
            responseObserver.onCompleted();
        } catch (RocketDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

    private Rocket findRocketOrThrow(String id) throws RocketDoesNotExistException {
        return RocketsMocked.find(id).orElseThrow(() -> new RocketDoesNotExistException(id));
    }
}
