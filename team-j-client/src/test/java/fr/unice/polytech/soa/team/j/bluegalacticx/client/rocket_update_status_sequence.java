package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.PayloadREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketStatus;
import io.cucumber.java8.En;

public class rocket_update_status_sequence implements En {

    private RocketRPC rocketRPC = new RocketRPC("localhost", 8081);
    private MissionREST missionREST = new MissionREST("http://localhost:8070/mission");
    private RocketREST rocketREST = new RocketREST("http://localhost:8080/rocket");
    private PayloadREST payloadREST = new PayloadREST("http://localhost:8050/payload");

    private Mission mission;
    private Payload payload;

    private String payloadId;
    private String missionId;
    private String rocketId;

    AppLog log = AppLog.getInstance();

    public rocket_update_status_sequence() {

        Given("Richard add a new mission with payload id {string} and rocket id {string}",
                (String missionId, String rocketId) -> {
                    this.missionId = missionId;
                    this.rocketId = rocketId;
                    mission = new Mission().id(missionId).rocketId(rocketId)
                            .destination(new SpaceCoordinate(100, 100, 100));
                    missionREST.createNewMission(mission);
                });
        Given("Gwynne create a new payload with id {string} and rocket id {string}",
                (String payloadId, String rocketId) -> {
                    this.payloadId = payloadId;
                    payload = new Payload().id(payloadId).rocketId(rocketId).type(PayloadType.SPACECRAFT).weight(10000);
                    payloadREST.createNewPayload(payload);
                });
        When("Elon make the request for the launch order", () -> {
            rocketRPC.setReadyToLaunch(missionId, rocketId);
            rocketRPC.LaunchOrderRequest(true, rocketId);
        });
        Then("the rocket status should be {string}", (String status) -> {
            RocketStatus rs = rocketREST.getStatus(rocketId);
            assertEquals(RocketStatus.valueOf(status), rs);
        });
        Then("the payload status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(PayloadStatus.valueOf(status), () -> {
                try {
                    return payloadREST.retrievePayload(payloadId).getStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        });
        Then("the mission status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(MissionStatus.valueOf(status), () -> {
                try {
                    return missionREST.retrieveMission(missionId).getStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        });
        When("Richard send a destruction order to the rocket", () -> {
            rocketRPC.destructionOrderOnRocket(rocketId);
        });
    }
}
