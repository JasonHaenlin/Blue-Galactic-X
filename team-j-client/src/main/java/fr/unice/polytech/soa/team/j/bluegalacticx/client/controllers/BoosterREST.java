package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;

public class BoosterREST extends RestAPI {

    public BoosterREST(String uri) {
        super(uri);
    }

    public void createBooster(Booster booster) {
        post("/booster", Booster.class, booster);
    }

}
