package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import static org.junit.jupiter.api.Assertions.fail;

public class Utils {

    public static void wait1s() throws InterruptedException {
        Thread.sleep(1000);
    }

    public static void wait100ms() throws InterruptedException {
        Thread.sleep(100);
    }

    public static <T> void assertEqualsWithRetry(T expect, Actual<T> actual) throws InterruptedException {
        assertEqualsWithRetry(expect, actual, 20, 200);
    }

    public static <T> void assertEqualsWithRetry(T expect, Actual<T> actual, int retry) throws InterruptedException {
        assertEqualsWithRetry(expect, actual, retry, 200);
    }

    public static <T> void assertEqualsWithRetry(T expect, Actual<T> actual, int retry, long delay)
            throws InterruptedException {
        int curTry = 0;
        int maxTry = retry;
        T currentValue = null;
        while (!expect.equals(currentValue)) {
            if (curTry > maxTry) {
                fail("FAILED : should be " + expect.toString() + " but was " + currentValue.toString());
            }
            Thread.sleep(delay);
            currentValue = actual.method();
            curTry++;
        }
    }

    @FunctionalInterface
    public interface Actual<T> {
        T method();
    }
}
