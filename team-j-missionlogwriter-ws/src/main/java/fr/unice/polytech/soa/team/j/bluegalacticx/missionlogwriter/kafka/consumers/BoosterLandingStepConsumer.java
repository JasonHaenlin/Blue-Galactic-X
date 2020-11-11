package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.MissionLogService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.exceptions.MissionNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.BoosterLandingStepRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.BoosterLandingStepRequest.EventType;

@Service
public class BoosterLandingStepConsumer {

    @Autowired
    private MissionLogService missionLogService;

    @KafkaListener(topics = "${kafka.topics.boosterlandingstep}", groupId = "${kafka.group.default}", containerFactory = "boosterLandingStepKafkaListenerContainerFactory")
    public void boosterLandingStepEvent(BoosterLandingStepRequest request) {
        String boosterId = request.getBoosterId();
        String missionId = request.getMissionId();
        EventType status = request.getEventType();
        try {
            String log;
            if(missionId != null && missionId != ""){
                log = "Booster [ID = " + boosterId + "]. On mission [ID = " + missionId
                    + "]. Landing sequence is now : " + status.name();
                missionLogService.createMissionLogData(missionId, log);
            } else {
                log = "Booster [ID = " + boosterId + "]. Landing sequence is now : " + status.name();
                missionLogService.createMissionLogData(boosterId, log);
            }
        } catch (MissionNotFoundException e) {
            // TODO : handle kafka exceptions
            e.printStackTrace();
        }
    }
}
