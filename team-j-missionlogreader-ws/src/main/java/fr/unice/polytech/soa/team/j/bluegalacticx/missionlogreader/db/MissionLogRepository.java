package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;

@Repository
public interface MissionLogRepository extends MongoRepository<MissionLog, String> {}
