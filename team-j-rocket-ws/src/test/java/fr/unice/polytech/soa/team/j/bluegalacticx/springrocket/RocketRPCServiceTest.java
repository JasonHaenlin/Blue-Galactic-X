
package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.protobuf.Empty;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketRPCService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DesctructionOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.DestructionOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.config.RocketRPCServiceTestConfig;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.internal.testing.StreamRecorder;

@SpringBootTest
@SpringJUnitConfig(classes = { RocketRPCServiceTestConfig.class })
@Tags(value = { @Tag("grpc"), @Tag("grpc-rocket") })
@TestMethodOrder(OrderAnnotation.class)
class RocketRPCServiceTest {

	@Autowired
	private RocketRPCService rocketRpcService;

	@Test
	@Order(1)
	public void setReadyToLaunchTest() throws Exception {
		MissionRequest request = MissionRequest.newBuilder().setMissionId("1").setRocketId("1").build();

		StreamRecorder<Empty> responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		assertNull(responseObserver.getValues().get(0));
	}

	@Test
	@Order(2)
	public void launchRocketTest() throws Exception {
		LaunchOrderRequest request = LaunchOrderRequest.newBuilder().setRocketId("1").setLaunchRocket(true).build();

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

	@Test
	@Order(3)
	public void setReadyToLaunchRocketNotFoundTest() throws Exception {
		MissionRequest request = MissionRequest.newBuilder().setMissionId("1").setRocketId("3").build();

		StreamRecorder<Empty> responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNotNull(responseObserver.getError());

		assertEquals(0, responseObserver.getValues().size());

		StatusException t = (StatusException) responseObserver.getError();
		assertEquals(Status.NOT_FOUND.getCode(), t.getStatus().getCode());
	}

	@Test
	@Order(4)
	public void setSameRocketWithMultipleMissionShouldBeOkayIfAtBaseTest() throws Exception {
		Rocket r = RocketsMocked.find("1").orElse(null);
		r.status(RocketStatus.AT_BASE);

		MissionRequest request = MissionRequest.newBuilder().setMissionId("1").setRocketId("1").build();
		StreamRecorder<Empty> responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		request = MissionRequest.newBuilder().setMissionId("2").setRocketId("1").build();
		responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());
	}

	@Test
	@Order(5)
	public void setSameRocketWithMultipleMissionErrorIfNotAtBaseTest() throws Exception {
		Rocket r = RocketsMocked.find("1").orElse(null);
		r.status(RocketStatus.IN_SERVICE);

		MissionRequest request = MissionRequest.newBuilder().setMissionId("1").setRocketId("1").build();
		StreamRecorder<Empty> responseObserver = StreamRecorder.create();
		rocketRpcService.setReadyToLaunch(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNotNull(responseObserver.getError());

		StatusException t = (StatusException) responseObserver.getError();
		assertEquals(Status.ABORTED.getCode(), t.getStatus().getCode());
	}

	@Test
	@Order(6)
	public void destroyRocketNotDestroyedIsOkTest() throws Exception {
		DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setRocketId("1").build();
		StreamRecorder<DesctructionOrderReply> responseObserver = StreamRecorder.create();
		rocketRpcService.destructionOrderOnRocket(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		List<DesctructionOrderReply> results = responseObserver.getValues();
		DesctructionOrderReply response = results.get(0);
		assertEquals("DESTROYED MOUHAHAH !!", response.getDestructionRocket());
	}

	@Test
	@Order(7)
	public void destroyRocketAlreadyDestroyedIsKoTest() throws Exception {
		DestructionOrderRequest request = DestructionOrderRequest.newBuilder().setRocketId("1").build();
		StreamRecorder<DesctructionOrderReply> responseObserver = StreamRecorder.create();
		rocketRpcService.destructionOrderOnRocket(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNotNull(responseObserver.getError());

		StatusException t = (StatusException) responseObserver.getError();
		assertEquals(Status.ABORTED.getCode(), t.getStatus().getCode());
	}

	@Test
	@Order(8)
	public void goToNextStageTest() throws Exception {
		NextStageRequest request = NextStageRequest.newBuilder().setRocketId("1").build();
		StreamRecorder<NextStageReply> responseObserver = StreamRecorder.create();
		rocketRpcService.nextStage(request, responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());

		List<NextStageReply> results = responseObserver.getValues();
		NextStageReply response = results.get(0);
		assertEquals(true, response.getMovedToNextStage());
	}

}
