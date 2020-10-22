package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class BadPayloadIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadPayloadIdException() {
        super("The payload id was not recognised by the system");
    }

}
