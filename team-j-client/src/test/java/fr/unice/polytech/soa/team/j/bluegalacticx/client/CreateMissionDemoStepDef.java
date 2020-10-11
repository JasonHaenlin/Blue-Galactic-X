package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.MissionREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.entities.MissionReply;
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
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities.SpaceMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.TelemetryREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.entities.Anomaly;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.telemetry.entities.TelemetryRocketData;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.entities.WeatherType;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.NextStageReply;
import io.cucumber.java8.En;

public class CreateMissionDemoStepDef implements En {

    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

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
    private SpaceMetrics rocketMetrics;
    private TelemetryRocketData telemetryRocketData;
    private List<Anomaly> listAnomalies;
    private Payload payload;

    public CreateMissionDemoStepDef() {

        root.setLevel(Level.INFO);

        Given("a handshake with all department", () -> {
            weatherREST = new WeatherREST("http://localhost:8060/weather");
            missionREST = new MissionREST("http://localhost:8070/mission");
            rocketREST = new RocketREST("http://localhost:8080/rocket");
            telemetryREST = new TelemetryREST("http://localhost:8090/telemetry");
            payloadREST = new PayloadREST("http://localhost:8050/payload");
            rocketRPC = new RocketRPC("localhost", 8081);

        });


        And("rocket department create a new report", () -> {

            rocketReport = new RocketReport().fuelLevel(10).overallResult("Good");
            rocketREST.setReport(rocketReport);

        });

        And("weather department create a new report", () -> {

            weatherReport = new WeatherReport().avgRainfall(50).avgHumidity(10).avgWind(20).avgVisibility(90)
                    .avgTemperature(25).weatherType(WeatherType.SUNNY).overallResult("Good");
            weatherREST.setReport(weatherReport);
        });



        And("payload department create a new payload", () -> {
            payload = new Payload().type(PayloadType.SPACECRAFT).weight(10000);
            System.out.println(payload);        
            //payloadREST.createNewPayload(payload);

        });

        When("I add a new mission", () -> {
            String response = missionREST.createNewMission(new Mission(125, new Date()));
            assertEquals(true, response.contains("200"));
        });

        And("the weather report is valid", () -> {

            WeatherReport weatherReport = weatherREST.getReport();
            assertEquals(true, weatherReport.getOverallResult().contains("Good"));
        });

        And("the rocket report is valid", () -> {

            RocketReport rocketReport = rocketREST.getReport("1");
            assertEquals(true, rocketReport.getOverallResult().equals("No problem, the preparation of the rocket goes well."));
        });

        Then("I can make a GO request", () -> {
            rocketRPC.setReadyToLaunch("1","1");
        });
        Then("Elon makes a launch request to rocket service", () -> {
            launchOrderReply = rocketRPC.LaunchOrderRequest(true,"1");
        });
        Then("the launch order to the rocket is triggered if the rocket is ready to launch", () -> {
            // need to check the boolean
            assertNotNull(launchOrderReply.getReply());
            missionREST.updateMissionStatus(MissionStatus.STARTED, "1");
        });
        When("rocket first stage is empty in fuel", () -> {

        });
        Then("Elon split the rocket", () -> {
            nextStageReply = rocketRPC.nextStage("1");
            assertEquals(true, nextStageReply.getMovedToNextStage());
        });
        And("Elon send the telemetry rocket data to Jeff", () -> {

            rocketMetrics = rocketREST.getMetrics("1");
            rocketMetrics = rocketMetrics.heatShield(3.0).speed(2.0).distance(1.0);
            rocketMetrics.setRocketId("1");

            assertEquals(true, rocketMetrics!=null);

            telemetryREST.sendTelemetryRocketData(rocketMetrics,"1");
        });
        And("Jeff can consult the telemetry data", () -> {
            telemetryRocketData =  telemetryREST.retrieveTelemetryRocketData("1");

        });
        And("Jeff can inform that there is no anomaly", () -> {
            listAnomalies = telemetryREST.checkForAnomalies();

            assertEquals(true, listAnomalies.size()==0);

        });
        Then("the payload is delivered", () -> {
            
            //Payload payload = payloadREST.retrievePayloadStatus("1");
            //assertEquals(true, payload.getStatus().equals(PayloadStatus.DELIVERED));

        });
        And("the mission is succesfull", () -> {
            missionREST.updateMissionStatus(MissionStatus.SUCCESSFUL, "1");
            //MissionReply missionReply = missionREST.retrieveMissionStatus("1");
            //assertEquals(true, missionReply.getMission());

        });


    }

}
