package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities;

public class MissionReply {

    private Mission mission;

    public MissionReply(){
        
    }
    public MissionReply(Mission mission){
        this.mission=mission;
    }

    public Mission getMission(){
        return mission;
    }

    public void setMission(Mission mission){
        this.mission=mission;
    }
}
