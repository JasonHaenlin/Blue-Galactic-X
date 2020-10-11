package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

public class BoosterDestroyedException extends Exception {

    private static final long serialVersionUID = 1L;

    public BoosterDestroyedException() {
        super("Booster has already been recorded destroyed");
    }

    public BoosterDestroyedException(String msg) {
        super(msg);
    }
}
