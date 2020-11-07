package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missionlogwriter")
public class MissionLogWriterController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
