package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.producers.BoosterStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterGrpc.BoosterImplBase;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

@GRpcService
public class BoosterRPCService extends BoosterImplBase {

    private final static Logger LOG = LoggerFactory.getLogger(BoosterRPCService.class);

    @Autowired
    BoosterService boosterService;

    @Autowired
    private BoosterStatusProducer boosterStatusProducer;

    @Override
    public void initiateLaunchSequence(BoosterRequest request, StreamObserver<Empty> responseObserver) {
        String rId = request.getBoosterId();
        double distanceFromEarth = request.getDistanceFromEarth();
        double speed = request.getSpeed();
        LOG.info("Initiating launch sequence");
        try {
            Booster b = boosterService.retrieveBooster(rId);
            b.setDistanceFromEarth(distanceFromEarth);
            b.setSpeed(speed);
            b.setStatus(BoosterStatus.RUNNING);
            boosterStatusProducer.notifyBoosterRunning(b.getId());
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (BoosterDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

    @Override
    public void initiateLandingSequence(BoosterRequest request, StreamObserver<Empty> responseObserver) {
        String rId = request.getBoosterId();
        double distanceFromEarth = request.getDistanceFromEarth();
        double speed = request.getSpeed();
        LOG.info("Initiating landing sequence");
        try {
            Booster b = boosterService.retrieveBooster(rId);
            b.setStatus(BoosterStatus.LANDING);
            boosterStatusProducer.notifyBoosterLanding(b.getId());
            b.setDistanceFromEarth(distanceFromEarth);
            b.setSpeed(speed);
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (BoosterDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

}
