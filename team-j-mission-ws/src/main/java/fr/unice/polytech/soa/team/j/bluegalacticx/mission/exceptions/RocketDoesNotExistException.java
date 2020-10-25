package fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions;

public class RocketDoesNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -1508183778310388986L;

    public RocketDoesNotExistException(String id) {
        super("Rocket of ID : " + id + " does not exist");
    }

}
