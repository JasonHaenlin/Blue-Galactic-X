package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DestructionOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc.RocketBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class RocketRPC {
    private ManagedChannel channel;
    private RocketBlockingStub stub;
    private final String source;
    private final int port;

    public RocketRPC(String source, int port) {
        this.source = source;
        this.port = port;
    }

    public void setReadyToLaunch(String missionId, String rocketId) {
        MissionRequest request = MissionRequest.newBuilder().setMissionId(missionId).setRocketId(rocketId).build();
        stub.setReadyToLaunch(request);
    }

    public void destructionOrderOnRocket(String rocketId) {
        DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setRocketId(rocketId).build();
        stub.destructionOrderOnRocket(request);
    }

    public LaunchOrderReply LaunchOrderRequest(boolean launchRocket, String rocketId) {
        LaunchOrderRequest request = LaunchOrderRequest.newBuilder().setLaunchRocket(launchRocket).setRocketId(rocketId)
                .build();
        return stub.launchOrderRocket(request);
    }

    public NextStageReply nextStage(String rocketId) {
        NextStageRequest request = NextStageRequest.newBuilder().setRocketId(rocketId).build();
        return stub.nextStage(request);
    }

    public void shutDown() {
        this.channel.shutdown();
    }

    public void create() {
        this.channel = ManagedChannelBuilder.forAddress(source, port).usePlaintext().build();
        this.stub = RocketGrpc.newBlockingStub(channel);
    }

}
