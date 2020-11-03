package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;

public class WeatherREST extends RestAPI {

    public WeatherREST(String uri) {
        super(uri);
    }

    public WeatherReport getCurrentWeather() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<WeatherReport>() {
        });
    }

    public void setGoNoGo(GoNg gonogo) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(JsonUtils.toJson(gonogo)))
                .header("Content-Type", "application/json").uri(URI.create(uri)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
