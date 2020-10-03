package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @PostMapping("/create")
    public MissionReply createNewMission(@RequestBody Mission mission) throws InvalidMissionException {
        if (invalidMission(mission)) {
            throw new InvalidMissionException();
        }

        return missionService.createMission(mission);
    }

    private boolean invalidMission(Mission mission) {
        return mission.getRocketId() <= 0 || mission.getDate() == null;

    }
}
