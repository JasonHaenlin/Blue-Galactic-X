package fr.unice.polytech.soa.team.j.bluegalacticx.springPayload;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.PayloadController;
@AutoConfigureMockMvc
@WebMvcTest(PayloadController.class)
@ContextConfiguration(classes = { PayloadController.class })

class PayloadApplicationTests {

	

}
