package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;

@RestController
@RequestMapping("/rocket")
public class RocketController {
    
    @Autowired
    private RocketService service;

    @GetMapping("/status")
    public RocketStatusReply getRocketStatus() {
        return service.getStatus();
    }

}
