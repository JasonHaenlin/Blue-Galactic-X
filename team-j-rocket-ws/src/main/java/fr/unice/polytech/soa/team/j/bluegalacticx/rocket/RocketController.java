package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.CannotBeNullException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketDoesNotExistException;

@RestController
@RequestMapping("/rocket")
@ResponseBody
public class RocketController {

    @Autowired
    private RocketService service;

    @GetMapping("/telemetry/{rocketId}")
    public SpaceTelemetry getRocketTelemetry(@PathVariable String rocketId) {
        try {
            return service.getLastTelemetry(rocketId);
        } catch (RocketDestroyedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RocketDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping()
    public void createRocket(@RequestBody Rocket rocket) {
        try {
            service.addNewRocket(rocket);
        } catch (CannotBeNullException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/telemetry/{rocketId}/status")
    public RocketStatus getRocketStatus(@PathVariable String rocketId) {
        try {
            return service.getRocketStatus(rocketId);
        } catch (RocketDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{rocketId}")
    public void setGoNoGo(@PathVariable String rocketId, @RequestBody GoNg go) {
        service.setRocketDepartmentStatus(rocketId, go.getGong());
    }

    @GetMapping("/{rocketId}")
    public RocketStatus getGoNoGo(@PathVariable String rocketId) {
        try {
            return service.getRocketDepartmentStatus(rocketId);
        } catch (RocketDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
