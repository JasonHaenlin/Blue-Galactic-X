package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

public class NoSameStatusException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoSameStatusException(String status) {
        super("Cannot trigger the same sequence twice : " + status);
    }
}
