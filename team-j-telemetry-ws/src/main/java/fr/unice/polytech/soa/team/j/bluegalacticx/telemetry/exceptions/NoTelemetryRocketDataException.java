package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class NoTelemetryRocketDataException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoTelemetryRocketDataException() {
        super("There is no telemetry rocket data available now");
    }
}
