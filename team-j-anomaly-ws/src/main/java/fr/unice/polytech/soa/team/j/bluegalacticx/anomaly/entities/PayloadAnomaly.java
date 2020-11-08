package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.ModuleType;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;

public class PayloadAnomaly extends AnomalyRules<TelemetryPayloadRequest> {

    public PayloadAnomaly() {
        super(ModuleType.PAYLOAD);
    }

    @Override
    public List<Rule<TelemetryPayloadRequest>> publishRules() {
        List<Rule<TelemetryPayloadRequest>> rules = new ArrayList<>();
        // No rules for the moment
        return rules;
    }

}
