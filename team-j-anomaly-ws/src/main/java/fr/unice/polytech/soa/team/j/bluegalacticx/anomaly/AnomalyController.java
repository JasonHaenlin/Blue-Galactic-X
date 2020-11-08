package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anomaly")
public class AnomalyController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
