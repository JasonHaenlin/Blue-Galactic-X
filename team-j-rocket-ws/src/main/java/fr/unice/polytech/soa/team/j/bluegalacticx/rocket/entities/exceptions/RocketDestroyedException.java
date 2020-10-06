package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

public class RocketDestroyedException extends Exception {

    private static final long serialVersionUID = 1L;

    public RocketDestroyedException() {
        super("Rocket has been recorded destroyed");
    }

    public RocketDestroyedException(String msg) {
        super(msg);
    }
}
