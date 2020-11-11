package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import io.cucumber.java8.En;

public class department_go_nogo_sequence implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public department_go_nogo_sequence() {
        Given("Gwynne create a rocket", () -> {
            ctx.rocket = ctx.rocketREST.createRocket(new Rocket());
        });
        Given("Richard add a mission", () -> {
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).payloadId("1")
                    .date(Calendar.getInstance().getTime())
                    .destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate(
                                    100, 100, 100))
                    .boosterIds(new String[] { "1", "2" });
            ctx.mission = ctx.missionPrepREST.createNewMission(ctx.mission);
        });
        When("Richard has the weather and rocket department not ready", () -> {
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            // Only mission is set to false at initialization
            assertEquals(1, status.size());
        });
        Then("Richard make a no go for the mission department with id", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(false));
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            assertFalse(status.get(Department.MISSION));
        });
        When("Tory see the weather is good", () -> {
            WeatherReport wr = ctx.weatherREST.getCurrentWeather();
            assertNotNull(wr);
        });
        Then("Tory set the weather department to go", () -> {
            ctx.weatherREST.setGoNoGo(new fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg(true));
        });
        When("Elon see the rocket is ready", () -> {
            SpaceTelemetry rm = ctx.rocketREST.getRocketTelemetry(ctx.rocket.getId());
            assertNotNull(rm);
        });
        Then("Elon set the rocket department to go with id", () -> {
            ctx.rocketREST.setGoNoGo(ctx.rocket.getId(), new GoNg(true));
        });
        When("Richard has the weather and rocket department ready for mission", () -> {
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
        Then("Richard make a go for the mission department", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(true));
        });
        Then("Elon should see the corresponding validation on the rocket department", () -> {
            RocketStatus rs = ctx.rocketREST.getGoNoGo(ctx.rocket.getId());
            assertEquals(RocketStatus.READY_FOR_LAUNCH, rs);
        });
    }

}
