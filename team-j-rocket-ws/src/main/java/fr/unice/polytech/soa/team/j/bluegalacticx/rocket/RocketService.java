package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;

@Service
public class RocketService {
    
    public RocketStatusReply getStatus() {
        return new RocketStatusReply(RocketStatus.READY);
    }
}
