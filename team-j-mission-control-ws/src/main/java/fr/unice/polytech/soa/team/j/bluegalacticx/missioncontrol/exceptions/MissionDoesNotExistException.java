package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions;

public class MissionDoesNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1508183778310388986L;

    public MissionDoesNotExistException(String id) {
        super("Mission of ID : " + id + " does not exist");
    }

}
