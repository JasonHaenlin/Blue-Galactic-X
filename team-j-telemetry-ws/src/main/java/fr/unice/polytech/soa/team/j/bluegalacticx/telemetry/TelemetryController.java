package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

  
    

}
