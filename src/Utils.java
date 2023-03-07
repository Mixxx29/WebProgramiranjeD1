import java.time.LocalDateTime;
import java.util.Random;

public class Utils {
    private static final String PRINT_LOCK = "PRINT_LOCK";

    private static volatile Random rand;

    private static volatile long startTime;

    public static void print(String text) {
        synchronized (PRINT_LOCK) {
            System.out.println("- " + text);
        }
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            print("Sleep interrupted!");
        }
    }

    public static void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    public static long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void setSeed(long seed) {
        rand = new Random(seed);
    }

    public static float nextFloat() {
        return rand.nextFloat();
    }

    public static int randInt(int min, int max) {
        return (int)(rand.nextFloat() * (max - min) + min);
    }

    public static long randLong(long min, long max) {
        return (long)(rand.nextFloat() * (max - min) + min);
    }
}
