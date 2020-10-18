package fr.unice.polytech.soa.team.j.bluegalacticx.client.booster.entities.exceptions;

public class BoosterDoesNotExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public BoosterDoesNotExistException(String id) {
        super("Booster information does not exist in the system for { \"id\": " + id + " }");
    }

}
