package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import java.io.IOException;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;

public class PayloadREST extends RestAPI {

    public PayloadREST(String port) {
        super("localhost", port, "payload");
    }

    public PayloadREST(String host, String port) {
        super(host, port, "payload");
    }

    public Payload createNewPayload(Payload payload) throws IOException, InterruptedException {
        return post("/", Payload.class, payload);
    }

    public Payload changePayloadStatus(PayloadStatus payload, String payloadId)
            throws IOException, InterruptedException {
        return post("/" + payloadId, Payload.class, payload);
    }

    public Payload retrievePayload(String payloadId) {
        return get("/" + payloadId, Payload.class);
    }

}
