package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booster")
public class BoosterController {

    @Autowired
    private BoosterService boosterService;

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }


}
