package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;

public class RocketREST extends RestAPI {

    public RocketREST(String uri) {
        super(uri);
    }

    public SpaceTelemetry getRocketTelemetry(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/telemetry/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<SpaceTelemetry>() {
        });
    }

    public RocketStatus getStatus(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/telemetry/" + rocketId + "/status")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<RocketStatus>() {
        });
    }

    public RocketStatus getGoNoGo(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<RocketStatus>() {
        });
    }

    public void setGoNoGo(String rocketId, GoNg gonogo) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(JsonUtils.toJson(gonogo)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/" + rocketId)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
