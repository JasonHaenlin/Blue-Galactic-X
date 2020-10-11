package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

@Repository
public interface TelemetryRocketDataRepository extends CrudRepository<TelemetryRocketData, String> {





}
