package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.exceptions;

public class NoRocketIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoRocketIdException() {
        super("You need to indicate an ID to check for anomalies on the rocket");
    }
}
