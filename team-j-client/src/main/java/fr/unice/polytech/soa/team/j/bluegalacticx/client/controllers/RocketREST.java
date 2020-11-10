package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;

public class RocketREST extends RestAPI {

    public RocketREST(String uri) {
        super(uri);
    }

    public Rocket createRocket(Rocket rocket) {
        return post("/", Rocket.class, rocket);
    }

    public SpaceTelemetry getRocketTelemetry(String rocketId) {
        return get("/telemetry/" + rocketId, SpaceTelemetry.class);
    }

    public RocketStatus getStatus(String rocketId) {
        return get("/telemetry/" + rocketId + "/status", RocketStatus.class);
    }

    public RocketStatus getGoNoGo(String rocketId) {
        return get("/" + rocketId, RocketStatus.class);
    }

    public void setGoNoGo(String rocketId, GoNg gonogo) {
        put("/" + rocketId, GoNg.class, gonogo);
    }

}
