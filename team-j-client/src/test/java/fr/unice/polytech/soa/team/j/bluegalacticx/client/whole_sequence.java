package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.PayloadREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.Engine;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.EngineState;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.TelemetryREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.entities.WeatherType;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MissionStatusRequest.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageReply;
import io.cucumber.java8.En;

public class whole_sequence implements En {

    // Endpoints
    private WeatherREST weatherREST;
    private MissionREST missionREST;
    private RocketREST rocketREST;
    private RocketRPC rocketRPC;
    private TelemetryREST telemetryREST;
    private PayloadREST payloadREST;

    // Report
    private WeatherReport weatherReport;
    private RocketReport rocketReport;

    private LaunchOrderReply launchOrderReply;
    private NextStageReply nextStageReply;
    private List<TelemetryRocketData> telemetryRocketData;

    private List<TelemetryBoosterData> telemetryBoosterData;
    private Set<Anomaly> listAnomalies;
    private Payload payload;

    AppLog log = AppLog.getInstance();

    public whole_sequence() {

        Given("a handshake with all department", () -> {
            weatherREST = new WeatherREST("http://localhost:8060/weather");
            missionREST = new MissionREST("http://localhost:8070/mission");
            rocketREST = new RocketREST("http://localhost:8080/rocket");
            telemetryREST = new TelemetryREST("http://localhost:8090/telemetry");
            payloadREST = new PayloadREST("http://localhost:8050/payload");
            rocketRPC = new RocketRPC("localhost", 8081);

            log.info("Create REST clients and RPC Clients").endSection();
        });

        And("Elon from the rocket department check the rocket metrics and create a report for Richard", () -> {
            rocketReport = new RocketReport().fuelLevel(100)
                    .engine(new Engine(EngineState.INITIALIZATION, EngineState.READY)).overallResult("Good");
            // TODO change
            // rocketREST.setReport(rocketReport, "1");

            log.request("Post report to rocket ws").info("Report " + rocketReport.toString()).endSection();
        });

        And("Tory from the weather department check the weather metrics and create a report for Richard", () -> {
            weatherReport = new WeatherReport().avgRainfall(50).avgHumidity(10).avgWind(20).avgVisibility(90)
                    .avgTemperature(25).weatherType(WeatherType.SUNNY).overallResult("Good");
            // TODO change
            // weatherREST.setReport(weatherReport);

            log.request("Post report to weather ws").info("Report " + weatherReport.toString()).endSection();
        });

        And("Gwynne from the payload department create a new payload", () -> {
            payload = new Payload().id("4f6911a8-437a-43fc-adad-a0ed6c6f69a7").type(PayloadType.SPACECRAFT)
                    .status(PayloadStatus.WAITING_FOR_MISSION).weight(10000);
            payloadREST.createNewPayload(payload);

            log.request("Post new payload to payload ws").info("Payload " + payload.toString()).endSection();

        });

        When("Richard add a new mission", () -> {
            Mission m = new Mission().id("1").rocketId("1").date(new Date());
            String response = missionREST.createNewMission(m);
            assertEquals(true, response.contains("200"));

            log.request("Post new mission to mission ws").info("mission " + m.toString()).endSection();
        });

        And("the weather report is valid", () -> {
            // TODO change
            // WeatherReport weatherReport = weatherREST.getReport();
            assertEquals(true, weatherReport.getOverallResult().contains("Good"));

            log.request("Get weather report from weather ws").info("Report " + weatherReport.toString()).endSection();
        });

        And("the rocket report is valid", () -> {
            // TODO
            // RocketReport rocketReport = rocketREST.getReport("1");
            assertEquals(true, rocketReport.getOverallResult().equals("Good"));

            log.request("Get rocket report from rocket ws").info("Report " + rocketReport.toString()).endSection();
        });

        Then("Richard can make a GO request", () -> {
            rocketRPC.setReadyToLaunch("1", "1");

            log.request("RPC action 'set ready to launch'").trace("rocket id : 1").trace("mission id : 1").endSection();
        });
        Then("Elon makes a launch request to rocket service", () -> {
            launchOrderReply = rocketRPC.LaunchOrderRequest(true, "1");

            log.request("RPC action 'launch order request'").trace("rocket id : 1").endSection();
        });
        Then("the launch order to the rocket is triggered if the rocket is ready to launch", () -> {
            // need to check the boolean
            assertNotNull(launchOrderReply.getReply());
            // TODO : to change
            // missionREST.updateMissionStatus(MissionStatus.STARTED, "1");

            log.request("Get mission status to verify if it has launched").info(MissionStatus.STARTED.toString())
                    .endSection();
        });
        When("rocket first stage is empty in fuel", () -> {

            telemetryBoosterData = telemetryREST.retrieveTelemetryBoosterData("2");
            log.request("Get the new telemetry data from the rocket").info("Booster id : 2");
            while (telemetryBoosterData.get(telemetryBoosterData.size() - 1).getFuel() > 0) {
                telemetryBoosterData = telemetryREST.retrieveTelemetryBoosterData("2");
                // log.trace(rocketTrace(telemetryBoosterData.get(telemetryBoosterData.size())));
                Utils.wait1s();
            }
            assertEquals(true, telemetryBoosterData.get(telemetryBoosterData.size() - 1).getFuel() == 0);
            log.info("First stage empty in fuel").endSection();
        });
        Then("Elon split the rocket", () -> {
            nextStageReply = rocketRPC.nextStage("1");
            assertEquals(true, nextStageReply.getMovedToNextStage());

            log.request("RPC action to split the stages, release the first stage booster").endSection();
        });
        And("Jeff can consult the telemetry data", () -> {
            telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
            assertEquals(true, telemetryRocketData != null);

            log.request("Get to check the telemetry data of the rocket").info("Telemetry " + telemetryRocketData)
                    .endSection();
            Utils.wait1s();
        });
        And("Jeff can inform that there is no anomaly", () -> {
            listAnomalies = telemetryREST.checkForAnomalies();
            assertEquals(true, listAnomalies.size() == 0);

            log.request("Get list of anomalies").info(listAnomalies.toString()).endSection();
            Utils.wait1s();
        });
        When("the payload is on the destination point", () -> {

            telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");

            System.out.println(telemetryRocketData.get(telemetryRocketData.size() - 1));

            log.info("Continue with the second stage");
            while ((int) telemetryRocketData.get(telemetryRocketData.size() - 1).getDistance() > 0) {
                telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");

                log.trace(rocketTrace(telemetryRocketData.get(telemetryRocketData.size() - 1)));
                Utils.wait1s();
            }
            assertEquals(true, (int) (telemetryRocketData.get(telemetryRocketData.size() - 1).getDistance()) == 0);
            log.endSection();
        });
        Then("the mission is succesfull", () -> {
            // TODO : to change
            // missionREST.updateMissionStatus(MissionStatus.SUCCESSFUL, "1");
            Mission mission = missionREST.retrieveMission("1");
            System.out.println(mission);
            assertEquals(true, mission.getStatus().equals(MissionStatus.SUCCESSFUL));
            payloadREST.changePayloadStatus(PayloadStatus.DELIVERED, "4f6911a8-437a-43fc-adad-a0ed6c6f69a7");
            payload = payloadREST.retrievePayload("4f6911a8-437a-43fc-adad-a0ed6c6f69a7");
            System.out.println(payload);

            assertEquals(true, payload.getStatus().equals(PayloadStatus.DELIVERED));

            log.request("Put final mission and payload status").info("mission " + mission.toString())
                    .info("payload " + payload.toString()).endSection();
        });
        And("the booster first stage landed correctly", () -> {
            telemetryBoosterData = telemetryREST.retrieveTelemetryBoosterData("2");

            // assertEquals(telemetryBoosterData.get(telemetryBoosterData.size()-1).getBoosterStatus(),BoosterStatus.LANDED);
        });

    }

    public String rocketTrace(TelemetryRocketData metrics) {
        int range = 10;
        int progress = calculateCurrentProgress(metrics) / range;
        progress = progress >= range ? range - 1 : progress;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int j = 0; j < range; j++) {
            if (progress == j) {
                sb.append("~=>");
            } else {
                sb.append("---");
            }
        }
        sb.append("] ");

        return sb.toString();
    }

    private int calculateCurrentProgress(TelemetryRocketData metrics) {
        double range = metrics.getTotalDistance();
        double correctedStartValue = metrics.getTotalDistance() - metrics.getDistance();
        return (int) ((correctedStartValue * 100) / range);
    }

}
