package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions;

public class PayloadNotFoundException extends Exception{

    /**
     * auto-generated
     */
    private static final long serialVersionUID = 1L;

    public PayloadNotFoundException() {
        super("There is no payload with a matching ID");
    }

}
