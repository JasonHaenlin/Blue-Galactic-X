package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;

public class MissionLogREST extends RestAPI {
    
    public MissionLogREST(String port) {
        super("localhost", port, "missionlogreader");
    }

    public MissionLogREST(String host, String port) {
        super(host, port, "missionlogreader");
    }

    public MissionLog retrieveMissionLogs(String id) {
        return get("/" + id, MissionLog.class);
    }

}
