package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int randomInt(int var, int base) {
        return randomInt(-var, var, base);
    }

    public static int randomInt(int min, int max, int base) {
        return base + ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double randomDouble(double var, double base) {
        return randomDouble(-var, var, base);
    }
    public static double randomDouble(double min, double max, double base) {
        return base + ThreadLocalRandom.current().nextDouble(min, max + 1);
    }
    
}
