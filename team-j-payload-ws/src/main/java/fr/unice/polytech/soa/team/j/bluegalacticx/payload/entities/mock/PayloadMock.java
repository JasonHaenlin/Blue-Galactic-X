package fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.Payload;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.PayloadType;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities.SpaceCoordinate;

public class PayloadMock {

    public static final List<Payload> payloads;

    static {
        payloads = new ArrayList<>();
        payloads.add(new Payload(PayloadType.WARHEADS, "5", PayloadStatus.WAITING_FOR_MISSION,
                new SpaceCoordinate(30, 10, 0), 500, "6c8c700c-1c01-4c72-b845-f251f01502e4", new Date()));
        payloads.add(new Payload(PayloadType.SATELLITE, "10", PayloadStatus.WAITING_FOR_MISSION,
                new SpaceCoordinate(30, 20, 0), 500, "4f6911a8-437a-43fc-adad-a0ed6c6f69a7", new Date()));
    }

    public static final Optional<Payload> find(String id) {
        return payloads.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public static final Optional<Payload> findByRocketId(String id) {
        return payloads.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

}
