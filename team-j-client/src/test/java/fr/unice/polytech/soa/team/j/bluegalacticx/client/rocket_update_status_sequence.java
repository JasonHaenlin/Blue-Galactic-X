package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.SpaceCoordinate;
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
            Rocket r = new Rocket();
            r.setBoosterId(ctx.boostersId.get(0));
            r.setBoosterId2(ctx.boostersId.get(1));
            ctx.rocket = ctx.rocketREST.createRocket(r);
        });
        Given("Gwynne create a new payload", () -> {
            ctx.payload = new Payload().rocketId(ctx.rocket.getId()).type(PayloadType.SPACECRAFT).weight(10000)
                    .position(new SpaceCoordinate(0, 0, 0));
            ctx.payload = ctx.payloadREST.createNewPayload(ctx.payload);
        });
        Given("Elon create {int} boosters", (Integer count) -> {
            Utils.repeat(count, () -> {
                ctx.boostersId.add(ctx.boosterREST.createBooster(new Booster()).getId());
            });
        });
        Given("Richard add a new mission", () -> {
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).payloadId(ctx.payload.getId())
                    .date(Calendar.getInstance().getTime())
                    .destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate(
                                    100, 100, 100))
                    .boosterIds(ctx.arrayBoostersId());
            ctx.mission = ctx.missionPrepREST.createNewMission(ctx.mission);
        });
        When("Elon make the request for the launch order", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.mission.getId(), ctx.rocket.getId());
            Utils.assertSuccessWithRetry(() -> ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocket.getId()), 10, 1000);
        });
        Then("the rocket status should be {string} or {string}", (String status, String alt) -> {
            RocketStatus rs = ctx.rocketREST.getStatus(ctx.rocket.getId());
            assertTrue(rs == RocketStatus.valueOf(status) || rs == RocketStatus.valueOf(alt));
        });
        Then("the booster status should be {string}", (String status) -> {
            BoosterStatus bs = ctx.boosterREST.lookForBooster(ctx.boostersId.get(0)).getStatus();
            assertEquals(BoosterStatus.valueOf(status), bs);
        });
        Then("the payload status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(PayloadStatus.valueOf(status),
                    () -> ctx.payloadREST.retrievePayload(ctx.payload.getId()).getStatus());
        });
        Then("the mission status should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(MissionStatus.valueOf(status),
                    () -> ((fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission) ctx.missionCtrlREST
                            .retrieveMission(ctx.mission.getId())).getStatus());
        });
        When("Richard send a destruction order to the rocket", () -> {
            ctx.rocketRPC.destructionOrderOnRocket(ctx.rocket.getId());
        });

    }
}
