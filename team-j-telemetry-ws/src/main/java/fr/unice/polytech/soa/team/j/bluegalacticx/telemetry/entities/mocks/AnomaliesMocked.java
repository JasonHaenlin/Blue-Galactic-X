package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.mocks;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.AnomalySeverity;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.AnomalyType;

public class AnomaliesMocked {
    static public final List<Anomaly> anomalies;

    static {
        // @formatter:off
        anomalies = new ArrayList<>();
        anomalies.add(new Anomaly()
                    .type(AnomalyType.TRAJECTORY)
                    .severity(AnomalySeverity.CRITICAL)
                    .reason("The rocket has exceeded an inclination of 90°"));
        anomalies.add(new Anomaly()
                    .type(AnomalyType.ENGINE)
                    .severity(AnomalySeverity.DANGER)
                    .reason("The 1st stage is not detached correctly"));
        // @formatter:on
    }
}
