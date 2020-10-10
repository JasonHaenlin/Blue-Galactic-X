package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks.MissionsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadPayloadIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.BadRocketIdException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.exceptions.MissionDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.requestModels.RocketStatus;

@Service
public class RestApiService {

    private final WebClient rocketClient = WebClient.create("localhost:8080/rocket");
    private final WebClient payloadClient = WebClient.create("localhost:8050/payload");

    public void updateRocketStatus(String missionId, RocketStatus status)
            throws BadRocketIdException, MissionDoesNotExistException {
        String rId = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId))
                .getRocketId();
        rocketClient.post().uri(URI.create("/status/" + rId)).body(BodyInserters.fromValue(status));
    }

    public void updatePayloadStatus(String missionId, PayloadStatus status)
            throws BadPayloadIdException, MissionDoesNotExistException {
        String pId = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId))
                .getRocketId();
        payloadClient.post().uri(URI.create("/" + pId)).body(BodyInserters.fromValue(status));
    }

}
