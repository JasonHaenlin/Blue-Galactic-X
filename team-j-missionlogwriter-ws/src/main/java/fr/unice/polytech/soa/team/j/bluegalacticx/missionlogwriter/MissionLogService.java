package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.db.MissionLogRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.MissionLog;

@Service
public class MissionLogService {

    @Autowired
    private MissionLogRepository missionLogRepository;
    
    // TODO use that code to store mission logs by mission ID
    /*public void createMissionLogData(PayloadStatusRequest payloadStatusData, String log) {
        String missionId = payloadStatusData.getMissionId();
        if (missionId == null) {
            throw new MissionNotFoundException();
        }
        MissionLog missionLogsData = missionLogRepository.findByMissionId(missionId).orElse(new MissionLog(missionId));
        missionLogsData.addLog(log);
        missionLogRepository.save(missionLogsData);
    }*/

}
