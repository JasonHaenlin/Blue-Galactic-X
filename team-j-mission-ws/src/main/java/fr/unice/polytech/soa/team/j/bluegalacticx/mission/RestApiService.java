package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
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

    private final WebClient rocketClient;
    private final WebClient payloadClient;

    // @formatter:off
    public RestApiService(
        @Value("${api.payload.host}") String hostPayload,
        @Value("${api.payload.port}") String portPayload,
        @Value("${api.rocket.host}") String hostRocket,
        @Value("${api.rocket.port}") String portRocket
        ) {
        rocketClient = WebClient.create("http://" + hostRocket + ":" + portRocket + "/telemetry/");
        payloadClient = WebClient.create("http://" + hostPayload + ":" + portPayload + "/payload/");
    }
    // @formatter:on

    public void updateRocketStatus(String missionId, RocketStatus status)
            throws BadRocketIdException, MissionDoesNotExistException {
        String rId = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId))
                .getRocketId();
        rocketClient.post().uri(URI.create("/status/" + rId)).body(BodyInserters.fromValue(status)).retrieve()
                .bodyToMono(Void.class).subscribe();
    }

    public void updatePayloadStatus(String missionId, PayloadStatus status)
            throws BadPayloadIdException, MissionDoesNotExistException {
        String pId = MissionsMocked.find(missionId).orElseThrow(() -> new MissionDoesNotExistException(missionId))
                .getPayloadId();
        payloadClient.post().uri("/"+pId).body(BodyInserters.fromValue(status)).retrieve()
                .bodyToMono(Void.class).subscribe();
    }

}
