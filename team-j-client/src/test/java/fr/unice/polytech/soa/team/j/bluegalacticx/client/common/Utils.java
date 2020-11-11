package fr.unice.polytech.soa.team.j.bluegalacticx.client.common;

import static org.junit.jupiter.api.Assertions.fail;

public class Utils {

    public static void wait1s() throws InterruptedException {
        Thread.sleep(1000);
    }

    public static void wait100ms() throws InterruptedException {
        Thread.sleep(100);
    }

    public static <T> void assertEqualsWithRetry(T expect, Fun<T> actual) throws InterruptedException {
        assertEqualsWithRetry(expect, actual, 20, 200);
    }

    public static <T> void assertEqualsWithRetry(T expect, Fun<T> actual, int retry) throws InterruptedException {
        assertEqualsWithRetry(expect, actual, retry, 200);
    }

    public static <T> void assertEqualsWithRetry(T expect, Fun<T> actual, int retry, long delay)
            throws InterruptedException {
        int curTry = 0;
        int maxTry = retry;
        T currentValue = null;
        while (!expect.equals(currentValue)) {
            if (curTry > maxTry) {
                fail("FAILED : should be " + expect.toString() + " but was " + currentValue.toString());
            }
            Thread.sleep(delay);
            curTry++;
            try {
                currentValue = actual.method();
            } catch (Exception e) {
                if (curTry > maxTry) {
                    fail("FAILED : should be " + expect.toString() + " but was " + currentValue.toString(), e);
                }
                currentValue = null;
            }
        }
    }

    public static <T> T assertSuccessWithRetry(Fun<T> actual) throws InterruptedException {
        return assertSuccessWithRetry(actual, 20, 200);
    }

    public static <T> T assertSuccessWithRetry(Fun<T> actual, int retry) throws InterruptedException {
        return assertSuccessWithRetry(actual, retry, 200);
    }

    public static <T> T assertSuccessWithRetry(Fun<T> actual, int retry, long delay) throws InterruptedException {
        try {
            return actual.method();
        } catch (Exception e) {
            if (retry <= 0) {
                fail("FAILED : no success was encountered", e);
            }
            Thread.sleep(delay);
            return assertSuccessWithRetry(actual, --retry, delay);
        }
    }

    public static void repeat(int times, FunVoid run) {
        for (int i = 0; i < times; i++) {
            run.method();
        }
    }

    @FunctionalInterface
    public interface Fun<T> {
        T method();
    }

    @FunctionalInterface
    public interface FunVoid {
        void method();
    }

}
