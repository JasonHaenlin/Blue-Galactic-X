package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import com.google.protobuf.Empty;

import org.lognet.springboot.grpc.GRpcService;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.ReadyToLaunchRequest;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketGrpc.RocketImplBase;
import io.grpc.stub.StreamObserver;

@GRpcService
public class RocketRPCService extends RocketImplBase {

    boolean isReady = false;

    private boolean launchRocket = false;

    @Override
    public void setReadyToLaunch(ReadyToLaunchRequest request, StreamObserver<Empty> responseObserver) {
        this.isReady = request.getIsReady();
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
