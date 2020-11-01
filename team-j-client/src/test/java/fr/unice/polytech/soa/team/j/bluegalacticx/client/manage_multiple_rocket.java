package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.booster.entities.BoosterREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.SpaceCoordinate;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class manage_multiple_rocket implements En {

    private RocketREST rocketREST = new RocketREST("http://localhost:8080/rocket");
    private BoosterREST boosterREST = new BoosterREST("http://localhost:8080/booster");
    private RocketRPC rocketRPC = new RocketRPC("localhost", 8081);
    private MissionREST missionREST = new MissionREST("http://localhost:8080/mission");

    public manage_multiple_rocket() {
        Given("Elon who added the rockets below :", (DataTable dataTable) -> {
            List<List<String>> rows = dataTable.asLists(String.class);

            for (List<String> columns : rows) {
                rocketREST.createRocket(new Rocket().id(columns.get(0)).objective(stringToCoordinate(columns.get(1)))
                        .boosterId(columns.get(2)));
            }

            for (List<String> columns : rows) {
                assertNotNull(rocketREST.getStatus(columns.get(0)));
            }
        });

        And("the boosters below :", (DataTable dataTable) -> {
            List<List<String>> rows = dataTable.asLists(String.class);

            for (List<String> columns : rows) {
                boosterREST.createBooster(new Booster().id(columns.get(0)).fuelLevel(Integer.parseInt(columns.get(1))));
            }
        });

        And("Richard programs the following missions", (DataTable dataTable) -> {
            List<List<String>> rows = dataTable.asLists(String.class);
            for (List<String> columns : rows) {
                missionREST.createNewMission(new Mission().id(columns.get(0)).rocketId(columns.get(1)));
            }
        });

        Given("Elon get the authorisation to launch the rocket {string} for the mission {string}",
                (String string, String string2) -> {
                    rocketRPC.setReadyToLaunch(string, string2);
                });

        When("Elon launches the rocket {string}", (String string) -> {
            rocketRPC.LaunchOrderRequest(true, string);
        });

        When("{int} time unit passed", (Integer int1) -> {
            TimeUnit.SECONDS.sleep(10);
        });

        Then("the rocket {string} is {string}", (String string, String string2) -> {
            RocketStatus rs = rocketREST.getStatus(string);
            assertEquals(string2, rs.toString());
        });
    }

    private SpaceCoordinate stringToCoordinate(String coordinate) {
        String[] tokens = coordinate.split(",");
        return new SpaceCoordinate(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2]));
    }

}