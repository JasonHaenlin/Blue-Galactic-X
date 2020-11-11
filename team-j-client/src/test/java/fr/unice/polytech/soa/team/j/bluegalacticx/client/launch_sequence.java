package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Context;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.common.Utils;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionlogreader.entities.Log;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketLaunchStep;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.SpaceCoordinate;
import io.cucumber.java8.En;

public class launch_sequence implements En {

    AppLog log = AppLog.getInstance();
    Context ctx = new Context();

    public launch_sequence() {

        Before(() -> {
            ctx.rocketRPC.create();
        });

        After(() -> {
            ctx.rocketRPC.shutDown();
        });

        Given("Elon who added the boosters below", () -> {
            Booster booster1 = ctx.boosterREST.createBooster(new Booster());
            ctx.boostersId.add(booster1.getId());
            Booster booster2 = ctx.boosterREST.createBooster(new Booster());
            ctx.boostersId.add(booster2.getId());
        });

        Given("the rocket below", () -> {
            ctx.rocket = ctx.rocketREST
                    .createRocket(new Rocket().boosterId(ctx.boostersId.get(0)).boosterId2(ctx.boostersId.get(1)));
        });
        Given("Richard who program the following mission", () -> {
            ctx.payload = new Payload().rocketId(ctx.rocket.getId()).type(PayloadType.SPACECRAFT).weight(10000)
                    .position(new SpaceCoordinate(0, 0, 0));
            ctx.payload = ctx.payloadREST.createNewPayload(ctx.payload);
            ctx.mission = new Mission().rocketId(ctx.rocket.getId()).payloadId(ctx.payload.getId())
                    .date(Calendar.getInstance().getTime())
                    .destination(
                            new fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.entities.SpaceCoordinate(
                                    600, 600, 600))
                    .boosterIds(ctx.arrayBoostersId());
            ctx.mission = ctx.missionPrepREST.createNewMission(ctx.mission);
        });
        When("Richard set the rocket ready to be launched", () -> {
            ctx.rocketRPC.setReadyToLaunch(ctx.mission.getId(), ctx.rocket.getId());
        });
        Then("the rocket should be in preparation", () -> {
            Utils.assertEqualsWithRetry(RocketStatus.READY_FOR_LAUNCH, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
            Utils.assertEqualsWithRetry(RocketLaunchStep.PREPARATION, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            });
        });
        When("Elon start the launch procedure", () -> {
            ctx.rocketRPC.LaunchOrderRequest(true, ctx.rocket.getId());
        });
        Then("the rocket should be starting", () -> {
            Utils.assertEqualsWithRetry(RocketStatus.STARTING, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
            Utils.assertEqualsWithRetry(RocketLaunchStep.STARTUP, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            });
        });
        Then("after a short time, the main engine start", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAIN_ENGINE_START, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 5, 1000);
        });
        Then("the rocket liftoff after a few second", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.LIFTOFF, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 3, 1000);
            Utils.assertEqualsWithRetry(RocketStatus.IN_SERVICE, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            }, 3, 1000);
        });
        Then("the rocket arrive at Max Q", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.ENTER_MAXQ, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 4, 1000);
        });
        Then("the rocket quit Max Q", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAXQ_PASSED, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 2, 1000);
        });
        Then("the main engine cutoff", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.MAIN_ENGINE_CUTOFF, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
        });
        Then("we separate from the stage", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.STAGE_SEPARATION, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
        });
        And("the booster is now landing", () -> {
            Utils.assertEqualsWithRetry(BoosterStatus.LANDING, () -> {
                return ctx.boosterREST.lookForBooster(ctx.boostersId.get(0)).getStatus();
            });
        });
        Then("we wait for the rocket to arrive", () -> {
            Utils.assertEqualsWithRetry(RocketLaunchStep.FINISHED, () -> {
                return ctx.rocketREST.getLaunchStep(ctx.rocket.getId());
            }, 60, 1000);
            Utils.assertEqualsWithRetry(RocketStatus.ARRIVED, () -> {
                return ctx.rocketREST.getStatus(ctx.rocket.getId());
            });
        });
        When("we check the mission logs", () -> {
            ctx.missionLog = ctx.missionLogREST.retrieveMissionLogs(ctx.payload.getId());
        });
        Then("we are able to see the landing sequence in the logs", () -> {
            System.out.println("booster " + ctx.boostersId.get(0));
            System.out.println("rocket " + ctx.rocket.getId());
            System.out.println("payload " + ctx.payload.getId());
            System.out.println("mission " + ctx.mission.getId());
            System.out.println("Log received :");
            for (Log log : ctx.missionLog.getLogs()) {
                System.out.println(log.getText());
            }
        });
    }
}
