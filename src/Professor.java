import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Professor extends Examiner {

    private static final Semaphore semaphore;
    private static final CyclicBarrier enterBarrier;
    private static final CyclicBarrier exitBarrier;

    static {
        semaphore = new Semaphore(2);
        enterBarrier = new CyclicBarrier(2);
        exitBarrier = new CyclicBarrier(2);
    }

    public Professor(Student student) {
        super("Professor", student);
    }

    @Override
    protected void start() throws InterruptedException {
        semaphore.acquire();
        try {
            enterBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void end() throws InterruptedException {
        try {
            enterBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }
}
