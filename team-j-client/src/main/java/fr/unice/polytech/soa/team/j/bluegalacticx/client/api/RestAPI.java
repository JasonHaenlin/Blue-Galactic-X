package fr.unice.polytech.soa.team.j.bluegalacticx.client.api;

import java.net.http.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class RestAPI {
    protected final String uri;
    protected final HttpClient client = HttpClient.newHttpClient();
    protected final ObjectMapper mapper = new ObjectMapper();

    public RestAPI(final String uri) {
        this.uri = uri;
    }
}
