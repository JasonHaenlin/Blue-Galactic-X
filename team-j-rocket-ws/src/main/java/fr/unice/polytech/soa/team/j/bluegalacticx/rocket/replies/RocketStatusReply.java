package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;

public class RocketStatusReply {
    
    private RocketStatus status;

    public RocketStatusReply(RocketStatus status) {
        this.status = status;
    }

    public RocketStatus getStatus() {
        return this.status;
    }

    public void setStatus(RocketStatus status) {
        this.status = status;
    }
}
