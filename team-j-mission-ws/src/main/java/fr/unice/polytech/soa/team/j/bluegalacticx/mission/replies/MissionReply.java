package fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies;

public class MissionReply {

    private String name;
    public MissionReply(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
}
