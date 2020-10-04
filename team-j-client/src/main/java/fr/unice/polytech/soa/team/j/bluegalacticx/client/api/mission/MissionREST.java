package fr.unice.polytech.soa.team.j.bluegalacticx.springmission.apiTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.springmission.utils.JsonUtils;

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
