package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
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

    public MissionReply setMissionStatus(MissionStatus missionStatus, String missionId) throws MissionDoesNotExistException {
        Mission mission = findMissionOrThrow(missionId);
        mission.setMissionStatus(missionStatus);
        return new MissionReply(mission);
    }

    private boolean invalidMission(Mission mission) {
        return Integer.parseInt(mission.getRocketId()) <= 0 || mission.getDate() == null;

    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return MissionsMocked.find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

}
