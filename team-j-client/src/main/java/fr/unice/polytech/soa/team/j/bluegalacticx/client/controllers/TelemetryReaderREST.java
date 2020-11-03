package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

public class TelemetryReaderREST extends RestAPI {

    public TelemetryReaderREST(String uri) {
        super(uri);
    }


    public List<TelemetryRocketData> retrieveTelemetryRocketData(String rocketId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/rocket/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<TelemetryRocketData>>() {
        });

    }

    public List<TelemetryBoosterData> retrieveTelemetryBoosterData(String boosterId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/booster/" + boosterId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<TelemetryBoosterData>>() {
        });

    }

}
