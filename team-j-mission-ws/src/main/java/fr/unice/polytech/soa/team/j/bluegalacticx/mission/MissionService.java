package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;

@Service
public class MissionService {

    @Autowired
    private RestApiService restService;

    public Mission createMission(Mission mission) throws InvalidMissionException {
        if (invalidMission(mission)) {
            throw new InvalidMissionException();
        }
        MissionsMocked.missions.add(mission);
        return mission;
    }

    public MissionStatus setMissionStatus(MissionStatus missionStatus, String missionId)
            throws MissionDoesNotExistException, BadPayloadIdException {
        Mission mission = findMissionOrThrow(missionId);
        mission.setMissionStatus(missionStatus);
        restService.updatePayloadStatus(missionId, PayloadStatus.values()[missionStatus.ordinal()]);
        return mission.getMissionStatus();
    }

    public Mission getMissionStatus(String missionId)
            throws MissionDoesNotExistException {
        Mission mission = findMissionOrThrow(missionId);
        return mission;
    }

    private boolean invalidMission(Mission mission) {
        return mission.getDate() == null;

    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return MissionsMocked.find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    public SpaceCoordinate retrieveDestination(String missionId) throws MissionDoesNotExistException {
        Mission m = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId));
        return m.getDestination();
    }

}
