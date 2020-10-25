package fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import org.json.simple.parser.ParseException;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.TelemetryRocketData;

public class TelemetryREST extends RestAPI {

    public TelemetryREST(String uri) {
        super(uri);
    }

    public void sendTelemetryRocketData(RocketMetrics rocketData, String rocketId)
            throws IOException, InterruptedException, ParseException {
        String jsonRocketData = JsonUtils.toJson(rocketData);

        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(jsonRocketData))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/rocket")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public List<TelemetryRocketData> retrieveTelemetryRocketData(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/rocket/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<TelemetryRocketData>>() {
        });

    }

    public List<TelemetryBoosterData> retrieveTelemetryBoosterData(String boosterId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/booster/" + boosterId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<TelemetryBoosterData> >() {
        });

    }

    public Set<Anomaly> checkForAnomalies() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/anomalies")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<Set<Anomaly>>() {
        });

    }

}
