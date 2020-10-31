package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;

@Repository
public interface TelemetryPayloadDataRepository extends MongoRepository<TelemetryPayloadData, String> {

    public List<TelemetryPayloadData> findAllByPayloadId(String payloadId);

}
