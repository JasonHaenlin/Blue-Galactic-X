package fr.unice.polytech.soa.team.j.bluegalacticx.springTelemetry;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.TelemetryController;
@AutoConfigureMockMvc
@WebMvcTest(TelemetryController.class)
@ContextConfiguration(classes = { TelemetryController.class })

class TelemetryApplicationTests {

	

}
