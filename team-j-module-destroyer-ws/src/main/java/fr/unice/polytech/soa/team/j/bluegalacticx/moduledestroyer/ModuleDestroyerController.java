package fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/module-destroyer")
public class ModuleDestroyerController {

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
