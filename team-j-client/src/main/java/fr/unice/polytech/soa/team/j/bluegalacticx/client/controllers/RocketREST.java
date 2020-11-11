package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketLaunchStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;

public class RocketREST extends RestAPI {

    public RocketREST(String port) {
        super("localhost", port, "rocket");
    }

    public RocketREST(String host, String port) {
        super(host, port, "rocket");
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

    public RocketLaunchStep getLaunchStep(String rocketId) {
        return get("/launchstep/" + rocketId, RocketLaunchStep.class);
    }

    public RocketStatus getGoNoGo(String rocketId) {
        return get("/" + rocketId, RocketStatus.class);
    }

    public GoNg setGoNoGo(String rocketId, GoNg gonogo) {
        return put("/" + rocketId, GoNg.class, gonogo);
    }

}
