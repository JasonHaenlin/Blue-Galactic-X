package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketLaunchStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import io.cucumber.java8.En;

public class demo_sequence implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public demo_sequence() {
        Given("Elon create 2 boosters in the booster micro-service", () -> {
            Utils.repeat(2, () -> {
                ctx.boostersId.add(ctx.boosterREST.createBooster(new Booster()).getId());
            });
        });
        Given("Gwynne create a new rocket in the rocket micro-service", () -> {
            Rocket r = new Rocket();
            r.setBoosterId(ctx.boostersId.get(0));
            r.setBoosterId2(ctx.boostersId.get(1));
            ctx.rocket = ctx.rocketREST.createRocket(r);
        });
        Given("Gwynne create a new payload in the payload micro-service", () -> {
            ctx.payload = new Payload().rocketId(ctx.rocket.getId()).type(PayloadType.SPACECRAFT).weight(10000)
                    .position(new SpaceCoordinate(0, 0, 0));
            ctx.payload = ctx.payloadREST.createNewPayload(ctx.payload);
        });
        Given("Richard add a new mission in the mission preparation micro-service", () -> {
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).payloadId(ctx.payload.getId())
                    .date(Calendar.getInstance().getTime())
                    .destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate(
                                    600, 600, 600))
                    .boosterIds(ctx.arrayBoostersId());
            ctx.mission = ctx.missionPrepREST.createNewMission(ctx.mission);
            log.trace("Associate the mission with the rocket, payload and the 2 created boosters").endSection();
        });
        When("Richard has the weather and rocket department not ready for launch", () -> {
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            // Only mission is set to false at initialization
            assertEquals(1, status.size());
            log.info(status.toString());
            log.trace("Only the status of the mission is set wiht the default value").endSection();
        });
        Then("Richard make a no go for the mission department", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(false));
            Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId()).getDepartments();
            assertFalse(status.get(Department.MISSION));
        });
        When("Tory from weather department see the weather is good", () -> {
            WeatherReport wr = ctx.weatherREST.getCurrentWeather();
            assertNotNull(wr);
            log.trace(wr.toString());
        });
        Then("Tory set the weather department to go to notify it is ready", () -> {
            ctx.weatherREST.setGoNoGo(new fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg(true));
        });
        When("Elon see the rocket is ready based on the Telemetry", () -> {
            SpaceTelemetry rm = ctx.rocketREST.getRocketTelemetry(ctx.rocket.getId());
            assertNotNull(rm);
            log.trace(rm.toString());
        });
        Then("Elon set the rocket department to go to notify it is ready", () -> {
            ctx.rocketREST.setGoNoGo(ctx.rocket.getId(), new GoNg(true));
        });
        When("Richard has the weather and rocket department ready for mission launch", () -> {
            Utils.assertEqualsWithRetry(2, () -> {
                try {
                    Map<Department, Boolean> status = ctx.missionPrepREST.getMissionGoNg(ctx.mission.getId())
                            .getDepartments();
                    int goCounter = 0;
                    if (status.get(Department.ROCKET)) {
                        goCounter++;
                    }
                    if (status.get(Department.WEATHER)) {
                        goCounter++;
                    }
                    if (goCounter == 2) {
                        log.trace(status.toString());
                    }
                    return goCounter;
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                return 0;
            });
            log.endSection();
        });
        Then("Richard make a go for the mission department in the mission preparation micro-service", () -> {
            ctx.missionPrepREST.updateMissionGoNg(ctx.mission.getId(),
                    new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.GoNg(true));
        });
        Then("Richard set the rocket ready to be launch", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.mission.getId(), ctx.rocket.getId());
        });
        Then("Elon should see the rocket department is {string}", (String status) -> {
            Utils.assertEqualsWithRetry(status, () -> {
                return ctx.rocketREST.getGoNoGo(ctx.rocket.getId()).toString();
            });
        });
        Then("Also the Rocket is {string}", (String status) -> {
            Utils.assertEqualsWithRetry(status, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId()).toString();
            });
        });
        Then("The Rocket Step should be {string}", (String status) -> {
            Utils.assertEqualsWithRetry(status, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId()).toString();
            });
        });
        When("Elon make the request for the launch order to begin the countdown from the rocket micro-service", () -> {
            Utils.assertSuccessWithRetry(() -> ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocket.getId()), 10, 500);
        });
        Then("The rocket status should be starting in the rocket micro-service", () -> {
            Utils.assertEqualsWithRetry(RocketStatus.STARTING, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
            Utils.assertEqualsWithRetry(RocketLaunchStep.STARTUP, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            });
        });
        Then("The payload status should be {string} in the payload micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ctx.payloadREST.retrievePayload(ctx.payload.getId()).getStatus().toString());
        });
        Then("The booster status should be {string} in the booster micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ctx.boosterREST.lookForBooster(ctx.boostersId.get(0)).getStatus().toString());
        });
        Then("The mission status should be {string} in the mission control micro-service", (String status) -> {
            Utils.assertEqualsWithRetry(status,
                    () -> ((fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities.Mission) ctx.missionCtrlREST
                            .retrieveMission(ctx.mission.getId())).getStatus().toString(),
                    20, 500);
        });
        When("The rocket sequence is starting", () -> {
            Utils.assertEqualsWithRetry(RocketStatus.STARTING, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
            Utils.assertEqualsWithRetry(RocketLaunchStep.STARTUP, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            });
        });
        Then("After a short time, the main engine start", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAIN_ENGINE_START, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 5, 1000);
        });
        Then("The rocket liftoff after a few second", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.LIFTOFF, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 5, 1000);
            Utils.assertEqualsWithRetry(RocketStatus.IN_SERVICE, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            }, 5, 1000);
        });
        Then("The rocket arrive at Max Q", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.ENTER_MAXQ, () -> {
                log.trace(logTelemetryRocket(ctx.telemetryReaderREST.retrieveTelemetryRocketData(ctx.rocket.getId())));
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 20, 1000);
        });
        Then("The rocket quit Max Q", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAXQ_PASSED, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 20, 1000);
        });
        Then("The main engine cutoff", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAIN_ENGINE_CUTOFF, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
        });
        Then("The rocket separate from the first stage", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.STAGE_SEPARATION, () -> {
                log.trace(logTelemetryRocket(ctx.telemetryReaderREST.retrieveTelemetryRocketData(ctx.rocket.getId())));
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
        });
        Then("The booster is now landing", () -> {
            Utils.assertEqualsWithRetry(BoosterStatus.LANDING, () -> {
                log.trace(logTelemetryBooster(
                        ctx.telemetryReaderREST.retrieveTelemetryBoosterData(ctx.rocket.getBoosterId())));
                return ctx.boosterREST.lookForBooster(ctx.boostersId.get(0)).getStatus();
            });
        });
        Then("The Rocket should arrive soon at the objective location", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.FINISHED, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
            Utils.assertEqualsWithRetry(RocketStatus.ARRIVED, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
        });
        When("Richard check the booster logs to see the booster landing process", () -> {
            Utils.assertEqualsWithRetry(5, () -> {
                return ctx.missionLogREST.retrieveMissionLogs(ctx.rocket.getBoosterId()).getLogs().size();
            }, 200, 1000);
            ctx.missionLog = ctx.missionLogREST.retrieveMissionLogs(ctx.rocket.getBoosterId());
        });
        Then("Richard is able to see the landing sequence in the logs", () -> {
            log.trace("missionId: " + ctx.missionLog.getMissionId());
            for (Log l : ctx.missionLog.getLogs()) {
                log.trace(l.getDate().toString() + " : " + l.getText());
            }
            log.endSection();
        });
        Then("The payload should start to emit telemetry from orbite", () -> {
            log.trace(ctx.telemetryReaderREST.retrieveTelemetryPayloadData(ctx.payload.getId()).toString());
        });

        Before(() -> {
            ctx.rocketRPC.create();
        });
        After(() -> {
            ctx.rocketRPC.shutDown();
        });
    }

    private String logTelemetryBooster(TelemetryBoosterData t) {
        return "{" + "id='" + t.getId() + "'" + " transaction='" + t.getTransactionCount() + "'" + ", lastMeasure='"
                + t.getMeasurements().get(t.getMeasurements().size() - 1) + "'" + "}";
    }

    private String logTelemetryRocket(TelemetryRocketData t) {
        return "{" + "id='" + t.getId() + "'" + " transaction='" + t.getTransactionCount() + "'" + ", lastMeasure='"
                + t.getMeasurements().get(t.getMeasurements().size() - 1) + "'" + "}";
    }
}
