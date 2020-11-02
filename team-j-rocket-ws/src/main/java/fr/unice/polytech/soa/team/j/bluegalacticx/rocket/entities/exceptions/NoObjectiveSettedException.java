package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

public class NoObjectiveSettedException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoObjectiveSettedException(String id) {
        super("No objective has been set on the rocket " + id);
    }

}
