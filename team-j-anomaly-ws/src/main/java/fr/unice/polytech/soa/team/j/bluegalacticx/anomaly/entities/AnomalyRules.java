package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.AnomalyType;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.ModuleType;

public abstract class AnomalyRules<T> {

    private List<Rule<T>> rules = new ArrayList<>();
    private final ModuleType moduleType;

    public AnomalyRules(ModuleType moduleType) {
        this.moduleType = moduleType;
        this.rules = this.publishRules();
    }

    public abstract List<Rule<T>> publishRules();

    public final List<AnomalyRequest> healthcheck(String id, T t) {
        List<AnomalyRequest> anomalies = new ArrayList<>();
        for (Rule<T> r : rules) {
            AnomalyType type = r.verify(t);
            if (type != null) {
                anomalies.add(AnomalyRequest.newBuilder().setModuleId(id).setModuleType(moduleType).setAnomalyType(type)
                        .build());
            }
        }
        return anomalies;
    }
}
