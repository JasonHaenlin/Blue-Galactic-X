package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions;

public class InvalidMissionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1508183778310388986L;

    public InvalidMissionException() {
        super("Invalid mission, give a correct date and an available rocket ID and an available payload ID and a available booster ID");
    }

}
