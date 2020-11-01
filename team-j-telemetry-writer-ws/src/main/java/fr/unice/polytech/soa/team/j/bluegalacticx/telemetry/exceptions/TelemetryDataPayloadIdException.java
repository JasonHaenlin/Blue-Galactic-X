package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class TelemetryDataPayloadIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public TelemetryDataPayloadIdException() {
        super("The payload id was not recognised by the system");
    }

}
