
package com.osassignment.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result {

    private final List<Process> processes;
    private final List<String> timeline;
    private final int totalTime;
    private final int contextSwitchCount;

    public Result(List<Process> processes,
                  List<String> timeline,
                  int totalTime,
                  int contextSwitchCount) {

        this.processes = new ArrayList<>(processes);
        this.timeline = new ArrayList<>(timeline);
        this.totalTime = totalTime;
        this.contextSwitchCount = contextSwitchCount;
    }

    public List<Process> getProcesses() {
        return Collections.unmodifiableList(processes);
    }

    public List<String> getTimeline() {
        return Collections.unmodifiableList(timeline);
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getContextSwitchCount() {
        return contextSwitchCount;
    }

    public double getAverageWaitingTime() {
        return processes.stream()
                .mapToInt(Process::getWaitingTime)
                .average()
                .orElse(0.0);
    }

    public double getAverageTurnaroundTime() {
        return processes.stream()
                .mapToInt(Process::getTurnaroundTime)
                .average()
                .orElse(0.0);
    }

    public double getAverageResponseTime() {
        return processes.stream()
                .mapToInt(Process::getResponseTime)
                .average()
                .orElse(0.0);
    }
}
