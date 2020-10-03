package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;

@Service
public class RocketService {
    
    public RocketStatusReply getStatus() {
        //TODO  generate random data
        RocketStatus status = new RocketStatus().setIrradiance(10)
                                    .setVelocityVariation(10)
                                    .setTemperature(10)
                                    .setGroundVibration(10)
                                    .setBoosterRGA(10)
                                    .setMidRocketRGA(10);
        return new RocketStatusReply(status);
    }
}
