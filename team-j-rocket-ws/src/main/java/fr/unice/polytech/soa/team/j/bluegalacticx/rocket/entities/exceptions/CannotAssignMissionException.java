package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;

public class CannotAssignMissionException extends Exception {

    private static final long serialVersionUID = 1L;

    public CannotAssignMissionException(RocketStatus status) {
        super("Cannot (re)assign new mission to rocket when " + status);
    }
}
