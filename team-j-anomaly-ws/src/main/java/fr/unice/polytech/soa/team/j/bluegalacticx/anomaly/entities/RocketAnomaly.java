package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.AnomalyType;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.ModuleType;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

public class RocketAnomaly extends AnomalyRules<TelemetryRocketRequest> {

    public RocketAnomaly() {
        super(ModuleType.ROCKET);
    }

    @Override
    public List<Rule<TelemetryRocketRequest>> publishRules() {
        List<Rule<TelemetryRocketRequest>> rules = new ArrayList<>();
        rules.add((TelemetryRocketRequest data) -> {
            if (data.getTemperature() > 350) {
                return AnomalyType.OVERHEATING;
            }
            return null;
        });
        rules.add((TelemetryRocketRequest data) -> {
            if (data.getHeatShield() < 30) {
                return AnomalyType.FAILED_TO_ENTRY_BURN;
            }
            return null;
        });
        return rules;
    }

}
