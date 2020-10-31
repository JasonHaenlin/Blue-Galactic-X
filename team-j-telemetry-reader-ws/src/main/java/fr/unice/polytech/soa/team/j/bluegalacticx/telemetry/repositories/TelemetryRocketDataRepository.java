package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

@Repository
public interface TelemetryRocketDataRepository extends MongoRepository<TelemetryRocketData, String> {

    public List<TelemetryRocketData> findAllByRocketId(String rocketId);

}
