package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private final static Logger LOG = LoggerFactory.getLogger(MissionRPCClient.class);

    WebClient webClientTelemetry;
    WebClient webClientMission;

    // @formatter:off
    public RestService(
            @Value("${api.telemetry.host}") String hostTelemetry,
            @Value("${api.telemetry.port}") String portTelemetry,
            @Value("${api.mission.host}") String hostMission,
            @Value("${api.mission.port}") String portMission
            ) {
        webClientTelemetry = WebClient.create("http://" + hostTelemetry + ":" + portTelemetry + "/telemetry/");
        webClientMission = WebClient.create("http://" + hostMission + ":" + portMission + "/mission/");
    }
     // @formatter:on

    public Mono<SpaceCoordinate> getCoordinatesFromMission(String id) {
        return webClientMission.get().uri("/destination/"+id).retrieve().bodyToMono(SpaceCoordinate.class);
    }

    public void postMissionStatus(SpaceMetrics telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceMetrics.class).retrieve()
                .bodyToMono(Void.class).subscribe();
    }

    public void postTelemetry(SpaceMetrics telemetry) {
        webClientTelemetry.post().uri("/rocket").body(Mono.just(telemetry), SpaceMetrics.class).retrieve()
                .bodyToMono(Void.class).subscribe();
    }
}
