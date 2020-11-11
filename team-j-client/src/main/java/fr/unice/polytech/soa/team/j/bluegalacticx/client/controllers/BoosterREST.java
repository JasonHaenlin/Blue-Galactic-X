package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;

public class BoosterREST extends RestAPI {

    public BoosterREST(String port) {
        super("localhost", port, "booster");
    }

    public BoosterREST(String host, String port) {
        super(host, port, "booster");
    }

    public Booster createBooster(Booster booster) {
        return post("/", Booster.class, booster);
    }

    public String getAvailableBoosterID() {
        return get("/available", String.class);
    }

    public Booster lookForBooster(String id) {
        return get("/" + id, Booster.class);
    }

}
