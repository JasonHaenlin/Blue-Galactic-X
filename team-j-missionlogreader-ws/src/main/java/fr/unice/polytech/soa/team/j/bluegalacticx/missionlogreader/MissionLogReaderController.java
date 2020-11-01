package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.db.MissionLogRepository;

@RestController
@RequestMapping("/missionlogreader")
public class MissionLogReaderController {

    @Autowired
    private MissionLogRepository missionLogRepository;

    @GetMapping()
    public List<MissionLog> getMissionLogs(){
        return missionLogRepository.findAll();
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
