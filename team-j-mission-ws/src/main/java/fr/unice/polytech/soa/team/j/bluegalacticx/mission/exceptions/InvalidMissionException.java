package fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions;

import javax.servlet.ServletException;

public class InvalidMissionException extends ServletException {

    /**
     *
     */
    private static final long serialVersionUID = -1508183778310388986L;

    public InvalidMissionException() {
        super("Invalid mission, give a correct date and an available rocket ID (positive and greather than 0)");
    }

}
