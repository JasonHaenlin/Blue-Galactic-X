package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlController;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.PayloadDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.RocketDoesNotExistException;

@AutoConfigureMockMvc
@WebMvcTest(MissionControlController.class)
@ContextConfiguration(classes = { MissionControlController.class, MissionControlService.class })
@ExtendWith(MockitoExtension.class)
class MissionApplicationTests {

	@Autowired
	private MissionControlService missionControlService;

	private Mission mission;

	private void missionInit() {
		mission = new Mission().id("1").rocketId("2").boosterId("3").payloadId("4")
				.destination(new SpaceCoordinate(500, 200, 100));

	}

	@BeforeEach
	public void setup() {
		missionInit();
	}

	@Test
	public void shouldStoreMission() throws MissionDoesNotExistException {
		missionControlService.storeMission(mission);
		assertTrue(missionControlService.retrieveMission("1") != null);
	}

	@Test
	public void shouldUpdateMissionStatus() throws MissionDoesNotExistException, BoosterDoesNotExistException,
			RocketDoesNotExistException, PayloadDoesNotExistException {

		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.PENDING);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.NOT_READY_FOR_LAUNCH);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.WAITING_FOR_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.PENDING);
		
		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.PENDING);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.AT_BASE);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.WAITING_FOR_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.PENDING);

		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.READY);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.READY_FOR_LAUNCH);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.WAITING_FOR_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.READY);

		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.RUNNING);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.IN_SERVICE);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.ON_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.STARTED);

		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.DESTROYED);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.IN_SERVICE);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.ON_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.FAILED);

		
		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.RUNNING);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.DESTROYED);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.ON_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.FAILED);

		
		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.RUNNING);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.ENTER_MAXQ);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.DESTROYED);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.FAILED);

		missionControlService.storeBoosterStatus(mission.getBoosterId(), BoosterStatus.READY);
		missionControlService.storeRocketStatus(mission.getRocketId(), RocketStatus.UNDER_CONSTRUCTION);
		missionControlService.storePayloadStatus(mission.getPayloadId(), PayloadStatus.WAITING_FOR_MISSION);
		missionControlService.updateMissionFromPayloadState(mission.getPayloadId());

		assertTrue(missionControlService.retrieveMission("1").getStatus() == MissionStatus.ABORTED);

	}

}
