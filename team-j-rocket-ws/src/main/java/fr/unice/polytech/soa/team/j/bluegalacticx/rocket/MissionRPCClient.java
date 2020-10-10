package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionGrpc.MissionStub;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

@Service
public class MissionRPCClient {
    private final static Logger LOG = LoggerFactory.getLogger(MissionRPCClient.class);
    private static final String source = "http://localhost";
    private static final int port = 8071;

    private ManagedChannel channel;
    private MissionStub stub;

    public MissionRPCClient() {
        this.channel = ManagedChannelBuilder.forAddress(source, port).usePlaintext().build();
        this.stub = MissionGrpc.newStub(channel);
    }

    public void startMission(String missionId) {
        MissionRequest req = MissionRequest.newBuilder().setMissionId(missionId).build();
        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("Could not start the mission");
            }

            @Override
            public void onCompleted() {
                LOG.info("Mission started");
            }
        };

        stub.startMission(req, responseObserver);
    }

    public void failedMission(String missionId) {
        MissionRequest req = MissionRequest.newBuilder().setMissionId(missionId).build();
        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("Could not put the mission as failed");
            }

            @Override
            public void onCompleted() {
                LOG.info("Could put the mission as failed");
            }
        };

        stub.failedMission(req, responseObserver);
    }
}