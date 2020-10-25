package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;

@Service
public class MissionService {

    public Mission createMission(Mission mission) throws InvalidMissionException {
        mission.setStatus(MissionStatus.PENDING);
        MissionsMocked.missions.add(mission);
        return mission;
    }

    public Mission setMissionStatus(MissionStatus missionStatus, String missionId)
            throws MissionDoesNotExistException, BadPayloadIdException {
        Mission mission = findMissionOrThrow(missionId);
        mission.setStatus(missionStatus);
        return mission;
    }

    public Mission getMissionStatus(String missionId) throws MissionDoesNotExistException {
        Mission mission = findMissionOrThrow(missionId);
        return mission;
    }

    public void updateMissionFromRocketState(MissionStatus status, String id) throws MissionDoesNotExistException {
        findMissionByRocketOrThrow(id).setStatus(status);
    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return MissionsMocked.find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    private Mission findMissionByRocketOrThrow(String id) throws MissionDoesNotExistException {
        return MissionsMocked.findByRocketId(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    public SpaceCoordinate retrieveDestination(String missionId) throws MissionDoesNotExistException {
        Mission m = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId));
        return m.getDestination();
    }

}
