package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.RocketReport;

public class RocketREST extends RestAPI {

    public RocketREST(String uri) {
        super(uri);
    }

    public RocketMetrics getMetrics(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/status/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<RocketMetrics>() {
        });

    }

    public RocketReport getReport(String rocketId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/report/" + rocketId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<RocketReport>() {
        });

    }

    public void setReport(RocketReport rocketReport) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(rocketReport)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/report")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

    }
}
