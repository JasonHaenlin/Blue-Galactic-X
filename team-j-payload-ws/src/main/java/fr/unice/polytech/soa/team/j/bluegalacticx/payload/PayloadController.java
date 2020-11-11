package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.InvalidPayloadException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatus;

@RestController
@RequestMapping("/payload")
public class PayloadController {

    @Autowired
    private PayloadService payloadService;

    @PostMapping("")
    public Payload createNewPayload(@RequestBody Payload payload) {
        try {
            return payloadService.createPayload(payload);
        } catch (InvalidPayloadException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Payload getPayload(@PathVariable String id) {
        try {
            return payloadService.retrievePayload(id);
        } catch (PayloadNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
