package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions;

public class PayloadDoesNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -6553432973801511287L;

    public PayloadDoesNotExistException(String id) {
        super("Payload of ID : " + id + " does not exist");
    }

}
