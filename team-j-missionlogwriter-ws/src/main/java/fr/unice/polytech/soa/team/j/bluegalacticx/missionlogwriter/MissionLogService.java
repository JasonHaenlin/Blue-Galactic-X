package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.db.MissionLogRepository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.MissionLog;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.exceptions.MissionNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.proto.PayloadStatusRequest;

@Service
public class MissionLogService {

    @Autowired
    private MissionLogRepository missionLogRepository;

    public void createMissionLogData(PayloadStatusRequest payloadStatusData, String log)
            throws MissionNotFoundException {
        String missionId = payloadStatusData.getMissionId();
        if (missionId == null) {
            throw new MissionNotFoundException();
        }
        MissionLog missionLogsData = missionLogRepository.findById(missionId).orElse(new MissionLog(missionId));
        missionLogsData.addLog(log);
        missionLogRepository.save(missionLogsData);
    }

}
