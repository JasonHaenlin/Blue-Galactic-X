package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import io.cucumber.java8.En;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherType;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.mission.MissionREST;

public class CreateMissionDemoStepDef implements En {

    Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    // Endpoints
    private WeatherREST weatherREST;
    private MissionREST missionREST;
    private RocketREST rocketREST;
    private RocketRPC rocketRPC;

    // Report
    WeatherReport weatherReport;
    RocketReport rocketReport;

    public CreateMissionDemoStepDef(){
        
        root.setLevel(Level.INFO);

        Given("a handshake with all department", () -> {
            weatherREST = new WeatherREST("http://localhost:8060/weather");
            missionREST = new MissionREST("http://localhost:8070/mission");
            rocketREST = new RocketREST("http://localhost:8080/rocket");
            rocketRPC = new RocketRPC("localhost", 8081);

        });
        And("rocket department create a new report", () -> {


         });
        And("weather department create a new report", () -> {

            weatherReport = new WeatherReport().avgRainfall(50).avgHumidity(10).avgWind(20).avgVisibility(90).avgTemperature(25)
                .weatherType(WeatherType.SUNNY).overallResult("Good");
            weatherREST.setReport(weatherReport);
         });

        When("I add a new mission", () -> {
            String response =  missionREST.createNewMission(new Mission(125, new Date()));
            assertEquals(true, response.contains("200"));
        });

        And("the weather report is valid", () -> {

           String reportString = weatherREST.getReport();
           assertEquals(true,reportString.contains("Good"));
        });

        And("the rocket report is valid", () -> {
            
           String reportString = rocketREST.getReport();
           assertEquals(true,reportString.contains("Good"));
        });

        Then("I can make a GO request", () -> {

            rocketRPC.setReadyToLaunch(true);

        });


    }
    
}
