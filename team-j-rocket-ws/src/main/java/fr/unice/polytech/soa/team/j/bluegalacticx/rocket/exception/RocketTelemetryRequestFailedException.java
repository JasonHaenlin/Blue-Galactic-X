package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception;

public class RocketTelemetryRequestFailedException extends Exception {

    private static final String MESSAGE = "Failed to POST telemetry data of the rocket";
    private static final long serialVersionUID = 1L;

    public RocketTelemetryRequestFailedException() {
        super(MESSAGE);
    }

    public RocketTelemetryRequestFailedException(String message) {
        super(message);
    }

}
