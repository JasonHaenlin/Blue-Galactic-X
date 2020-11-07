package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterNotAvailableException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.CannotBeNullException;

@RestController
@RequestMapping("/booster")
public class BoosterController {

    @Autowired
    private BoosterService boosterService;

    @GetMapping("/available")
    public String getAvailableBoosterID() {
        try {
            return boosterService.getAvailableBoosterID();
        } catch (BoosterNotAvailableException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping()
    public void createBooster(@RequestBody Booster booster) {
        try {
            boosterService.addNewBooster(booster);
        } catch (CannotBeNullException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Booster lookForBooster(@PathVariable String id) {
        try {
            return boosterService.retrieveBooster(id);
        } catch (BoosterDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
