package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.DepartmentGoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;

public class MissionPreparationREST extends RestAPI {

    public MissionPreparationREST(String port) {
        super("localhost", port, "mission-preparation");
    }

    public MissionPreparationREST(String host, String port) {
        super(host, port, "mission-preparation");
    }

    public Mission createNewMission(Mission mission) {
        return post("/", Mission.class, mission);
    }

    public DepartmentGoNg getMissionGoNg(String missionId) {
        return get("/" + missionId + "/gonogo", DepartmentGoNg.class);
    }

    public DepartmentGoNg updateMissionGoNg(String missionId, GoNg goNg) {
        return put("/" + missionId + "/gonogo", DepartmentGoNg.class, goNg);
    }

}
