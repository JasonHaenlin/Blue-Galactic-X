package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception;

public class RocketDoesNotExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public RocketDoesNotExistException(String id) {
        super("Rocket information does not exist in the system for { \"id\": " + id + " }");
    }

}
