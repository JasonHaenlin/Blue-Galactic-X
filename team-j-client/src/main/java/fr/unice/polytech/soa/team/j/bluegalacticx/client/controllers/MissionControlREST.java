package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission;

public class MissionControlREST extends RestAPI {

    public MissionControlREST(String port) {
        super("localhost", port, "mission-control");
    }

    public MissionControlREST(String host, String port) {
        super(host, port, "mission-control");
    }

    public Mission retrieveMission(String id) {
        return get("/" + id, Mission.class);
    }

}
