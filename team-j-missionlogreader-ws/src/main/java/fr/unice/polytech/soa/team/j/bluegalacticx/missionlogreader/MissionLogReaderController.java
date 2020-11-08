package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.exceptions.MissionNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.db.MissionLogRepository;

@RestController
@RequestMapping("/missionlogreader")
public class MissionLogReaderController {

    @Autowired
    private MissionLogRepository missionLogRepository;

    @Autowired
    private MissionLogReaderService missionLogReaderService;

    @GetMapping("")
    public List<MissionLog> getMissionLogs() {
        return missionLogRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public List<Log> getMissionLogById(@PathVariable String id) {
        try {
            return missionLogReaderService.getLogsForMission(id);
        } catch (MissionNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }

}
