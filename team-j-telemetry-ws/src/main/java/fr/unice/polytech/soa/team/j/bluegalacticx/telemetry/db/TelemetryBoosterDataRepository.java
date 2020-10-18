package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;

@Repository
public interface TelemetryBoosterDataRepository extends MongoRepository<TelemetryBoosterData, String> {

    public List<TelemetryBoosterData> findByBoosterId(String boosterId);
}