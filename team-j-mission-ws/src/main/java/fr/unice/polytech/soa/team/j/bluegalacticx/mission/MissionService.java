package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;

@Service
public class MissionService {

    public MissionReply createMission(Mission mission) throws InvalidMissionException {
        if (invalidMission(mission)) {
            throw new InvalidMissionException();
        }
        return new MissionReply(mission);
    }

    private boolean invalidMission(Mission mission) {
        return mission.getRocketId() <= 0 || mission.getDate() == null;

    }

}
