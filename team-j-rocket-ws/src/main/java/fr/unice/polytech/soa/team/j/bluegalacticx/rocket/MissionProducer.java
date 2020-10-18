package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionStatusRequest.MissionStatus;

// @Service
@RestController
public class MissionProducer {

    @Autowired
    private KafkaTemplate<String, MissionStatusRequest> kafkaTemplate;

    @GetMapping("/start")
    public void startMission(String missionId) {
        MissionStatusRequest req = MissionStatusRequest.newBuilder().setMissionId("1").setStatus(MissionStatus.STARTED)
                .build();

        kafkaTemplate.send("mission", req);
    }

    @GetMapping("/failed")
    public void failedMission(String missionId) {
        MissionStatusRequest req = MissionStatusRequest.newBuilder().setMissionId("1").setStatus(MissionStatus.FAILED)
                .build();

        kafkaTemplate.send("mission", req);
    }
}
