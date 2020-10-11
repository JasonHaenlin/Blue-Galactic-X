package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;
import reactor.core.publisher.Mono;

/**
 * RestService
 */
@Service
public class RestService {

    WebClient webClientTelemetry = WebClient.create("http://telemetry:8090/telemetry/");
    WebClient webClientMission = WebClient.create("http://localhost:8070/mission/");

    public Mono<SpaceCoordinate> getCoordinatesFromMission(String id) {
        return webClientMission.get().uri(id + "/coordinates").retrieve().bodyToMono(SpaceCoordinate.class);
    }

    public void postMissionStatus(SpaceMetrics telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceMetrics.class);
    }

    public void postTelemetry(SpaceMetrics telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceMetrics.class);
    }
}
