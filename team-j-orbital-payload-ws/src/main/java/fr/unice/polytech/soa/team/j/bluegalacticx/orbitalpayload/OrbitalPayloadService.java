package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers.PayloadStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.kafka.producers.TelemetryPayloadProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;

@Service
public class OrbitalPayloadService {

    private final List<Payload> payloads = new ArrayList<>();

    @Autowired
    PayloadStatusProducer payloadStatusProducer;

    @Autowired
    TelemetryPayloadProducer telemetryPayloadProducer;

    public void activateTelemetryRecorder(String id) throws PayloadNotFoundException {
        Payload p = retrievePayload(id);
        p.setDeployed(true);
        p.setStatus(PayloadStatus.IN_ROLLOUT);
        // shortcut for now
        payloadStatusProducer.notifyDeployedPayloadDelivered(p.getPayloadId(), p.getMissionId());
        p.setStatus(PayloadStatus.DELIVERED);
    }

    public void sendNewTelemetry() {
        for (Payload p : payloads) {
            if (p.isDeployed()) {
                p.getApi().recoverCurrentPosition();
                telemetryPayloadProducer.notifyDeployedPayloadEvent(p);
            }
        }
    }

    public void addPayload(Payload p) {
        payloads.add(p);
    }

    public Payload retrievePayload(String id) throws PayloadNotFoundException {
        return find(id).orElseThrow(() -> new PayloadNotFoundException());
    }

    private Optional<Payload> find(String id) {
        return payloads.stream().filter(r -> r.getPayloadId().equals(id)).findFirst();
    }

    public List<Payload> retrievePayloads() {
        return payloads;
    }

}
