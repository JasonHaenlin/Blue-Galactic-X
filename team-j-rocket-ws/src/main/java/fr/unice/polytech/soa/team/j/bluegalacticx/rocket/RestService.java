package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.RocketTelemetryRequestFailedException;
import reactor.core.publisher.Mono;

/**
 * RestService
 */
@Service
public class RestService {

    WebClient webClientTelemetry;
    WebClient webClientMission;
    WebClient webClientBooster;

    // @formatter:off
    public RestService(
            @Value("${api.telemetry.host}") String hostTelemetry,
            @Value("${api.telemetry.port}") String portTelemetry,
            @Value("${api.mission.host}") String hostMission,
            @Value("${api.mission.port}") String portMission,
            @Value("${api.booster.host}") String hostBooster,
            @Value("${api.booster.port}") String portBooster
            ) {
        webClientTelemetry = WebClient.create("http://" + hostTelemetry + ":" + portTelemetry + "/telemetry/");
        webClientMission = WebClient.create("http://" + hostMission + ":" + portMission + "/mission/");
        webClientBooster = WebClient.create("http://" + hostBooster + ":" + portBooster + "/booster/");
    }
     // @formatter:on

    public Mono<SpaceCoordinate> getCoordinatesFromMission(String id) {
        return webClientMission.get().uri("/destination/" + id).retrieve().bodyToMono(SpaceCoordinate.class);
    }

    public void postMissionStatus(SpaceTelemetry telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceTelemetry.class).retrieve()
                .bodyToMono(Void.class).subscribe();
    }

    public void postTelemetry(SpaceTelemetry telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceTelemetry.class).retrieve()
                .bodyToMono(Void.class).onErrorResume(e -> Mono.error(new RocketTelemetryRequestFailedException()))
                .subscribe();
    }

    public Mono<String> getAvailableRocketID() {
        return webClientBooster.get().uri("/available").retrieve().bodyToMono(String.class);
    }
}
