package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import io.cucumber.java8.En;

public class rocket_update_status_sequence implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public rocket_update_status_sequence() {

        Before(() -> {
            ctx.rocketRPC.create();
        });

        After(() -> {
            ctx.rocketRPC.shutDown();
        });

        Given("Gwynne create a new rocket with id {string}", (String rocketId) -> {
            ctx.rocketId = rocketId;
            ctx.rocket = new Rocket().id(rocketId);
            ctx.rocketREST.createRocket(ctx.rocket);
        });
        Given("Richard add a new mission with mission id {string} and rocket id {string}",
                (String missionId, String rocketId) -> {
                    ctx.missionId = missionId;
                    ctx.mission = new Mission().id(missionId).rocketId(rocketId).destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate(100, 100,
                                    100));
                    ctx.missionREST.createNewMission(ctx.mission);
                });
        Given("Gwynne create a new payload with id {string} and rocket id {string}",
                (String payloadId, String rocketId) -> {
                    ctx.payloadId = payloadId;
                    ctx.payload = new Payload().id(payloadId).rocketId(rocketId).type(PayloadType.SPACECRAFT)
                            .weight(10000);
                    ctx.payloadREST.createNewPayload(ctx.payload);
                });
        When("Elon make the request for the launch order", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.missionId, ctx.rocketId);
            Utils.assertSuccessWithRetry(() -> ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocketId), 10, 1000);
        });
        Then("the rocket status should be {string}", (String status) -> {
            RocketStatus rs = ctx.rocketREST.getStatus(ctx.rocketId);
            assertEquals(RocketStatus.valueOf(status), rs);
        });
        Then("the payload status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(PayloadStatus.valueOf(status),
                    () -> ctx.payloadREST.retrievePayload(ctx.payloadId).getStatus());
        });
        Then("the mission status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(MissionStatus.valueOf(status),
                    () -> ctx.missionREST.retrieveMission(ctx.missionId).getStatus());
        });
        When("Richard send a destruction order to the rocket", () -> {
            ctx.rocketRPC.destructionOrderOnRocket(ctx.rocketId);
        });

    }
}
