
package com.osassignment.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PreemptiveSJF implements Scheduler {

    private final int contextSwitch;

    public PreemptiveSJF(int contextSwitch) {

        if (contextSwitch < 0) {
            throw new IllegalArgumentException(
                    "Context switch time cannot be negative"
            );
        }

        this.contextSwitch = contextSwitch;
    }

    private Process pickShortestRemaining(List<Process> processes,
                                          int currentTime) {

        return processes.stream()
                .filter(p -> p.getArrivalTime() <= currentTime)
                .filter(p -> p.getRemainingTime() > 0)
                .min(Comparator
                        .comparingInt(Process::getRemainingTime)
                        .thenComparingInt(Process::getArrivalTime)
                        .thenComparing(Process::getId))
                .orElse(null);
    }

    @Override
    public Result run(List<Process> inputProcesses) {

        if (inputProcesses == null) {
            throw new IllegalArgumentException(
                    "Process list cannot be null"
            );
        }

        // Check that the list does not contain null processes.
        for (Process p : inputProcesses) {
            if (p == null) {
                throw new IllegalArgumentException(
                        "Process list cannot contain null"
                );
            }
        }

        // Create copies so the original input is not modified.
        List<Process> processes = new ArrayList<>();

        for (Process p : inputProcesses) {

            processes.add(new Process(
                    p.getId(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
            ));
        }

        List<String> timeline = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int contextSwitchCount = 0;

        Process lastExecuted = null;

        while (completed < processes.size()) {

            Process shortest = pickShortestRemaining(
                    processes, currentTime
            );

            // No process is ready.
            if (shortest == null) {

                timeline.add("IDLE");
                currentTime++;

                lastExecuted = null;
                continue;
            }

            // A switch is needed when changing processes.
            if (lastExecuted != null && lastExecuted != shortest) {

                contextSwitchCount++;

                // Consume context-switch time.
                for (int i = 0; i < contextSwitch; i++) {

                    timeline.add("CS");
                    currentTime++;
                }

                // Recheck after context switching because
                // a shorter process may have arrived.
                shortest = pickShortestRemaining(
                        processes, currentTime
                );
            }

            if (shortest == null) {
                lastExecuted = null;
                continue;
            }

            // Record the first time this process gets the CPU.
            if (shortest.getFirstStartTime() == -1) {

                shortest.setFirstStartTime(currentTime);

                shortest.setResponseTime(
                        currentTime - shortest.getArrivalTime()
                );
            }

            // Execute for one time unit.
            timeline.add(shortest.getId());

            shortest.setRemainingTime(
                    shortest.getRemainingTime() - 1
            );

            currentTime++;

            lastExecuted = shortest;

            // Check if the process has finished.
            if (shortest.getRemainingTime() == 0) {

                completed++;

                shortest.setCompletionTime(currentTime);

                int turnaroundTime =
                        shortest.getCompletionTime()
                                - shortest.getArrivalTime();

                int waitingTime =
                        turnaroundTime - shortest.getBurstTime();

                shortest.setTurnaroundTime(turnaroundTime);
                shortest.setWaitingTime(waitingTime);
            }
        }

        return new Result(
                processes,
                timeline,
                currentTime,
                contextSwitchCount
        );
    }

    public int getContextSwitch() {
        return contextSwitch;
    }
}
