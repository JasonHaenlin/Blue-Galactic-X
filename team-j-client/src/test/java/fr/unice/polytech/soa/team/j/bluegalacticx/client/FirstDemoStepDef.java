package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.RocketRPC;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.models.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.WeatherREST;
import fr.unice.polytech.soa.team.j.bluegalacticx.client.api.weather.models.WeatherStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.LaunchOrderReply;
import io.cucumber.java8.En;

public class FirstDemoStepDef implements En{
    Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    //Endpoints
    WeatherREST weatherREST;
    RocketREST rocketREST;
    RocketRPC rocketRPC;

    //REST responses
    WeatherStatus weatherStatus;
    RocketStatus rocketStatus;

    //RPC responses
    LaunchOrderReply launchOrderReply;

    public FirstDemoStepDef() {
        root.setLevel(Level.INFO);
        Given("a handshake with all services", () -> {
            weatherREST = new WeatherREST("http://localhost:8060/weather");
            rocketREST = new RocketREST("http://localhost:8080/rocket");
            rocketRPC = new RocketRPC("localhost", 8081);
        });

        When("Tory makes a request to the weather service for weather forecast", () -> {
            weatherStatus = weatherREST.getStatus();
        });
        
        And("the weather service's response is SUNNY", () -> {
            WeatherStatus ws = new WeatherStatus();
            ws.setStatus("SUNNY");
            assertEquals(ws, weatherStatus); 
        });

        When("Elon makes a request to the rocket service to get the rocket status", () -> {
            rocketStatus = rocketREST.getStatus();
        });

        Then("the weather and the rocket status is ok", () -> {
            WeatherStatus ws = new WeatherStatus();
            ws.setStatus("SUNNY");
            RocketStatus rs = new RocketStatus();
            rs.setStatus("READY"); 
            assertEquals(ws, weatherStatus);
            assertEquals(rs, rocketStatus);
        });

        Then("Richard makes a request to the rocket service that everything is ok", () -> {
            rocketRPC.setReadyToLaunch(true);
        });
        Then("Elon makes a launch request to rocket service", () -> {
            launchOrderReply = rocketRPC.LaunchOrderRequest(true);
        });
        Then("the launch order to the rocket is triggered if the rocket is ready to launch.", () -> {
            assertEquals("Launch approved !", launchOrderReply.getReply());
        });
    }
    
}
