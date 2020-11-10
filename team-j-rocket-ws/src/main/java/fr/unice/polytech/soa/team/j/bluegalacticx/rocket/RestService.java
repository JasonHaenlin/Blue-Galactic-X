package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import reactor.core.publisher.Mono;

/**
 * RestService
 */
@Service
public class RestService {

    WebClient webClientMission;
    WebClient webClientBooster;

    // @formatter:off
    public RestService(
            @Value("${api.mission.host}") String hostMission,
            @Value("${api.mission.port}") String portMission,
            @Value("${api.booster.host}") String hostBooster,
            @Value("${api.booster.port}") String portBooster
            ) {
        webClientMission = WebClient.create("http://" + hostMission + ":" + portMission + "/mission-preparation/");
        webClientBooster = WebClient.create("http://" + hostBooster + ":" + portBooster + "/booster/");
    }
     // @formatter:on

    public Mono<SpaceCoordinate> getCoordinatesFromMission(String id) {
        return webClientMission.get().uri("/destination/" + id).retrieve().bodyToMono(SpaceCoordinate.class);
    }

    public Mono<String> getAvailableRocketID() {
        return webClientBooster.get().uri("/available").retrieve().bodyToMono(String.class);
    }
}
