package os;

import os.scheduler.Process;
import os.scheduler.Result;
import os.scheduler.Scheduler;
import os.scheduler.PreemptiveSJF;
import os.scheduler.ACSScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Process> readInput(Scanner input) {

        List<Process> processes = new ArrayList<>();

        System.out.print("Enter number of processes: ");
        int numberOfProcesses = input.nextInt();

        for (int i = 0; i < numberOfProcesses; i++) {

            System.out.println("\nProcess " + (i + 1));

            System.out.print("Enter ID: ");
            String id = input.next();

            System.out.print("Enter Arrival Time: ");
            int arrivalTime = input.nextInt();

            System.out.print("Enter Burst Time: ");
            int burstTime = input.nextInt();

            System.out.print("Enter Priority: ");
            int priority = input.nextInt();

            processes.add(new Process(
                    id,
                    arrivalTime,
                    burstTime,
                    priority
            ));
        }

        return processes;
    }

    private static List<Process> copyProcesses(List<Process> processes) {

        List<Process> copies = new ArrayList<>();

        for (Process p : processes) {

            copies.add(new Process(
                    p.getId(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
            ));
        }

        return copies;
    }

    private static void runScheduler(
            List<Process> processes,
            int choice,
            int quantum,
            int contextSwitch) {

        Scheduler scheduler;
        String schedulerName;

        if (choice == 1) {

            scheduler = new PreemptiveSJF(contextSwitch);
            schedulerName = "Preemptive SJF";

        } else if (choice == 2) {

            scheduler = new ACSScheduler(quantum, contextSwitch);
            schedulerName = "ACS";

        } else {

            System.out.println("Invalid algorithm choice!");
            return;
        }

        Result result = scheduler.run(copyProcesses(processes));

        OutputPrinter.printResults(schedulerName, result);
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        List<Process> processes = readInput(input);

        System.out.println("\nChoose Scheduling Algorithm:");
        System.out.println("1. Preemptive SJF");
        System.out.println("2. ACS");

        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        int quantum = 0;

        if (choice == 2) {
            System.out.print("Enter Time Quantum: ");
            quantum = input.nextInt();
        }

        System.out.print("Enter Context Switch Time: ");
        int contextSwitch = input.nextInt();

        runScheduler(
                processes,
                choice,
                quantum,
                contextSwitch
        );

        input.close();
    }
}