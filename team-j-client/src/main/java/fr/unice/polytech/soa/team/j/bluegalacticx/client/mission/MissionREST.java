package fr.unice.polytech.soa.team.j.bluegalacticx.client.mission;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadStatus;

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

        public void updatePayloadStatus(PayloadStatus payloadStatus, String missionId)
                        throws IOException, InterruptedException {

                HttpRequest request = HttpRequest.newBuilder()
                                .POST(BodyPublishers.ofString(JsonUtils.toJson(payloadStatus)))
                                .header("Content-Type", "application/json")
                                .uri(URI.create(uri + "/status/" + missionId + "/payload")).build();

                client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        public void updateMissionStatus(MissionStatus missionStatus, String missionId)
                        throws IOException, InterruptedException {

                HttpRequest request = HttpRequest.newBuilder()
                                .POST(BodyPublishers.ofString(JsonUtils.toJson(missionStatus)))
                                .header("Content-Type", "application/json")
                                .uri(URI.create(uri + "/status/" + missionId)).build();

                client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        public Mission retrieveMissionStatus(String missionId) throws IOException, InterruptedException {
                HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                                .uri(URI.create(uri + "/status/" + missionId)).build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                return mapper.readValue(response.body(), new TypeReference<Mission>() {
                });

        }

}
