package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class NoTelemetryPayloadDataException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoTelemetryPayloadDataException() {
        super("There is no data for the corresponding payload");
    }

}
