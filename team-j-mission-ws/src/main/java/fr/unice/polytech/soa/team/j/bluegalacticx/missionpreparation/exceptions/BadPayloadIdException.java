package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions;

public class BadPayloadIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadPayloadIdException() {
        super("The payload doesn't belong to this mission or doesn't exist");
    }

}
