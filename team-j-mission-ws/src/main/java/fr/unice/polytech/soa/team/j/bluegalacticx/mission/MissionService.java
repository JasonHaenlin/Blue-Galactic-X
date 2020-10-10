package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;

import java.io.IOException;
import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.apiCallTools.RestApiTools;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.InvalidMissionException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.RocketStatus;

@Service
public class MissionService {

    public static String ROCKET_REST_ENDPOINT = "localhost:8080/rocket";
    public static String PAYLOAD_REST_ENDPOINT = "localhost:8050/payload";

    private final WebClient rocketClient = WebClient.create(ROCKET_REST_ENDPOINT);
    private final WebClient payloadClient = WebClient.create(PAYLOAD_REST_ENDPOINT);

    public MissionReply createMission(Mission mission) throws InvalidMissionException {
        if (invalidMission(mission)) {
            throw new InvalidMissionException();
        }
        return new MissionReply(mission);
    }

    public MissionReply setMissionStatus(MissionStatus missionStatus, String missionId)
            throws MissionDoesNotExistException {
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

    public void updateRocketStatus(String id, RocketStatus status) throws BadRocketIdException {
        // new RestApiTools(ROCKET_REST_ENDPOINT + "/status/" + id).post(status);
        rocketClient.post().uri(URI.create("/status/" + id)).body(BodyInserters.fromValue(status));
    }

    public void updatePayloadStatus(String id, PayloadStatus status) throws BadPayloadIdException {
        // new RestApiTools(PAYLOAD_REST_ENDPOINT + id).post(status);
        payloadClient.post().uri(URI.create("/" + id)).body(BodyInserters.fromValue(status));
    }

    public SpaceCoordinate retrieveDestination(String missionId) throws MissionDoesNotExistException {
        Mission m = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId));
        return m.getDestination();
    }

}
