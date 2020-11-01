package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;

@Repository
public interface TelemetryBoosterDataRepository extends MongoRepository<TelemetryBoosterData, String> {
    public Optional<TelemetryBoosterData> findByBoosterId(String boosterId);
}
