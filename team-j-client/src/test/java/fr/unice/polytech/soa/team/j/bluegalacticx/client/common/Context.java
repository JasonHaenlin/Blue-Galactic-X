package fr.unice.polytech.soa.team.j.bluegalacticx.client.common;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.KafkaProducerClient;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.BoosterREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.PayloadREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.TelemetryReaderREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;

public class Context {

    public WeatherREST weatherREST = new WeatherREST("http://localhost:8060/weather");
    public MissionREST missionREST = new MissionREST("http://localhost:8070/missionPreparation");
    public RocketREST rocketREST = new RocketREST("http://localhost:8080/rocket");
    public PayloadREST payloadREST = new PayloadREST("http://localhost:8050/payload");
    public BoosterREST boosterREST = new BoosterREST("http://localhost:8030/booster");
    public TelemetryReaderREST telemetryReaderREST = new TelemetryReaderREST("http://localhost:8094/telemetry");

    public RocketRPC rocketRPC = new RocketRPC("localhost", 8081);

    public Mission mission;
    public Payload payload;
    public Rocket rocket;

    public KafkaProducerClient<TelemetryBoosterRequest> kPBoosterTelemetry;
    public KafkaProducerClient<TelemetryRocketRequest> kPRocketTelemetry;
    public KafkaProducerClient<TelemetryPayloadRequest> kPPayloadTelemetry;

    public TelemetryBoosterRequest reqBooster;
    public TelemetryRocketRequest reqRocket;
    public TelemetryPayloadRequest reqPayload;
}
