package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions;

public class BoosterNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public BoosterNotAvailableException() {
        super("There isn't any Booster available.");
    }

    public BoosterNotAvailableException(String id) {
        super("Booster isn't available { \"id\": " + id + " }");
    }
    
}
