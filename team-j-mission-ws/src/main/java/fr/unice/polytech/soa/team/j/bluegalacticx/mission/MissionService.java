package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.stereotype.Service;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;
@Service
public class MissionService {

    public MissionReply createMission(String name) {
        return new MissionReply(name);
    }

}
