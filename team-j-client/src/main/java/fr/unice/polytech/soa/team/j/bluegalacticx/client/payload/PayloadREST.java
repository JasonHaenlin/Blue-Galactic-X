package fr.unice.polytech.soa.team.j.bluegalacticx.client.payload;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.JsonUtils;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadStatus;

public class PayloadREST extends RestAPI {

    public PayloadREST(String uri) {
        super(uri);
    }

    public Payload createNewPayload(Payload payload) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(payload)))
                .header("Content-Type", "application/json").uri(URI.create(uri)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<Payload>() {
        });
    }

    public Payload changePayloadStatus(PayloadStatus payload, String payloadId)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(JsonUtils.toJson(payload)))
                .header("Content-Type", "application/json").uri(URI.create(uri + "/" + payloadId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<Payload>() {
        });
    }

    public Payload retrievePayload(String payloadId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json")
                .uri(URI.create(uri + "/" + payloadId)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<Payload>() {
        });

    }

}
