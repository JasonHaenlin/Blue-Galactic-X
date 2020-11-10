package fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.proto.MissionPreparationRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.proto.MissionPreparationRequest.Destination;

@Service
public class MissionPreparationProducer {

    @Value(value = "${kafka.topics.missionpreparation}")
    private String missionPreparationTopic0;

    @Autowired
    private KafkaTemplate<String, MissionPreparationRequest> kafkaTemplate;

    public void notifyNewMission(Mission mission) {
        MissionPreparationRequest req = MissionPreparationRequest.newBuilder().setId(mission.getId())
                .setRocketId(mission.getRocketId()).setBoosterId1(mission.getBoosterIds()[0])
                .setBoosterId2(mission.getBoosterIds()[1]).setPayloadId(mission.getPayloadId())
                .setDate(mission.getDate().toString())
                .setDestination(Destination.newBuilder().setX(mission.getDestination().getX())
                        .setY(mission.getDestination().getY()).setZ(mission.getDestination().getZ()).build()).build();
        kafkaTemplate.send(missionPreparationTopic0, req);
    }

}
