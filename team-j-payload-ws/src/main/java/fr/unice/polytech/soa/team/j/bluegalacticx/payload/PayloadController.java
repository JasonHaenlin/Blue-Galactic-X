package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telemetry")
public class PayloadController {

    @Autowired
    private PayloadService payloadService;

  
    

}
