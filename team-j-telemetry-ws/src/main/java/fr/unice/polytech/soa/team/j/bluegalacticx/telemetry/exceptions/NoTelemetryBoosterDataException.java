package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class NoTelemetryBoosterDataException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoTelemetryBoosterDataException() {
        super("There is no telemetry booster data available now");
    }
}
