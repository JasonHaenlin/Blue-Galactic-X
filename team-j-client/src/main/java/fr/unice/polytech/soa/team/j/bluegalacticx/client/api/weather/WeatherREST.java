package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherStatus;


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

    public String getReport() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri+"/report")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonUtils.toJson(response);
    }

    public void setReport(WeatherReport weatherReport) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(weatherReport)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/report")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        
    }
}


