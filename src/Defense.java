import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Defense implements Runnable {

    private int duration;

    private CountDownLatch startLatch; // Start defense latch

    private long startTime; // Time when defense started

    private List<Student> students; // Arrived students

    // Calculation of average grade
    private AtomicInteger sumOfGrades;
    private AtomicInteger studentsGraded;

    public Defense(int duration) {
        // Defense duration
        this.duration = duration;

        // Create start latch
        startLatch = new CountDownLatch(1);

        // Allocate students list
        students = new ArrayList<>();

        sumOfGrades = new AtomicInteger(0);
        studentsGraded = new AtomicInteger(0);
    }

    public void start() {
        Utils.print("START");
        ThreadManager.execute(this); // Start defense thread
        Utils.setStartTime(); // Set start time
        Utils.sleep(duration); // Sleep main thread for duration
        Utils.print("END");

        // Print average grade
        Utils.print("Average grade: " + (float)sumOfGrades.get() / (float)studentsGraded.get());
    }

    public void waitForStart() {
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        startLatch.countDown(); // Start defense
        startTime = Utils.time(); // Take reference of start time

        while (!isOver()) {
            // Delegate student
            if (!students.isEmpty()) {
                float value = Utils.nextFloat(); // Random value between 0 and 1

                // Assign student
                if (value >= 0.5f) {
                    ThreadManager.execute(new Professor(students.get(0))); // Create new professor thread
                } else {
                    ThreadManager.execute(new Assistant(students.get(0))); // Create new assistant thread
                }

                // Student delegated
                students.remove(0);
            }

            // Sleep for amount of time
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
        }
    }

    public void addGrade(int grade) {
        sumOfGrades.addAndGet(grade);
        studentsGraded.incrementAndGet();
    }

    synchronized public void studentReady(Student student) {
        students.add(student);
    }

    public boolean isOver() {
        return Utils.time() >= duration;
    }
}
