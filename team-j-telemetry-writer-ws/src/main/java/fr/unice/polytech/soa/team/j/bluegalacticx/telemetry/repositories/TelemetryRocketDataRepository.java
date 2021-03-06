package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

@Repository
public interface TelemetryRocketDataRepository extends MongoRepository<TelemetryRocketData, String> {
    public Optional<TelemetryRocketData> findByRocketId(String rocketId);
}
