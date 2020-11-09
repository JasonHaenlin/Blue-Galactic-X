package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.DepartmentGoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.exceptions.RocketDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.producers.DepartmentStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.producers.MissionPreparationProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

@Service
public class MissionPreparationService {

    private List<Mission> missions = new ArrayList<>();

    @Autowired
    private DepartmentStatusProducer departmentStatusProducer;

    @Autowired
    private MissionPreparationProducer missionPreparationProducer;

    public Mission createMission(Mission mission) throws InvalidMissionException {
        if (invalidMission(mission))
            throw new InvalidMissionException();
        mission.updateGoNogo(Department.MISSION, false);
        missions.add(mission);
        return mission;
    }

    private boolean invalidMission(Mission mission) {
        return (mission.getRocketId() == null || mission.getPayloadId() == null
                || (mission.getBoosterIds()[0] == null || mission.getBoosterIds()[1] == null));
    }

    public void updateMissionGoNogo(Department department, boolean status) {
        missions.forEach(m -> m.updateGoNogo(department, status));
    }

    public DepartmentGoNg retrieveGoNogoStatus(String id) throws MissionDoesNotExistException {
        return findMissionOrThrow(id).getGoNogos();
    }

    public DepartmentGoNg makeGoNogo(Boolean gonogo, String id) throws MissionDoesNotExistException {
        Mission m = findMissionOrThrow(id);
        m.updateGoNogo(Department.MISSION, gonogo);
        departmentStatusProducer.notifyDepartmentStatus(m.getRocketId(), gonogo);
        if (gonogo)
            missionPreparationProducer.notifyNewMissionReady(m);
        return m.getGoNogos();
    }

    public void updateMissionGoNogo(Department department, boolean status, String id)
            throws RocketDoesNotExistException {
        findMissionByRocketOrThrow(id).updateGoNogo(department, status);
    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    private Mission findMissionByRocketOrThrow(String id) throws RocketDoesNotExistException {
        return findByRocketId(id).orElseThrow(() -> new RocketDoesNotExistException(id));
    }

    public SpaceCoordinate retrieveDestination(String missionId) throws MissionDoesNotExistException {
        Mission m = find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId));
        return m.getDestination();
    }

    private Optional<Mission> find(String id) {
        return missions.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    private Optional<Mission> findByRocketId(String id) {
        return missions.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

}
