import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private static ThreadManager instance;

    private ExecutorService executorService;

    private ThreadManager(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public static void initialize(int numberOfThreads) {
        if (instance != null) return;
        instance = new ThreadManager(numberOfThreads);
    }

    public static void execute(Runnable command) {
        instance.executorService.execute(new Thread(command));
    }

    public static void shutdown() {
        instance.executorService.shutdownNow();
    }
}
