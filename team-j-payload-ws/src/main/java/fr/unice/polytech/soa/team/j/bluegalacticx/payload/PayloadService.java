package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.InvalidPayloadException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.mock.PayloadMock;

@Service
public class PayloadService {

    Payload createPayload(Payload payload) throws InvalidPayloadException {

        if (checkInput(payload)){
            throw new InvalidPayloadException();
        }

        payload.setStatus(PayloadStatus.WAITING_FOR_MISSION);
        payload.setId(UUID.randomUUID().toString());
        payload.setDate(new Date());

        PayloadMock.payloads.add(payload);

        return payload;
    }

    Payload retrievePayload(String id) throws PayloadNotFoundException {
        return PayloadMock.payloads.stream().filter(p -> p.getId().equals(id)).findFirst()
            .orElseThrow(() -> new PayloadNotFoundException());
    }

    private boolean checkInput(Payload input) {
        return input == null; 
    }
    
}
