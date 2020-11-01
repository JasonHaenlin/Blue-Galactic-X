package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions.TelemetryDataBoosterIdException;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @GetMapping("/ping")
    public String ping() {
        try {
            telemetryService.createBoosterData(
                    TelemetryBoosterRequest.newBuilder().setBoosterId("1").setBoosterStatus("STATUS").build());
        } catch (TelemetryDataBoosterIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "ok";
    }

}
