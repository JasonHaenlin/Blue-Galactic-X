package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@WebMvcTest(BoosterController.class)
@ContextConfiguration(classes = { BoosterController.class })
class BoosterApplicationTests {

}
