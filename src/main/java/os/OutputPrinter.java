package os;

import os.scheduler.Process;
import os.scheduler.Result;

import java.util.List;

public class OutputPrinter {
    public static void printResults(String schedulerName, Result result) {

        System.out.println("\n==================================");
        System.out.println("      " + schedulerName + " Results");
        System.out.println("==================================");

        System.out.println("\nTimeline:");
        printTimeline(result.getTimeline());

        if (!result.getTierMatrices().isEmpty()) {
            System.out.println("\nDemotion Matrices:");
            printTierMatrices(result.getTierMatrices());
        }

        System.out.println("\nProcess Results:");
        for (Process p : result.getProcesses()) {
            System.out.println(
                    "Process " + p.getId()
                               + " | Arrival Time: " + p.getArrivalTime()
                               + " | Finish Time: " + p.getCompletionTime()
                               + " | Turnaround Time: " + p.getTurnaroundTime()
                               + " | Waiting Time: " + p.getWaitingTime()
                               + " | Response Time: " + p.getResponseTime()
            );
        }

        System.out.printf("\nAverage Turnaround Time: %.2f time units%n",
                result.getAverageTurnaroundTime());

        System.out.printf("Average Waiting Time: %.2f time units%n",
                          result.getAverageWaitingTime());

        System.out.printf("Average Response Time: %.2f time units%n",
                          result.getAverageResponseTime());

        System.out.println("==================================");
    }

    private static void printTimeline(List<String> timeline) {

        if (timeline.isEmpty()) {
            System.out.println("No execution.");
            return;
        }

        // Check if the timeline already contains intervals
        if (timeline.get(0).contains("-")) {
            for (String entry : timeline) {
                System.out.println(entry);
            }
            return;
        }

        // Group consecutive time units for SJF
        int start = 0;

        while (start < timeline.size()) {

            String process = timeline.get(start);
            int end = start + 1;

            while (end < timeline.size() && timeline.get(end).equals(process)) {
                end++;
            }

            System.out.println(process + " " + start + "-" + end);

            start = end;
        }
    }
    private static void  printTierMatrices(List<String> matrices) {
        boolean afterAging = false;

        for (String entry : matrices) {
            if (!afterAging) System.out.println("----------------------------------");
            System.out.println(entry.stripTrailing());
            afterAging = entry.startsWith("--- Aging");
        }

        System.out.println("----------------------------------");
    }
}
