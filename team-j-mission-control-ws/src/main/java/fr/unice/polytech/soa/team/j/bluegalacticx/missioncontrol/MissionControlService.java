package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.PayloadDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.RocketDoesNotExistException;

@Service
public class MissionControlService {

    private List<Mission> missions = new ArrayList<>();

    public void storeMission(Mission mission) {
        missions.add(mission);
    }

    public Mission setMissionStatus(MissionStatus missionStatus, String missionId) throws MissionDoesNotExistException {
        Mission mission = findMissionOrThrow(missionId);
        mission.setStatus(missionStatus);
        return mission;
    }

    public Mission getMissionStatus(String missionId) throws MissionDoesNotExistException {
        Mission mission = findMissionOrThrow(missionId);
        return mission;
    }

    public Mission retrieveMission(String id) throws MissionDoesNotExistException {
        return find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    public void storeRocketStatus(String rocketId, RocketStatus rocketStatus) throws RocketDoesNotExistException {
        findMissionByRocketOrThrow(rocketId).addRocketStatusToRules(rocketStatus);
    }

    public void storeBoosterStatus(String boosterId, BoosterStatus boosterStatus) throws BoosterDoesNotExistException {
        findMissionByBoosterOrThrow(boosterId).addBoosterStatusToRules(boosterStatus);
    }

    public void storePayloadStatus(String payloadId, PayloadStatus payloadStatus) throws PayloadDoesNotExistException {
        findMissionByPayloadOrThrow(payloadId).addPayloadStatusToRules(payloadStatus);
    }

    public void updateMissionFromRocketState(String id) throws RocketDoesNotExistException {
        findMissionByRocketOrThrow(id).updateMissionStatus();
    }

    public void updateMissionFromBoosterState(String id) throws BoosterDoesNotExistException {
        findMissionByBoosterOrThrow(id).updateMissionStatus();
    }

    public void updateMissionFromPayloadState(String id) throws PayloadDoesNotExistException {
        findMissionByPayloadOrThrow(id).updateMissionStatus();
    }

    private Mission findMissionOrThrow(String id) throws MissionDoesNotExistException {
        return find(id).orElseThrow(() -> new MissionDoesNotExistException(id));
    }

    private Mission findMissionByRocketOrThrow(String id) throws RocketDoesNotExistException {
        return findByRocketId(id).orElseThrow(() -> new RocketDoesNotExistException(id));
    }

    private Mission findMissionByPayloadOrThrow(String id) throws PayloadDoesNotExistException {
        return findByPayloadId(id).orElseThrow(() -> new PayloadDoesNotExistException(id));
    }

    private Mission findMissionByBoosterOrThrow(String id) throws BoosterDoesNotExistException {
        return findByBoosterId(id).orElseThrow(() -> new BoosterDoesNotExistException(id));
    }

    private Optional<Mission> find(String id) {
        return missions.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public Optional<Mission> findByRocketId(String id) {
        return missions.stream().filter(m -> m.getRocketId().equals(id)).findFirst();
    }

    public Optional<Mission> findByBoosterId(String id) {
        return missions.stream().filter(m -> m.retrieveBoosterId(id) != null).findFirst();
    }

    public Optional<Mission> findByPayloadId(String id) {
        return missions.stream().filter(m -> m.getPayloadId().equals(id)).findFirst();
    }

}
