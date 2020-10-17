package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.BoosterController;

@AutoConfigureMockMvc
@WebMvcTest(BoosterController.class)
@ContextConfiguration(classes = { BoosterController.class })
class BoosterApplicationTests {

}
