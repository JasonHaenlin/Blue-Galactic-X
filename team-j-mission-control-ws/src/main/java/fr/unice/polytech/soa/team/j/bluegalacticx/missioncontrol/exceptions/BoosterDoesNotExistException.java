package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions;

public class BoosterDoesNotExistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -3302851332329444674L;

    public BoosterDoesNotExistException(String id) {
        super("Booster of ID : " + id + " does not exist");
    }

}
