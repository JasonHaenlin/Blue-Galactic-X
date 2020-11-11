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
    public void newMissionEvent(MissionPreparationRequest missionRequest) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Mission mission = new Mission().id(missionRequest.getId()).rocketId(missionRequest.getRocketId())
                .boosterIds(new String[] { missionRequest.getBoosterId1(), missionRequest.getBoosterId2()})
                .payloadId(missionRequest.getPayloadId()).date(formatter.parse(missionRequest.getDate()))
                .destination(new SpaceCoordinate(missionRequest.getDestination().getX(),
                        missionRequest.getDestination().getY(), missionRequest.getDestination().getZ()));
        mission.setStatus(MissionStatus.PENDING);
        missionControlService.storeMission(mission);
    }

}
