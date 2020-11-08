package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import java.util.List;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import io.cucumber.java8.En;

public class multiple_rockets_launched implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public multiple_rockets_launched() {
        Given("Elon who added the rockets below :", (io.cucumber.datatable.DataTable table) -> {
            List<Map<String, String>> columns = table.asMaps(String.class, String.class);

            for (Map<String, String> rows : columns) {
                String rocketId = rows.get("rocketId");
                String boosterId = rows.get("boosterId");
                int x = Integer.parseInt(rows.get("x"));
                int y = Integer.parseInt(rows.get("y"));
                int z = Integer.parseInt(rows.get("z"));
                ctx.rocketREST.createRocket(
                        new Rocket().id(rocketId).spaceCoordinate(new SpaceCoordinate(x, y, z)).boosterId(boosterId));
            }
        });
        Given("the boosters below :", (io.cucumber.datatable.DataTable table) -> {
            List<Map<String, String>> columns = table.asMaps(String.class, String.class);

            for (Map<String, String> rows : columns) {
                String boosterId = rows.get("boosterId");
                int fuelLevel = Integer.parseInt(rows.get("fuel"));
                ctx.boosterREST.createBooster(new Booster().id(boosterId).fuelLevel(fuelLevel));
            }
        });
        Given("Richard programs the following missions :", (io.cucumber.datatable.DataTable table) -> {
            List<Map<String, String>> columns = table.asMaps(String.class, String.class);

            for (Map<String, String> rows : columns) {
                String missionId = rows.get("missionId");
                String rocketId = rows.get("rocketId");
                ctx.missionREST.createNewMission(new Mission().id(missionId).rocketId(rocketId));
            }
        });
        Given("Elon get the authorisation to launch the rocket {string} for the mission {string}",
                (String string, String string2) -> {
                    // Write code here that turns the phrase above into concrete actions
                    throw new io.cucumber.java8.PendingException();
                });
        When("Elon launches the rocket {string}", (String string) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java8.PendingException();
        });
        When("{int} time unit passed", (Integer int1) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java8.PendingException();
        });
        Then("the rocket {string} is {string}", (String string, String string2) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java8.PendingException();
        });
    }
}
