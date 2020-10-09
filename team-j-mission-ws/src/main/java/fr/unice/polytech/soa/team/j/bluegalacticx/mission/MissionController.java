package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @PostMapping("/create")
    public MissionReply createNewMission(@RequestBody Mission mission) {
        try {
            return missionService.createMission(mission);
        } catch (InvalidMissionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("/status/{missionId}")
    public MissionReply setMissionStatus(@RequestBody MissionStatus missionStatus, @PathVariable String missionId) {
        try {
            return missionService.setMissionStatus(missionStatus, missionId);
        } catch (MissionDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
