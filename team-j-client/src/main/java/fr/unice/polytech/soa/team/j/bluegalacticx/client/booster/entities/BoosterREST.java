package fr.unice.polytech.soa.team.j.bluegalacticx.client.booster.entities;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;

public class BoosterREST extends RestAPI {

    public BoosterREST(String uri) {
        super(uri);
    }

    public void createBooster(Booster booster) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(booster)))
                .header("Content-Type", "application/json").uri(URI.create(uri)).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
