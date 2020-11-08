package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.PayloadStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.PayloadStatusRequest.EventType;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.MissionLogService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.exceptions.MissionNotFoundException;

@Service
public class PayloadStatusConsumer {

    @Autowired
    private MissionLogService missionLogService;

    @KafkaListener(topics = "${kafka.topics.payloadstatus}", groupId = "${kafka.group.default}", containerFactory = "payloadStatusKafkaListenerContainerFactory")
    public void payloadStatusEvent(PayloadStatusRequest request) {
        String payloadId = request.getPayloadId();
        String rocketId = request.getRocketId();
        String missionId = request.getMissionId();
        EventType status = request.getEventType();
        try {
            if(status == EventType.DELIVERED){
                String log = "Payload of ID " + payloadId + ", attached to rocket of ID " + rocketId + ", for mission of ID " + missionId + " have been successfully delivered";
                missionLogService.createMissionLogData(request, log);
            }
        } catch (MissionNotFoundException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }
    }
}
