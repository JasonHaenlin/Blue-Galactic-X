package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionStatusRequest.MissionStatus;

@Service
public class MissionProducer {

    @Autowired
    private KafkaTemplate<String, MissionStatusRequest> kafkaTemplate;

    public void startMission(String missionId) {
        MissionStatusRequest req = MissionStatusRequest.newBuilder().setMissionId("1").setStatus(MissionStatus.STARTED)
                .build();

        kafkaTemplate.send("mission", req);
    }

    public void failedMission(String missionId) {
        MissionStatusRequest req = MissionStatusRequest.newBuilder().setMissionId("1").setStatus(MissionStatus.FAILED)
                .build();

        kafkaTemplate.send("mission", req);
    }
}
