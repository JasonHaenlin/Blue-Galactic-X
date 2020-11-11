package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import io.cucumber.java8.En;

public class demo_sequence implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public demo_sequence() {
        Given("Elon create 2 boosters in the booster micro-service", () -> {
            Utils.repeat(2, () -> {
                ctx.boostersId.add(ctx.boosterREST.createBooster(new Booster()).getId());
            });
        });
        Given("Gwynne create a new rocket in the rocket micro-service", () -> {
            Rocket r = new Rocket();
            r.setBoosterId(ctx.boostersId.get(0));
            r.setBoosterId2(ctx.boostersId.get(1));
            ctx.rocket = ctx.rocketREST.createRocket(r);
        });
        Given("Gwynne create a new payload in the payload micro-service", () -> {
            ctx.payload = new Payload().rocketId(ctx.rocket.getId()).type(PayloadType.SPACECRAFT).weight(10000)
                    .position(new SpaceCoordinate(0, 0, 0));
            ctx.payload = ctx.payloadREST.createNewPayload(ctx.payload);
        });
        Given("Richard add a new mission in the mission preparation micro-service", () -> {
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).payloadId(ctx.payload.getId())
                    .date(Calendar.getInstance().getTime())
                    .destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate(
                                    100, 100, 100))
                    .boosterIds(ctx.arrayBoostersId());
            ctx.mission = ctx.missionPrepREST.createNewMission(ctx.mission);
        });
        When("Richard has the weather and rocket department not ready for launch", () -> {
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            // Only mission is set to false at initialization
            assertEquals(1, status.size());
        });
        Then("Richard make a no go for the mission department", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(false));
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            assertFalse(status.get(Department.MISSION));
        });
        When("Tory from weather department see the weather is good", () -> {
            WeatherReport wr = ctx.weatherREST.getCurrentWeather();
            assertNotNull(wr);
        });
        Then("Tory set the weather department to go to notify it is ready", () -> {
            ctx.weatherREST.setGoNoGo(new fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg(true));
        });
        When("Elon see the rocket is ready based on the Telemetry", () -> {
            SpaceTelemetry rm = ctx.rocketREST.getRocketTelemetry(ctx.rocket.getId());
            assertNotNull(rm);
        });
        Then("Elon set the rocket department to go to notify it is ready", () -> {
            ctx.rocketREST.setGoNoGo(ctx.rocket.getId(), new GoNg(true));
        });
        When("Richard has the weather and rocket department ready for mission launch", () -> {
            Utils.assertEqualsWithRetry(2, () -> {
                try {
                    Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId())
                            .getDepartments();
                    int goCounter = 0;
                    if (status.get(Department.ROCKET)) {
                        goCounter++;
                    }
                    if (status.get(Department.WEATHER)) {
                        goCounter++;
                    }
                    return goCounter;
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                return 0;
            });
        });
        Then("Richard make a go for the mission department in the mission preparation micro-service", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(true));
        });
        Then("Elon should see the rocket department is {string}", (String string) -> {
            RocketStatus rs = ctx.rocketREST.getGoNoGo(ctx.rocket.getId());
            assertEquals(string, rs.toString());
        });
        When("Elon make the request for the launch order to begin the countdown from the rocket micro-service", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.mission.getId(), ctx.rocket.getId());
            Utils.assertSuccessWithRetry(() -> ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocket.getId()), 10, 1000);
        });
        Then("the rocket status should be set on {string} in the rocket micro-service", (String status) -> {
            RocketStatus rs = ctx.rocketREST.getStatus(ctx.rocket.getId());
            assertEquals(status, rs.toString());
        });
        Then("the payload status should be {string} in the payload micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ctx.payloadREST.retrievePayload(ctx.payload.getId()).getStatus().toString());
        });
        Then("the booster status should be {string} in the booster micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ctx.boosterREST.lookForBooster(ctx.boostersId.get(0)).getStatus().toString());
        });
        Then("the mission status should be {string} in the mission control micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ((fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission) ctx.missionCtrlREST
                            .retrieveMission(ctx.mission.getId())).getStatus().toString());
        });
        Before(() -> {
            ctx.rocketRPC.create();
        });

        After(() -> {
            ctx.rocketRPC.shutDown();
        });
    }
}
