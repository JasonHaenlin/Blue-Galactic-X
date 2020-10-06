package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

public class CannotBeNullException extends Exception {

    private static final long serialVersionUID = 1L;

    public CannotBeNullException(String on) {
        super("Cannot replace current " + on + " with a empty one");
    }
}
