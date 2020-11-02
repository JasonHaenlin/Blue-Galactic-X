package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class RestAPI {
    protected final String uri;
    protected final HttpClient client = HttpClient.newHttpClient();
    protected final ObjectMapper mapper = new ObjectMapper();

    public RestAPI(final String uri) {
        this.uri = uri;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    protected <T> T get(String route, Class<T> tClass) {
        try {
            HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                    .uri(URI.create(uri + route)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (tClass == null) {
                return null;
            }
            return mapper.readValue(response.body(), mapper.getTypeFactory().constructType(tClass));
        } catch (IOException | InterruptedException e) {
            // DO NOTHING
        }
        return null;
    }

    protected <T> T post(String route, Class<T> tClass, Object body) {
        try {
            HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(body)))
                    .header("Content-Type", "application/json").uri(URI.create(uri + route)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (tClass == null) {
                return null;
            }
            return mapper.readValue(response.body(), mapper.getTypeFactory().constructType(tClass));
        } catch (IOException | InterruptedException e) {
            // DO NOTHING
        }
        return null;
    }

    protected <T> T put(String route, Class<T> tClass, Object body) {
        try {
            HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(JsonUtils.toJson(body)))
                    .header("Content-Type", "application/json").uri(URI.create(uri + route)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (tClass == null) {
                return null;
            }
            return mapper.readValue(response.body(), mapper.getTypeFactory().constructType(tClass));
        } catch (IOException | InterruptedException e) {
            // DO NOTHING
        }
        return null;
    }
}
