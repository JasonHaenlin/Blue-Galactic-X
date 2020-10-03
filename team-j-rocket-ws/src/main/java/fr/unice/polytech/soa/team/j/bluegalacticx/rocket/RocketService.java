package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.ReportNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;

@Service
public class RocketService {

    // private String report;
    private RocketReport report;
    private RocketStatus status;

    public RocketStatusReply getStatus() {
        Object obj;
        status = new RocketStatus();
        try {
            obj = new JSONParser().parse(new FileReader("src/main/java/fr/unice/polytech/soa/team/j/bluegalacticx/rocket/mock/mock.json"));
            JSONObject jo = (JSONObject) obj;
            status.setIrradiance(((Long)jo.get("Irradiance")).intValue())
            .setVelocityVariation(((Long)jo.get("VelocityVariation")).intValue())
            .setTemperature(((Long)jo.get("Temperature")).intValue())
            .setGroundVibration(((Long)jo.get("GroundVibration")).intValue())
            .setBoosterRGA(((Long)jo.get("BoosterRGA")).intValue())
            .setMidRocketRGA(((Long)jo.get("MidRocketRGA")).intValue());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new RocketStatusReply(status);
    }

    public void submitReport(RocketReport rocketReport) {
        report = rocketReport;
    }

    public RocketReport getReport() throws ReportNotFoundException {
        if (report == null) {
            throw new ReportNotFoundException();
        }
        return report;
    }
}
