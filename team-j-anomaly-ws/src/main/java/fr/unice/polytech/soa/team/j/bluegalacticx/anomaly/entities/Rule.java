package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest.AnomalyType;

public interface Rule<T> {
    AnomalyType verify(T data);
}
