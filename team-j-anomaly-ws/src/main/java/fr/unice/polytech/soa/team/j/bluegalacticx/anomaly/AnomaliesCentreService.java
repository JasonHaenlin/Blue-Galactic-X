package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities.BoosterAnomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities.PayloadAnomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.entities.RocketAnomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.kafka.producer.AnomalyProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

@Service
public class AnomaliesCentreService {

    @Autowired
    private AnomalyProducer ayProducer;

    private BoosterAnomaly boosterAy = new BoosterAnomaly();
    private RocketAnomaly rocketAy = new RocketAnomaly();
    private PayloadAnomaly payloadAy = new PayloadAnomaly();

    public void checkBoosterTelemetry(TelemetryBoosterRequest req) {
        ayProducer.alertAnomalies(boosterAy.healthcheck(req.getBoosterId(), req));
    }

    public void checkPayloadTelemetry(TelemetryPayloadRequest req) {
        ayProducer.alertAnomalies(payloadAy.healthcheck(req.getPayloadId(), req));
    }

    public void checkRocketTelemetry(TelemetryRocketRequest req) {
        ayProducer.alertAnomalies(rocketAy.healthcheck(req.getRocketId(), req));
    }

}
