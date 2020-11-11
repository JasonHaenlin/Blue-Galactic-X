package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
