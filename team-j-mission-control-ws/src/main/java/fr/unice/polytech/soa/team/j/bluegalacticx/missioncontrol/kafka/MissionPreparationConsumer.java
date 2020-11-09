package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.MissionControlService;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.proto.MissionPreparationRequest;

@Service
public class MissionPreparationConsumer {

    @Autowired
    private MissionControlService missionControlService;

    @KafkaListener(topics = "${kafka.topics.missionpreparation}", groupId = "${kafka.group.default}", containerFactory = "missionPreparationKafkaListenerContainerFactory")
    public void missionPreparationEvent(MissionPreparationRequest missionRequest) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
        Mission mission = new Mission().id(missionRequest.getId()).rocketId(missionRequest.getRocketId())
                .boosterId(missionRequest.getBoosterId()).payloadId(missionRequest.getPayloadId())
                .date(formatter.parse(missionRequest.getDate()))
                .destination(new SpaceCoordinate(missionRequest.getDestination().getX(),
                        missionRequest.getDestination().getY(), missionRequest.getDestination().getZ()));
        switch (missionRequest.getEventType()) {
            case PENDING:
                mission.setStatus(MissionStatus.PENDING);
                break;
            case READY:
                mission.setStatus(MissionStatus.READY);
                break;
            default:
                break;
        }
        missionControlService.storeMission(mission);
    }

}
