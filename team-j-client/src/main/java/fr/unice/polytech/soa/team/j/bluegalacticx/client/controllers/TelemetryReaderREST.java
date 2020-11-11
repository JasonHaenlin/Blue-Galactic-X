package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryPayloadData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

public class TelemetryReaderREST extends RestAPI {

    public TelemetryReaderREST(String port) {
        super("localhost", port, "telemetry");
    }

    public TelemetryReaderREST(String host, String port) {
        super(host, port, "telemetry");
    }

    public TelemetryRocketData retrieveTelemetryRocketData(String rocketId) {
        return get("/rocket/" + rocketId, TelemetryRocketData.class);
    }

    public TelemetryBoosterData retrieveTelemetryBoosterData(String boosterId) {
        return get("/booster/" + boosterId, TelemetryBoosterData.class);
    }

    public TelemetryPayloadData retrieveTelemetryPayloadData(String payloadId) {
        return get("/payload/" + payloadId, TelemetryPayloadData.class);
    }

}
