public abstract class Examiner implements Runnable {

    private String name;

    private Student student; // Student to grade

    public Examiner(String name, Student student) {
        this.name = name;
        this.student = student;
    }

    @Override
    public void run() {
        try {
            // Start defense
            start();

            // Start time reference
            long startTime = Utils.time();

            // Simulate defense
            Thread.sleep(student.getDefenseTime());

            // End defense
            end();

            // End time reference
            long endTime = Utils.time();

            // Grade student
            student.grade(Utils.randInt(5, 10), name, startTime, endTime);

        } catch (InterruptedException e) {

        }
    }

    protected abstract void start() throws InterruptedException;
    protected abstract void end() throws InterruptedException;
}
