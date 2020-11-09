package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.DepartmentGoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;

public class MissionREST extends RestAPI {

    public MissionREST(String uri) {
        super(uri);
    }

    public Mission createNewMission(Mission mission) {
        return post("/", Mission.class, mission);
    }

    public Mission retrieveMission(String missionId) {
        return get("/" + missionId, Mission.class);
    }

    public DepartmentGoNg getMissionGoNg(String missionId) {
        return get("/" + missionId + "/gonogo", DepartmentGoNg.class);
    }

    public DepartmentGoNg updateMissionGoNg(String missionId, GoNg goNg) {
        return put("/" + missionId + "/gonogo", DepartmentGoNg.class, goNg);
    }

}
