package fr.unice.polytech.soa.team.j.bluegalacticx.client.common;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.KafkaProducerClient;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.BoosterREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.MissionControlREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.MissionLogREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.MissionPreparationREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.PayloadREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.TelemetryReaderREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.MissionLog;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

public class Context {

    public WeatherREST weatherREST = new WeatherREST("8060");
    public MissionPreparationREST missionPrepREST = new MissionPreparationREST("8070");
    public MissionControlREST missionCtrlREST = new MissionControlREST("8200");
    public RocketREST rocketREST = new RocketREST("8080");
    public PayloadREST payloadREST = new PayloadREST("8050");
    public BoosterREST boosterREST = new BoosterREST("8030");
    public TelemetryReaderREST telemetryReaderREST = new TelemetryReaderREST("8094");
    public MissionLogREST missionLogREST = new MissionLogREST("8110");

    public RocketRPC rocketRPC = new RocketRPC("localhost", 8081);

    public Mission mission;
    public Payload payload;
    public Rocket rocket;
    public List<String> boostersId = new ArrayList<>();

    public KafkaProducerClient<TelemetryBoosterRequest> kPBoosterTelemetry;
    public KafkaProducerClient<TelemetryRocketRequest> kPRocketTelemetry;
    public KafkaProducerClient<TelemetryPayloadRequest> kPPayloadTelemetry;

    public TelemetryBoosterRequest reqBooster;
    public TelemetryRocketRequest reqRocket;
    public TelemetryPayloadRequest reqPayload;

    public MissionLog missionLog;

    public String[] arrayBoostersId() {
        return boostersId.stream().toArray(String[]::new);
    }
}
