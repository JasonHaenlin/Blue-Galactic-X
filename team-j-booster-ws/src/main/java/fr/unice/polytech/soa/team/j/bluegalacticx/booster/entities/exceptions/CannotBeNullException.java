package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions;

public class CannotBeNullException extends Exception {

    private static final long serialVersionUID = 1L;

    public CannotBeNullException(String on) {
        super(on + " can't be null");
    }
}
