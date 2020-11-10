package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.MissionDoesNotExistException;

@RestController
@RequestMapping("/mission-control")
public class MissionControlController {

    @Autowired
    private MissionControlService missionControlService;

    @GetMapping("/{missionId}")
    public Mission retrieveMission(@PathVariable String missionId) {
        try {
            return missionControlService.getMissionStatus(missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
