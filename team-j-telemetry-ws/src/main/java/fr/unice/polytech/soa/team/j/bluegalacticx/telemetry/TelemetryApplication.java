package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class TelemetryApplication {
	public static void main(String[] args) {
		SpringApplication.run(TelemetryApplication.class, args);
	}

}
