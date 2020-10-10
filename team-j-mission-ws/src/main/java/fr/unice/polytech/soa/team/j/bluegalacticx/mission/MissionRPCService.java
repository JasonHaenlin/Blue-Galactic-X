package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionGrpc.MissionImplBase;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

@GRpcService
public class MissionRPCService extends MissionImplBase {

    @Autowired
    private MissionService missionService;

    @Override
    public void startMission(MissionRequest request, StreamObserver<Empty> responseObserver) {
        String missionId = request.getMissionId();
        try {
            missionService.setMissionStatus(MissionStatus.STARTED, missionId);
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (MissionDoesNotExistException | BadPayloadIdException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

    @Override
    public void failedMission(MissionRequest request, StreamObserver<Empty> responseObserver) {
        String missionId = request.getMissionId();
        try {
            missionService.setMissionStatus(MissionStatus.FAILED, missionId);
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        } catch (MissionDoesNotExistException | BadPayloadIdException e) {
            responseObserver.onError(new StatusException(Status.NOT_FOUND.withDescription(e.getMessage())));
        }
    }

}
