package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterGrpc.BoosterStub;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Context;

@Service
public class BoosterRPCClient {
    private final static Logger LOG = LoggerFactory.getLogger(BoosterRPCClient.class);
    private ManagedChannel channel;
    private BoosterStub stub;

    public BoosterRPCClient(@Value("${api.booster.host}") String source, @Value("${api.booster.grpc}") int port) {
        this.channel = ManagedChannelBuilder.forAddress(source, port).usePlaintext().build();
        this.stub = BoosterGrpc.newStub(channel);
    }

    public void initiateLaunchSequence(String boosterId, double distanceFromEarth, double speed) {
        BoosterRequest req = BoosterRequest.newBuilder()
            .setBoosterId(boosterId)
            .setDistanceFromEarth(distanceFromEarth)
            .setSpeed(speed)
            .build();
        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("Could not init launch sequence : " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                LOG.info("Launch sequence started");
            }
        };
        Context ctx = Context.current().fork();
        ctx.run(() -> {
            stub.initiateLaunchSequence(req, responseObserver);
        });
    }


    public void initiateLandingSequence(String boosterId, double distanceFromEarth, double speed) {
        BoosterRequest req = BoosterRequest.newBuilder()
            .setBoosterId(boosterId)
            .setDistanceFromEarth(distanceFromEarth)
            .setSpeed(speed)
            .build();
        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty value) {
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("Could not init landing sequence : " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                LOG.info("Landing sequence started");
            }
        };
        Context ctx = Context.current().fork();
        ctx.run(() -> {
            stub.initiateLandingSequence(req, responseObserver);
        });
    }

    
}
