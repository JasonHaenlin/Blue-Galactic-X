package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities.Payload;

@RestController
@RequestMapping("/payload")
public class PayloadController {

    @Autowired
    OrbitalPayloadService orbitalPayloadService;

    @GetMapping("")
    public List<Payload> getPayloads(){
        return orbitalPayloadService.retrievePayloads();
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
