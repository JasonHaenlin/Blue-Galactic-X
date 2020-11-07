package fr.unice.polytech.soa.team.j.bluegalacticx.booster.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

}
