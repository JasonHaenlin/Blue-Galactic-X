package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.payload.PayloadREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.payload.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.TelemetryREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.entities.WeatherType;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.proto.NextStageReply;
import io.cucumber.java8.En;

public class CreateMissionDemoStepDef implements En {

    private static final Logger LOG = LogManager.getLogger(CreateMissionDemoStepDef.class);

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
    private RocketMetrics rocketMetrics;
    private TelemetryRocketData telemetryRocketData;
    private List<Anomaly> listAnomalies;
    private Payload payload;

    public CreateMissionDemoStepDef() {

        Given("a handshake with all department", () -> {
            LOG.info("Create REST clients and RPC Clients");
            weatherREST = new WeatherREST("http://localhost:8060/weather");
            missionREST = new MissionREST("http://localhost:8070/mission");
            rocketREST = new RocketREST("http://localhost:8080/rocket");
            telemetryREST = new TelemetryREST("http://localhost:8090/telemetry");
            payloadREST = new PayloadREST("http://localhost:8050/payload");
            rocketRPC = new RocketRPC("localhost", 8081);

        });

        And("Elon from the rocket department check the rocket metrics and create a report for Richard", () -> {
            rocketReport = new RocketReport().fuelLevel(10).overallResult("Good");
            LOG.info("Report : \n" + rocketReport.toString());
            rocketREST.setReport(rocketReport);
        });

        And("Tory from the weather department check the weather metrics and create a report for Richard", () -> {
            weatherReport = new WeatherReport().avgRainfall(50).avgHumidity(10).avgWind(20).avgVisibility(90)
                    .avgTemperature(25).weatherType(WeatherType.SUNNY).overallResult("Good");
            LOG.info("Report : \n" + weatherReport.toString());
            weatherREST.setReport(weatherReport);
        });

        And("Gwynne from the payload department create a new payload", () -> {
            payload = new Payload().id("4f6911a8-437a-43fc-adad-a0ed6c6f69a7").type(PayloadType.SPACECRAFT)
                    .weight(10000);
            LOG.info("");
            payloadREST.createNewPayload(payload);

        });

        When("Richard add a new mission", () -> {
            Mission m = new Mission().id("1").payloadId("4f6911a8-437a-43fc-adad-a0ed6c6f69a7")
                    .destination(new SpaceCoordinate(10, 10, 10)).date(new Date());
            String response = missionREST.createNewMission(m);
            LOG.info("mission : \n" + m);
            assertEquals(true, response.contains("200"));
        });

        And("the weather report is valid", () -> {
            WeatherReport weatherReport = weatherREST.getReport();
            assertEquals(true, weatherReport.getOverallResult().contains("Good"));
        });

        And("the rocket report is valid", () -> {
            RocketReport rocketReport = rocketREST.getReport("1");
            assertEquals(true,
                    rocketReport.getOverallResult().equals("No problem, the preparation of the rocket goes well."));
        });

        Then("Richard can make a GO request", () -> {
            rocketRPC.setReadyToLaunch("1", "1");
        });
        Then("Elon makes a launch request to rocket service", () -> {
            launchOrderReply = rocketRPC.LaunchOrderRequest(true, "1");
        });
        Then("the launch order to the rocket is triggered if the rocket is ready to launch", () -> {
            // need to check the boolean
            assertNotNull(launchOrderReply.getReply());
            missionREST.updateMissionStatus(MissionStatus.STARTED, "1");

            telemetryREST.sendTelemetryRocketData(rocketMetrics, "1");
        });
        When("rocket first stage is empty in fuel", () -> {

            telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
            while (telemetryRocketData.getBoosters().get(0).getFuelLevel() > 0) {
                telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
                LOG.info(telemetryRocketData.toString());
                Thread.sleep(1000);
            }
            assertEquals(true, telemetryRocketData.getBoosters().get(0).getFuelLevel() == 0);
        });
        Then("Elon split the rocket", () -> {
            nextStageReply = rocketRPC.nextStage("1");
            assertEquals(true, nextStageReply.getMovedToNextStage());

            rocketMetrics = rocketREST.getMetrics("1");

            assertEquals(true, rocketMetrics != null);

        });
        And("Jeff can consult the telemetry data", () -> {
            telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
            assertEquals(true, telemetryRocketData != null);
            Thread.sleep(1000);
        });
        And("Jeff can inform that there is no anomaly", () -> {
            listAnomalies = telemetryREST.checkForAnomalies();
            assertEquals(true, listAnomalies.size() == 0);
            LOG.info(listAnomalies.toString());
            Thread.sleep(1000);
        });
        When("the payload is on the destination point", () -> {

            telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
            while ((int) telemetryRocketData.getDistance() > 0) {
                telemetryRocketData = telemetryREST.retrieveTelemetryRocketData("1");
                LOG.info(telemetryRocketData.toString());
                Thread.sleep(1000);
            }
            assertEquals(true, (int) (telemetryRocketData.getDistance()) == 0);

        });
        Then("the mission is succesfull", () -> {
            missionREST.updateMissionStatus(MissionStatus.SUCCESSFUL, "1");
            Mission mission = missionREST.retrieveMissionStatus("1");
            LOG.info(mission.toString());
            assertEquals(true, mission.getMissionStatus().equals(MissionStatus.SUCCESSFUL));
            payload = payloadREST.retrievePayload("4f6911a8-437a-43fc-adad-a0ed6c6f69a7");
            LOG.info(payload.toString());
            assertEquals(true, payload.getStatus().equals(PayloadStatus.DELIVERED));

        });

    }

}
