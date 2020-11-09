package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions;

public class InvalidPayloadException extends Exception {

    /**
     * auto-generated
     */
    private static final long serialVersionUID = 4133499328083489405L;

    public InvalidPayloadException() {
        super("You cannot create a payload with those parameters, please change and retry");
    }

}
