
package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.Empty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketRPCService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionId;
import fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.config.RocketRPCServiceTestConfig;
import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@SpringJUnitConfig(classes = { RocketRPCServiceTestConfig.class })
class RocketRPCServiceTest {

	@Autowired
	private RocketRPCService rocketRpcService;

	@Test
	public void setReadyToLaunchTest() throws Exception {
		MissionId request = MissionId.newBuilder().setMissionId(1).build();

		StreamRecorder<Empty> responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		assertNull(responseObserver.getValues().get(0));
	}

	@Test
	public void launchRocketTest() throws Exception {
		LaunchOrderRequest request = LaunchOrderRequest.newBuilder().setLaunchRocket(true).build();

		StreamRecorder<LaunchOrderReply> responseObserver = StreamRecorder.create();
		rocketRpcService.launchOrderRocket(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		List<LaunchOrderReply> results = responseObserver.getValues();
		LaunchOrderReply response = results.get(0);
		assertEquals("Launch approved !", response.getReply());
	}

}
