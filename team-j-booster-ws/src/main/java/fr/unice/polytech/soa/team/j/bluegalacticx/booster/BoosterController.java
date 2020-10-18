package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterNotAvailableException;

@RestController
@RequestMapping("/booster")
public class BoosterController {

    @Autowired
    private BoosterService boosterService;

    @GetMapping("/available")
    public String getAvailableBoosterID() {
        try {
            return boosterService.getAvailableBoosterID();
        } catch(BoosterNotAvailableException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }


}
