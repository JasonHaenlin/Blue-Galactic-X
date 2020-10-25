package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.kafka.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

@Service
public class MissionService {

    @Autowired
    private DepartmentStatusProducer departmentStatusProducer;

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

    public void updateMissionGoNogo(Department department, boolean status) {
        MissionsMocked.missions.forEach(m -> m.updateGoNogo(department, status));
    }

    public Map<Department, Boolean> retrieveGoNogoStatus(String id) throws MissionDoesNotExistException {
        return findMissionOrThrow(id).getGoNogos();
    }

    public void makeGoNogo(Boolean gonogo, String id) throws MissionDoesNotExistException {
        Mission m = findMissionOrThrow(id);
        m.updateGoNogo(Department.MISSION, gonogo);
        departmentStatusProducer.notifyDepartmentStatus(m.getRocketId(), gonogo);
    }

    public void updateMissionGoNogo(Department department, boolean status, String id)
            throws RocketDoesNotExistException {
        findMissionByRocketOrThrow(id).updateGoNogo(department, status);
    }

    public void updateMissionFromRocketState(MissionStatus status, String id) throws RocketDoesNotExistException {
        findMissionByRocketOrThrow(id).setStatus(status);
    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return MissionsMocked.find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    private Mission findMissionByRocketOrThrow(String id) throws RocketDoesNotExistException {
        return MissionsMocked.findByRocketId(id).orElseThrow(() -> new RocketDoesNotExistException(id));
    }

    public SpaceCoordinate retrieveDestination(String missionId) throws MissionDoesNotExistException {
        Mission m = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId));
        return m.getDestination();
    }

}
