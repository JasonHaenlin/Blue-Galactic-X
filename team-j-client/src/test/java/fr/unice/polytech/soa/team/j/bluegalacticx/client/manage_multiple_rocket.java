package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.booster.entities.BoosterREST;
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

    public manage_multiple_rocket() {
        Given("Elon who added the rockets below :", (DataTable dataTable) -> {
            List<List<String>> rows = dataTable.asLists(String.class);

            for (List<String> columns : rows) {
                rocketREST.createRocket(new Rocket().id(columns.get(0)).objective(stringToCoordinate(columns.get(1)))
                        .boosterId(columns.get(2)));
            }
        });

        // TODO create boosters
        // Given("the boosters below :", (DataTable dataTable) -> {
        // });

        Given("Elon get the authorisation to launch the rocket {string}", (String string) -> {
            assertTrue(true);
        });
        When("Elon launches the rocket {string}", (String string) -> {
            // Write code here that turns the phrase above into concrete actions
            rocketRPC.LaunchOrderRequest(true, string);
        });
        When("{int} time unit passed", (Integer int1) -> {
            TimeUnit.SECONDS.sleep(10);
        });
        Then("the rocket {string} is {string}", (String string, String string2) -> {
            RocketStatus rs = rocketREST.getStatus(string);
            assertEquals(rs.toString(), string2);
        });
    }

    private SpaceCoordinate stringToCoordinate(String coordinate) {
        String[] tokens = coordinate.split(",");
        return new SpaceCoordinate(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2]));
    }
}