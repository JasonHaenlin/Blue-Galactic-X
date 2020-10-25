package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;
import io.cucumber.java8.En;

public class department_go_nogo_sequence implements En {

    private MissionREST missionREST = new MissionREST("http://localhost:8070/mission");
    private RocketREST rocketREST = new RocketREST("http://localhost:8080/rocket");
    private WeatherREST weatherREST = new WeatherREST("http://localhost:8060/weather");

    private String missionId;
    private String rocketId;

    AppLog log = AppLog.getInstance();

    public department_go_nogo_sequence() {
        Given("Richard add a new mission with mission id {string} and rocket id {string}",
                (String missionId, String rocketId) -> {
                    this.missionId = missionId;
                    this.rocketId = rocketId;
                    Mission mission = new Mission().id(missionId).rocketId(rocketId)
                            .destination(new SpaceCoordinate(100, 100, 100));
                    missionREST.createNewMission(mission);
                });
        When("Richard has the weather and rocket department not ready", () -> {
            Map<Department, Boolean> status = missionREST.getMissionGoNg(missionId);
            assertEquals(0, status.size());
        });
        Then("Richard make a no go for the mission department with id", () -> {
            missionREST.updateMissionGoNg(missionId, new GoNg(false));
            Map<Department, Boolean> status = missionREST.getMissionGoNg(missionId);
            assertFalse(status.get(Department.MISSION));
        });
        When("Tory see the weather is good", () -> {
            WeatherReport wr = weatherREST.getCurrentWeather();
            assertNotNull(wr);
        });
        Then("Tory set the weather department to go", () -> {
            weatherREST.setGoNoGo(new GoNg(true));
        });
        When("Elon see the rocket is ready", () -> {
            RocketMetrics rm = rocketREST.getMetrics(rocketId);
            assertNotNull(rm);
        });
        Then("Elon set the rocket department to go with id", () -> {
            rocketREST.setGoNoGo(rocketId, new GoNg(true));
        });
        When("Richard has the weather and rocket department ready for mission", () -> {
            Utils.assertEqualsWithRetry(2, () -> {
                try {
                    Map<Department, Boolean> status = missionREST.getMissionGoNg(missionId);
                    int goCounter = 0;
                    if (status.get(Department.ROCKET)) {
                        goCounter++;
                    }
                    if (status.get(Department.WEATHER)) {
                        goCounter++;
                    }
                    return goCounter;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            });
        });
        Then("Richard make a go for the mission department", () -> {
            missionREST.updateMissionGoNg(missionId, new GoNg(true));
        });
        Then("Elon should see the corresponding validation on the rocket department", () -> {
            RocketStatus rs = rocketREST.getGoNoGo(rocketId);
            assertEquals(RocketStatus.READY_FOR_LAUNCH, rs);
        });
    }

}
