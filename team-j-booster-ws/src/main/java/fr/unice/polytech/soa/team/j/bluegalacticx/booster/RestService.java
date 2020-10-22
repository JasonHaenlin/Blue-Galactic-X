package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetryData;
import reactor.core.publisher.Mono;

/**
 * RestService
 */
@Service
public class RestService {

    WebClient webClientTelemetry;

    // @formatter:off
    public RestService(
            @Value("${api.telemetry.host}") String hostTelemetry,
            @Value("${api.telemetry.port}") String portTelemetry
            ) {
        webClientTelemetry = WebClient.create("http://" + hostTelemetry + ":" + portTelemetry + "/telemetry/");
    }
     // @formatter:on

    public void postTelemetry(BoosterTelemetryData telemetry) {
        webClientTelemetry.post().uri("/booster").body(Mono.just(telemetry), 
                BoosterTelemetryData.class).retrieve()
                .bodyToMono(Void.class).subscribe();
    }

}
