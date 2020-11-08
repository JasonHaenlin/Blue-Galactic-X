package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;

@Service
public class OrbitalPayloadService {

    private final List<Payload> payloads = new ArrayList<>();

    @Autowired
    PayloadStatusRequest payloadStatusRequest;

    public void activateTelemetryRecorder(String id) throws PayloadNotFoundException {
    }

}
