package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceMetrics;

/**
 * RestService
 */
@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    public SpaceCoordinate getCoordinatesFromMission(String id) {
        return new SpaceCoordinate(0, 0, 0);
        // String url = "http://localhost:8070/mission/{id}/coordinates";
        // return this.restTemplate.getForObject(url, SpaceCoordinate.class, id);
    }

    public void postTelemetry(SpaceMetrics telemetry) {
        String url = "http://localhost:8090/telemetry/rocket";
        HttpEntity<SpaceMetrics> request = new HttpEntity<>(telemetry);
        restTemplate.postForObject(url, request, SpaceMetrics.class);
    }
}
