package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionId;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc.RocketBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class RocketRPC {
    private ManagedChannel channel;
    private RocketBlockingStub stub;

    public RocketRPC(String source, int port) {
        this.channel = ManagedChannelBuilder.forAddress(source, port).usePlaintext().build();
        this.stub = RocketGrpc.newBlockingStub(channel);
    }

    public void setReadyToLaunch(int id) {
        MissionId request = MissionId.newBuilder().setMissionId(id).build();
        stub.setReadyToLaunch(request);
    }

    public LaunchOrderReply LaunchOrderRequest(boolean launchRocket) {
        LaunchOrderRequest request = LaunchOrderRequest.newBuilder().setLaunchRocket(launchRocket).build();
        return stub.launchOrderRocket(request);
    }

}
