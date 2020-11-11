package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.InvalidPayloadException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers.PayloadCreateProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers.PayloadStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;

@Service
public class PayloadService {

    private final List<Payload> payloads = new ArrayList<>();

    @Autowired
    private PayloadStatusProducer payloadStatusProducer;

    @Autowired
    private PayloadCreateProducer payloadProducer;

    Payload createPayload(Payload payload) throws InvalidPayloadException {
        if (payload == null) {
            throw new InvalidPayloadException();
        }
        payload.setStatus(PayloadStatus.WAITING_FOR_MISSION);
        payload.setDate(new Date());
        payload.setId(UUID.randomUUID().toString());
        payloads.add(payload);
        payloadProducer.notifyNewPayload(payload);
        return payload;
    }

    public void updatePayloadFromRocketState(PayloadStatus status, String id) throws PayloadNotFoundException {
        Payload p = retrievePayloadByRocketId(id);
        p.setStatus(status);
    }

    public Payload retrievePayloadFromRocket(String id) throws PayloadNotFoundException {
        return retrievePayloadByRocketId(id);
    }

    public Payload retrievePayload(String id) throws PayloadNotFoundException {
        return find(id).orElseThrow(() -> new PayloadNotFoundException());
    }

    public Payload retrievePayloadByRocketId(String id) throws PayloadNotFoundException {
        return findByRocketId(id).orElseThrow(() -> new PayloadNotFoundException());
    }

    private Optional<Payload> find(String id) {
        return payloads.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    private Optional<Payload> findByRocketId(String id) {
        return payloads.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

}
