import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Student implements Runnable {

    // Arrival time range
    private final long MIN_ARRIVAL_TIME = 1;
    private final long MAX_ARRIVAL_TIME = 1000;

    // Defense time range
    private final long MIN_DEFENSE_TIME = 500;
    private final long MAX_DEFENSE_TIME = 1000;

    private String name;
    private static AtomicInteger counter;

    static {
        counter = new AtomicInteger(0);
    }

    private long arrivalTime; // Time when student arrives
    private long defenseTime; // Duration of student's defense

    private Defense defense;

    private CountDownLatch gradeLatch;

    // Grading results
    private int grade;
    private String examinerName;
    private long startTime;
    private long endTime;

    public Student(Defense defense) {
        // Defence reference
        this.defense = defense;

        // Assign name
        name = "Student " + counter.incrementAndGet();

        // Generate random arrival time
        arrivalTime = Utils.randLong(MIN_ARRIVAL_TIME, MAX_ARRIVAL_TIME);

        // Generate random defense time
        defenseTime = Utils.randLong(MIN_DEFENSE_TIME, MAX_DEFENSE_TIME);

        // Create student grade latch
        gradeLatch = new CountDownLatch(1);
    }

    @Override
    public void run() {
        // Wait for defense to start
        defense.waitForStart();

        // Wait for arrival
        Utils.sleep(arrivalTime);

        // Notify defense that arrived
        defense.studentReady(this);

        // Wait for results
        try {
            gradeLatch.await();
        } catch (InterruptedException e) {
            Utils.print(name + " failed!");
            return; // Defense is over
        }

        // Display results
        printResults();
    }

    public void grade(int grade, String examinerName, long startTime, long endTime) {
        // Save results
        this.grade = grade;
        this.examinerName = examinerName;
        this.startTime = startTime;
        this.endTime = endTime;

        // Add grade
        defense.addGrade(grade);

        // Notify student
        gradeLatch.countDown();
    }

    public void printResults() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(name).append(" | ");
        builder.append("Examiner: ").append(examinerName).append(" | ");
        builder.append("Arrival Time: ").append(arrivalTime).append(" | ");
        builder.append("Start Time: ").append(startTime).append(" | ");
        builder.append("Defense Time: ").append(defenseTime).append(" | ");
        builder.append("End Time: ").append(endTime).append(" | ");
        builder.append("Grade: ").append(grade);
        Utils.print(builder.toString());
    }

    public long getDefenseTime() {
        return defenseTime;
    }
}
