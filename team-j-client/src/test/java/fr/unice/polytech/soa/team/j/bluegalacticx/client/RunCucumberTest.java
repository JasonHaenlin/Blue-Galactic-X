package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty" }, features = "src/test/resources/features",tags = "@integration" )
public class RunCucumberTest {

}
