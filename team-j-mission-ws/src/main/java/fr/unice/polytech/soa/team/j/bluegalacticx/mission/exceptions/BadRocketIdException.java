package fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions;

public class BadRocketIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadRocketIdException() {
        super("The rocket doesn't belong to this mission or doesn't exist");
    }

}
