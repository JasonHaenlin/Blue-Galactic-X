package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryBoosterData;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.TelemetryRocketData;

@SpringJUnitConfig(value = { TelemetryRocketData.class, 
        TelemetryBoosterData.class })
@TestMethodOrder(OrderAnnotation.class)
public class TelemetryAnomaliesTest {

    TelemetryRocketData rocketData;

    TelemetryBoosterData boosterData;

    @Test
    @Order(1)
    public void check_metrics_with_anomaly() throws Exception {
        rocketData = new TelemetryRocketData("1", 200, 500, 10000, 30, 10, 10, 80, 900, 300, 1000);
        assertEquals(1, rocketData.checkForAnomalies().size());
    }

    @Test
    @Order(2)
    public void check_metrics_with_multiple_anomalies() throws Exception {
        boosterData = new TelemetryBoosterData(3, "1", "1", BoosterStatus.LANDING, 30.0, 500.0);
        assertEquals(2, boosterData.checkForAnomalies().size());
    }

    @Test
    @Order(3)
    public void check_metrics_with_no_anomaly() throws Exception {
        rocketData = new TelemetryRocketData("1", 200, 500, 400, 30, 10, 10, 80, 900, 300, 1000);
        assertEquals(0, rocketData.checkForAnomalies().size());
    }
    
}
