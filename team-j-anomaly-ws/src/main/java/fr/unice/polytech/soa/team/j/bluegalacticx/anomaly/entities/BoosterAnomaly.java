package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.AnomalyType;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.ModuleType;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;

public class BoosterAnomaly extends AnomalyRules<TelemetryBoosterRequest> {

    public BoosterAnomaly() {
        super(ModuleType.BOOSTER);
    }

    @Override
    public List<Rule<TelemetryBoosterRequest>> publishRules() {
        List<Rule<TelemetryBoosterRequest>> rules = new ArrayList<>();
        rules.add((TelemetryBoosterRequest data) -> {
            if (data.getDistanceFromEarth() < 500 && data.getSpeed() > 500
                    && !data.getBoosterStatus().equals("RUNNING")) {
                return AnomalyType.OVERHEATING;
            }
            return null;
        });
        rules.add((TelemetryBoosterRequest data) -> {
            if (data.getDistanceFromEarth() < 500 && data.getFuel() < 30) {
                return AnomalyType.FAILED_TO_DETACH;
            }
            return null;
        });
        return rules;
    }

}
