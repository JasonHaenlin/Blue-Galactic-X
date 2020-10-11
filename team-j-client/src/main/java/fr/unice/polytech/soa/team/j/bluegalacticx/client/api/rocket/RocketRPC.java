package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.MissionRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.NextStageReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.NextStageRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.RocketGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.RocketGrpc.RocketBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class RocketRPC {
    private ManagedChannel channel;
    private RocketBlockingStub stub;

    public RocketRPC(String source, int port) {
        this.channel = ManagedChannelBuilder.forAddress(source, port).usePlaintext().build();
        this.stub = RocketGrpc.newBlockingStub(channel);
    }

    public void setReadyToLaunch(String missionId, String rocketId) {
        MissionRequest request = MissionRequest.newBuilder().setMissionId(missionId).setRocketId(rocketId).build();
        stub.setReadyToLaunch(request);
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

}
