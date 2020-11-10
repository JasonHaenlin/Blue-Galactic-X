package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.db.MissionLogRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.exceptions.MissionNotFoundException;

@Service
public class MissionLogReaderService {
    
    @Autowired
    private MissionLogRepository missionLogRepository;

    List<Log> getLogsForMission(String missionId) throws MissionNotFoundException{
        MissionLog missionLog = missionLogRepository.findById(missionId).orElse(null);
        if(missionLog == null){
            throw new MissionNotFoundException();
        }
        return missionLog.getLogs();
    }

}
