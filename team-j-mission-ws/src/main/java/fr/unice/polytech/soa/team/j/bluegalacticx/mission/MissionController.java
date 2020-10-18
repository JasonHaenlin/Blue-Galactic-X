package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.RocketStatus;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private RestApiService restService;

    @PostMapping("/")
    public void createNewMission(@RequestBody Mission mission) {
        try {

            missionService.createMission(mission);
        } catch (InvalidMissionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/destination/{missionId}")
    public SpaceCoordinate getDestination(@PathVariable String missionId) {
        try {
            return missionService.retrieveDestination(missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/status/{missionId}")
    public Mission setMissionStatus(@RequestBody MissionStatus missionStatus, @PathVariable String missionId) {
        try {
            return missionService.setMissionStatus(missionStatus, missionId);
        } catch (MissionDoesNotExistException | BadPayloadIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/status/{missionId}")
    public Mission retrieveMissionStatus(@PathVariable String missionId) {
        try {
            return missionService.getMissionStatus(missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/status/{missionId}/rocket")
    public void postRocketStatus(@RequestBody RocketStatus status, @PathVariable String missionId) {
        try {
            restService.updateRocketStatus(missionId, status);
        } catch (BadRocketIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/status/{missionId}/payload")
    public void postPayloadStatus(@RequestBody PayloadStatus status, @PathVariable String missionId) {
        try {
            restService.updatePayloadStatus(missionId, status);
        } catch (BadPayloadIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
