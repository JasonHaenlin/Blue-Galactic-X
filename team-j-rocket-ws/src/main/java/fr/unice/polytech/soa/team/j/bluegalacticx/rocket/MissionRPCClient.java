package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionGrpc.MissionStub;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Context;

@Service
public class MissionRPCClient {
    private final static Logger LOG = LoggerFactory.getLogger(MissionRPCClient.class);
    private ManagedChannel channel;
    private MissionStub stub;

    public MissionRPCClient(@Value("${api.mission.host}") String source, @Value("${api.mission.grpc}") int port) {
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
        Context ctx = Context.current().fork();
        ctx.run(() -> {
            stub.startMission(req, responseObserver);
        });
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
        Context ctx = Context.current().fork();
        ctx.run(() -> {
            stub.failedMission(req, responseObserver);
        });
    }
}
