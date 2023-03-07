import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        // Get number of students
        System.out.println("Enter number of students:");
        Scanner scanner = new Scanner(System.in);
        int numberOfStudents = scanner.nextInt();
        scanner.close();

        // Initialize thread manager
        ThreadManager.initialize(numberOfStudents + 1 + 1 + 2);

        // Initialize seed
        Utils.setSeed(System.currentTimeMillis()); // Use random seed
        //Utils.setSeed(0); // use concrete seed

        // Initialize new defense
        Defense defense = new Defense(5000);

        // Initialize students
        for (int i = 0; i < numberOfStudents; i++) {
            ThreadManager.execute(new Student(defense)); // Create new student thread
        }

        // Start defense
        defense.start();

        // Shutdown remaining threads
        ThreadManager.shutdown();
    }
}
