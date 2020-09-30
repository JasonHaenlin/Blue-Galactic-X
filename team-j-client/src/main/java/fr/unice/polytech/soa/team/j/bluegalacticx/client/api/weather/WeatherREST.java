package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.models.WeatherStatus;

public class WeatherREST extends RestAPI{

    public WeatherREST(String uri) {
        super(uri);
    }

    public WeatherStatus getStatus() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<WeatherStatus>() {
        });
    }
}
