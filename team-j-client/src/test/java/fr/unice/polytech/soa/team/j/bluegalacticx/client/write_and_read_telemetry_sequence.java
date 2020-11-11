package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;
import io.cucumber.java8.En;

public class write_and_read_telemetry_sequence implements En {

    Context ctx = new Context();

    public write_and_read_telemetry_sequence() {

        Given("a producer to send event from booster {string}", (String topic) -> {
            ctx.kPBoosterTelemetry = new KafkaProducerClient<>(topic);
        });
        Given("a producer to send event from payload {string}", (String topic) -> {
            ctx.kPPayloadTelemetry = new KafkaProducerClient<>(topic);
        });
        Given("a producer to send event from rocket {string}", (String topic) -> {
            ctx.kPRocketTelemetry = new KafkaProducerClient<>(topic);
        });
        When("the booster topic prepare a telemetry with id {string}", (String id) -> {
            ctx.reqBooster = TelemetryBoosterRequest.newBuilder().setBoosterId(id).setBoosterStatus("FLYING")
                    .setDistanceFromEarth(510).setRocketId("10").setSpeed(5000).build();
        });
        Then("I should be able to write it to booster topic", () -> {
            ctx.kPBoosterTelemetry.emit(ctx.reqBooster);
        });
        Then("read the booster data and check id {string} with {int} measure", (String id, Integer count) -> {
            Utils.assertEqualsWithRetry(id,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryBoosterData(id).getBoosterId());
            Utils.assertEqualsWithRetry(count,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryBoosterData(id).getTransactionCount());
        });
        When("the rocket topic prepare a telemetry with id {string}", (String id) -> {
            ctx.reqRocket = TelemetryRocketRequest.newBuilder().setRocketId(id).setSpeed(1000).setTemperature(50)
                    .setTotalDistance(100).build();
        });
        Then("I should be able to write it {int} times to rocket topic", (Integer count) -> {
            Utils.repeat(count, () -> ctx.kPRocketTelemetry.emit(ctx.reqRocket));
        });
        Then("read the rocket data and check id {string} with {int} measures", (String id, Integer count) -> {
            Utils.assertEqualsWithRetry(id,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryRocketData(id).getRocketId());
            Utils.assertEqualsWithRetry(count,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryRocketData(id).getTransactionCount());
        });
        When("the payload topic prepare a telemetry with id {string}", (String id) -> {
            ctx.reqPayload = TelemetryPayloadRequest.newBuilder().setPayloadId(id).setPayloadStatus("READY")
                    .setWeight(1000).build();
        });
        Then("I should be able to write it {int} times to payload topic", (Integer count) -> {
            Utils.repeat(count, () -> ctx.kPPayloadTelemetry.emit(ctx.reqPayload));
        });
        Then("I should be able to write it to payload topic", () -> {
            ctx.kPPayloadTelemetry.emit(ctx.reqPayload);
        });
        Then("read the payload data from id {string} with {int} measure", (String id, Integer count) -> {
            Utils.assertEqualsWithRetry(id,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryPayloadData(id).getPayloadId());
            Utils.assertEqualsWithRetry(count,
                    () -> ctx.telemetryReaderREST.retrieveTelemetryPayloadData(id).getTransactionCount());
        });

    }

}
