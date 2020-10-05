package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import fr.unice.polytech.soa.team.j.bluegalacticx.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities.Mission;

public class MissionREST extends RestAPI {

    public MissionREST(String uri) {
        super(uri);
    }


    public String createNewMission(Mission mission) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(mission)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/create")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.toString();

    }
}
