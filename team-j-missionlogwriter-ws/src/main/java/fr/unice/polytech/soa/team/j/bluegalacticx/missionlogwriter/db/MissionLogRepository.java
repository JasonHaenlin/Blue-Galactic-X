package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities.Log;

@Repository
public interface MissionLogRepository extends MongoRepository<Log, String> {}
