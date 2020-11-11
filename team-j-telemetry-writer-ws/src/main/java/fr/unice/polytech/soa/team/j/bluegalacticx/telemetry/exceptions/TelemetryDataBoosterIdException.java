package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class TelemetryDataBoosterIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public TelemetryDataBoosterIdException() {
        super("You need to indicate an ID to create a telemetry for the booster");
    }
}
