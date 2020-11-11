package fr.unice.polytech.soa.team.j.bluegalacticx.orbitalpayload.entities;

import java.util.concurrent.ThreadLocalRandom;

import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadRequest.SpaceCoordinate;

public class PayloadAPI {
    private SpaceCoordinate position;

    public PayloadAPI(SpaceCoordinate position) {
        this.position = position;
    }

    /**
     * random values to simulate movement
     * 
     * @return
     */
    public SpaceCoordinate recoverCurrentPosition() {
        position = SpaceCoordinate.newBuilder().setX(position.getX() + randomInt(-10, 10))
                .setY(position.getY() + randomInt(-10, 10)).setZ(position.getZ() + randomInt(-10, 10)).build();

        return position;
    }

    public SpaceCoordinate getPosition() {
        return position;
    }

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
