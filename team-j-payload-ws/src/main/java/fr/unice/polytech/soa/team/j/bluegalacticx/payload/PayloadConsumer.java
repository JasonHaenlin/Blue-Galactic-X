package fr.unice.polytech.soa.team.j.bluegalacticx.payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.exceptions.PayloadNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest.PayloadStatus;

@Service
public class PayloadConsumer {
    @Autowired
    private PayloadService payloadService;

    @KafkaListener(topics = "payload", groupId = "blue-origin-payload", containerFactory = "payloadStatuskafkaListenerContainerFactory")
    public void mission(PayloadStatusRequest request) {
        String id = request.getPayloadId();
        PayloadStatus status = request.getStatus();

        try {
            payloadService.updateStatus(status, id);
        } catch (PayloadNotFoundException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }

    }
}
