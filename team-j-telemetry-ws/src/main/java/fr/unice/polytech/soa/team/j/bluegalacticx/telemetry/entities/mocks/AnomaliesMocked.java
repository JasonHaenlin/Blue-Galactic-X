package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.mocks;

import java.util.HashSet;
import java.util.Set;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.AnomalySeverity;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.AnomalyType;

public class AnomaliesMocked {
    static public final Set<Anomaly> anomalies;

    static {
        // @formatter:off
        anomalies = new HashSet<>();
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
