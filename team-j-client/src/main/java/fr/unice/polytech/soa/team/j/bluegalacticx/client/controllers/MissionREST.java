package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

public class MissionREST extends RestAPI {

    public MissionREST(String uri) {
        super(uri);
    }

    public String createNewMission(Mission mission) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(mission)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.toString();
    }

    public Mission retrieveMission(String missionId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/" + missionId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<Mission>() {
        });
    }

    public Map<Department, Boolean> getMissionGoNg(String missionId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/" + missionId + "/gonogo")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(), new TypeReference<Map<Department, Boolean>>() {
        });
    }

    public void updateMissionGoNg(String missionId, GoNg goNg) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().PUT(BodyPublishers.ofString(JsonUtils.toJson(goNg)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/" + missionId + "/gonogo")).build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
