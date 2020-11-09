package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.exceptions.PayloadDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;

@Service
public class PayloadStatusConsumer {

    @Autowired
    private MissionControlService missionControlService;

    @KafkaListener(topics = "${kafka.topics.payloadstatus}", groupId = "${kafka.group.default}", containerFactory = "payloadStatusKafkaListenerContainerFactory")
    public void payloadStatusEvent(PayloadStatusRequest request) {
        String id = request.getPayloadId();
        try {
            switch (request.getEventType()) {
                case WAITING_FOR_MISSION:
                    missionControlService.storePayloadStatus(id, PayloadStatus.WAITING_FOR_MISSION);
                    break;
                case ON_MISSION:
                    missionControlService.storePayloadStatus(id, PayloadStatus.ON_MISSION);
                    break;
                case DESTROYED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.DESTROYED);
                    break;
                case DELIVERED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.DELIVERED);
                    break;
                case NOT_DELIVERED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.NOT_DELIVERED);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }
            
            missionControlService.updateMissionFromPayloadState(id);
        } catch (PayloadDoesNotExistException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }

    }

}
