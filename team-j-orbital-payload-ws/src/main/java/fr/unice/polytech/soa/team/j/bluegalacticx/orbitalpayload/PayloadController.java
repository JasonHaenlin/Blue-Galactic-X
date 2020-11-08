package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payload")
public class PayloadController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
