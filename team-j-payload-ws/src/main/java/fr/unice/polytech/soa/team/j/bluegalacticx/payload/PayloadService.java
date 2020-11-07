package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.mock.PayloadMock;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.InvalidPayloadException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.kafka.producers.PayloadStatusProducer;

@Service
public class PayloadService {

    @Autowired
    private PayloadStatusProducer payloadStatusProducer;

    Payload createPayload(Payload payload) throws InvalidPayloadException {
        if (payload == null) {
            throw new InvalidPayloadException();
        }
        payload.setStatus(PayloadStatus.WAITING_FOR_MISSION);
        payload.setDate(new Date());

        PayloadMock.payloads.add(payload);

        return payload;
    }

    public void updatePayloadFromRocketState(PayloadStatus status, String id) throws PayloadNotFoundException {
        Payload p = retrievePayloadByRocketId(id);
        p.setStatus(status);
        // make the rollout (nothing right now)
        payloadStatusProducer.notifyDeployedPayloadEvent(p.getId(), id);
    }

    Payload retrievePayload(String id) throws PayloadNotFoundException {
        return PayloadMock.find(id).orElseThrow(() -> new PayloadNotFoundException());
    }

    Payload retrievePayloadByRocketId(String id) throws PayloadNotFoundException {
        return PayloadMock.findByRocketId(id).orElseThrow(() -> new PayloadNotFoundException());
    }

    Payload updateStatus(PayloadStatus status, String id) throws PayloadNotFoundException {
        for (Payload p : PayloadMock.payloads) {
            if (p.getId().equals(id)) {
                p.setStatus(status);
                if(status == PayloadStatus.DELIVERED){
                    payloadStatusProducer.notifyDeployedPayloadEvent(p.getId(), p.getRocketId());
                }
                return p;
            }
        }
        throw new PayloadNotFoundException();
    }

}
