package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.exceptions;

public class MissionNotFoundException extends Exception {

    /**
     * auto-generated
     */
    private static final long serialVersionUID = 1L;

    public MissionNotFoundException() {
        super("There is no mission with a matching ID");
    }

}
