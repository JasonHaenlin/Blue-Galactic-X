package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterGrpc.BoosterImplBase;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.LandingRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

@GRpcService
public class BoosterRPCService extends BoosterImplBase {

    private final static Logger LOG = LoggerFactory.getLogger(BoosterRPCService.class);

    @Autowired
    BoosterService boosterService;

    @Override
    public void initiateLandingSequence(LandingRequest request, StreamObserver<Empty> responseObserver) {
        String rId = request.getBoosterId();
        double distanceFromEarth = request.getDistanceFromEarth();
        double speed = request.getSpeed();
        LOG.info("Initating landing sequence");
        try {
            Booster r = boosterService.retrieveBooster(rId);
            r.setStatus(BoosterStatus.LANDING);
            r.setDistanceFromEarth(distanceFromEarth);
            r.setSpeed(speed);
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (BoosterDoesNotExistException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

}
