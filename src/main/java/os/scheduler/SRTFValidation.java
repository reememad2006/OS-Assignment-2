
package os.scheduler;

import java.util.ArrayList;
import java.util.List;

public class SRTFValidation {

    private static void check(
            String testName,
            boolean condition) {

        if (!condition) {
            throw new AssertionError(
                    "FAILED: " + testName
            );
        }

        System.out.println("PASSED: " + testName);
    }

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 8, 2));
        processes.add(new Process("P2", 1, 4, 1));
        processes.add(new Process("P3", 2, 2, 3));
        processes.add(new Process("P4", 3, 1, 2));

        int contextSwitchTime = 1;

        Scheduler scheduler =
                new PreemptiveSJF(contextSwitchTime);

        Result result = scheduler.run(processes);

        System.out.println("===== SRTF VALIDATION =====");

        // Validate total simulation time
        check(
                "Total Simulation Time",
                result.getTotalTime() == 19
        );

        // Validate context switch count
        check(
                "Context Switch Count",
                result.getContextSwitchCount() == 4
        );

        // Validate average metrics
        check(
                "Average Waiting Time",
                Math.abs(
                        result.getAverageWaitingTime() - 4.75
                ) < 0.001
        );

        check(
                "Average Turnaround Time",
                Math.abs(
                        result.getAverageTurnaroundTime() - 8.50
                ) < 0.001
        );

        check(
                "Average Response Time",
                Math.abs(
                        result.getAverageResponseTime() - 2.00
                ) < 0.001
        );

        // Print timeline
        System.out.println("\n===== SRTF TIMELINE =====");

        int time = 0;

        for (String entry : result.getTimeline()) {
            System.out.println(
                    "Time " + time + " : " + entry
            );
            time++;
        }

        // Print process results
        System.out.println("\n===== PROCESS RESULTS =====");

        for (Process p : result.getProcesses()) {
            System.out.println(p);
        }

        System.out.println("\nALL VALIDATION TESTS PASSED!");
    }
}
