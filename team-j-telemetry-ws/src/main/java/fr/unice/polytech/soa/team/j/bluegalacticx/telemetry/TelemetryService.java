package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.mocks.AnomaliesMocked;

@Service
public class TelemetryService {

    private List<Anomaly> anomalies = AnomaliesMocked.anomalies;

    /**
     * for now, the first call, we do not send back any anomalies but the second
     * time yes
     */
    private static boolean sendAnomalies = false;

    public List<Anomaly> checkRecordedAnomalies() {
        if (sendAnomalies == false) {
            sendAnomalies = true;
            return Collections.emptyList();
        }
        return anomalies;
    }

}
