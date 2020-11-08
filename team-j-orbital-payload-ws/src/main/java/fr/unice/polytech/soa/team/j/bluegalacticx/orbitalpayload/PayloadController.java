package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payload")
public class PayloadController {

    @Autowired
    private OrbitalPayloadService payloadService;

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
