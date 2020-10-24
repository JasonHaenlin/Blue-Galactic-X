package fr.unice.polytech.soa.team.j.bluegalacticx.booster.exceptions;

public class BoosterTelemetryRequestFailedException extends Exception {

    private static final String MESSAGE = "Failed to POST telemetry data of the booster";
    private static final long serialVersionUID = 1L;

    public BoosterTelemetryRequestFailedException() {
        super(MESSAGE);
    }

    public BoosterTelemetryRequestFailedException(String message) {
        super(message);
    }

}
