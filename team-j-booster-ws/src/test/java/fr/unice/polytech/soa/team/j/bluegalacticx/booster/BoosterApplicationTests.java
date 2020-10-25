package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;

@AutoConfigureMockMvc
@WebMvcTest(BoosterController.class)
@ContextConfiguration(classes = { BoosterController.class, BoosterService.class })
class BoosterApplicationTests {

    @MockBean
    BoosterService boosterService;

    @Test
    public void testUpdateSpeedBooster(){
        Booster b = new Booster().fuelLevel(100);
        b.setSpeed(100);

        b.updatePower(0.2);
        // Increase speed
        assertEquals(120, b.getSpeed());

        //Decrease speed
        b.updatePower(-0.2);
        assertEquals(96.0, b.getSpeed());

    }
}
