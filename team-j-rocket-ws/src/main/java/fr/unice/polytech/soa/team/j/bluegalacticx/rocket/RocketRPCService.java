package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionId;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc.RocketImplBase;
import io.grpc.stub.StreamObserver;

@GRpcService
public class RocketRPCService extends RocketImplBase {

    int missionId = -1;

    private boolean launchRocket = false;

    @Override
    public void setReadyToLaunch(MissionId request, StreamObserver<Empty> responseObserver) {
        this.missionId = request.getMissionId();
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }

    @Override
    public void launchOrderRocket(LaunchOrderRequest request, StreamObserver<LaunchOrderReply> responseObserver) {
        StringBuilder messageResponseAfterLaunch = new StringBuilder();

        launchRocket = request.getLaunchRocket();
        if (launchRocket) {
            messageResponseAfterLaunch.append("Launch approved !");
        }

        LaunchOrderReply launchOrderReply = LaunchOrderReply.newBuilder()
                .setReply(messageResponseAfterLaunch.toString()).build();
        responseObserver.onNext(launchOrderReply);
        responseObserver.onCompleted();
    }
}
