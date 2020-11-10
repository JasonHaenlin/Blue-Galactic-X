package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
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
                case ON_MISSION:
                    missionControlService.storePayloadStatus(id, PayloadStatus.ON_MISSION);
                    missionControlService.updateMissionFromPayloadState(id);
                    break;
                case DESTROYED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.DESTROYED);
                    missionControlService.updateMissionFromPayloadState(id);
                    break;
                case DELIVERED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.DELIVERED);
                    missionControlService.updateMissionFromPayloadState(id);
                    break;
                case NOT_DELIVERED:
                    missionControlService.storePayloadStatus(id, PayloadStatus.NOT_DELIVERED);
                    missionControlService.updateMissionFromPayloadState(id);
                    break;
                default:
                    // DO NOT PROCEED NOT WANTED EVENTS
                    break;
            }

        } catch (PayloadDoesNotExistException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }

    }

}
