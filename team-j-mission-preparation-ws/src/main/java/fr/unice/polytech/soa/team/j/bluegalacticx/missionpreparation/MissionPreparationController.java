package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.DepartmentGoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.MissionDoesNotExistException;

@RestController
@RequestMapping("/mission-preparation")
public class MissionPreparationController {

    @Autowired
    private MissionPreparationService missionPreparationService;

    @PostMapping("/")
    public Mission createNewMission(@RequestBody Mission mission) {
        try {
            return missionPreparationService.createMission(mission);
        } catch (InvalidMissionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    public List<Mission> getMissions() {
        return missionPreparationService.retrieveMissions();
    }

    @GetMapping("/destination/{missionId}")
    public SpaceCoordinate getDestination(@PathVariable String missionId) {
        try {
            return missionPreparationService.retrieveDestination(missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{missionId}/gonogo")
    public DepartmentGoNg getGoNogoStatus(@PathVariable String missionId) {
        try {
            return missionPreparationService.retrieveGoNogoStatus(missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{missionId}/gonogo")
    public DepartmentGoNg updateGoNogoStatus(@RequestBody GoNg gonogo, @PathVariable String missionId) {
        try {
            return missionPreparationService.makeGoNogo(gonogo.getGong(), missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
