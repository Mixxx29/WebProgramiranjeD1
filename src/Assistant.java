
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Assistant extends Examiner {

    private static final Semaphore semaphore;

    static {
        semaphore = new Semaphore(2);
    }

    public Assistant(Student student) {
        super("Assistant", student);
    }

    @Override
    protected void start() throws InterruptedException {
        semaphore.acquire();
    }

    @Override
    protected void end() {
        semaphore.release();
    }
}
