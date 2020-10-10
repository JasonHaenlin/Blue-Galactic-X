package fr.unice.polytech.soa.team.j.bluegalacticx.mission.apiCallTools;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestApiTools {
    protected final String uri;
    protected final HttpClient client = HttpClient.newHttpClient();
    protected final ObjectMapper mapper = new ObjectMapper();

    public RestApiTools(final String uri) {
        this.uri = uri;
    }

    public void post(Object body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(body)))
                .header("Content-Type", "application/json").uri(URI.create(uri)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
