package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.PayloadStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.PayloadStatusRequest.EventType;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.MissionLogService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.db.MissionLogRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.MissionLog;

@Service
public class PayloadStatusConsumer {

    @Autowired
    private MissionLogRepository missionLogRepository;

    @Autowired
    private MissionLogService missionLogService;

    @KafkaListener(topics = "${kafka.topics.payloadstatus}", groupId = "${kafka.group.default}", containerFactory = "payloadStatusKafkaListenerContainerFactory")
    public void payloadStatusEvent(PayloadStatusRequest request) {
        String payloadId = request.getPayloadId();
        String rocketId = request.getRocketId();
        EventType status = request.getEventType();
        if(status == EventType.DELIVERED){
            String log = "Payload of ID " + payloadId + ", attache to rocket of ID " + rocketId + " have been successfully delivered";
            // TODO Store using MissionLog aggregate once the payload is storing mission ID
            //missionLogService.createMissionLogData(request, log);
            missionLogRepository.save(new Log(log));
        }
    }
}
