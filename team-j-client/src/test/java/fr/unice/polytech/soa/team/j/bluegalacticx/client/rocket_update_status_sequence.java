package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;
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

        Given("Gwynne create a new rocket", () -> {
            ctx.rocket = ctx.rocketREST.createRocket(new Rocket());
        });
        Given("Richard add a new mission", () -> {
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).destination(
                    new fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate(100, 100, 100));
            ctx.missionREST.createNewMission(ctx.mission);
        });
        Given("Gwynne create a new payload", () -> {
            ctx.payload = new Payload().rocketId(ctx.rocket.getId()).type(PayloadType.SPACECRAFT).weight(10000);
            ctx.payloadREST.createNewPayload(ctx.payload);
        });
        When("Elon make the request for the launch order", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.mission.getId(), ctx.rocket.getId());
            Utils.assertSuccessWithRetry(() -> ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocket.getId()), 10, 1000);
        });
        Then("the rocket status should be {string}", (String status) -> {
            RocketStatus rs = ctx.rocketREST.getStatus(ctx.rocket.getId());
            assertEquals(RocketStatus.valueOf(status), rs);
        });
        Then("the payload status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(PayloadStatus.valueOf(status),
                    () -> ctx.payloadREST.retrievePayload(ctx.payload.getId()).getStatus());
        });
        Then("the mission status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(MissionStatus.valueOf(status),
                    () -> ctx.missionREST.retrieveMission(ctx.mission.getId()).getStatus());
        });
        When("Richard send a destruction order to the rocket", () -> {
            ctx.rocketRPC.destructionOrderOnRocket(ctx.rocket.getId());
        });

    }
}
