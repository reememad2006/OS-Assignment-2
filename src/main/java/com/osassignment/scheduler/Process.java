
package com.osassignment.scheduler;

public class Process {

    private String id;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int priority;

    private int waitingTime;
    private int turnaroundTime;
    private int responseTime;
    private int completionTime;

    private int firstStartTime = -1;

    public Process(String id, int arrivalTime,
                   int burstTime, int priority) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Process ID cannot be empty");
        }

        if (arrivalTime < 0 || burstTime <= 0) {
            throw new IllegalArgumentException("Invalid arrival or burst time");
        }

        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getFirstStartTime() {
        return firstStartTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setFirstStartTime(int firstStartTime) {
        this.firstStartTime = firstStartTime;
    }

    @Override
    public String toString() {
        return "Process " + id +
                " | Arrival: " + arrivalTime +
                " | Burst: " + burstTime +
                " | Completion: " + completionTime +
                " | Waiting: " + waitingTime +
                " | Turnaround: " + turnaroundTime +
                " | Response: " + responseTime;
    }
    public void resetForSimulation() {
        remainingTime = burstTime;
        waitingTime = 0;
        turnaroundTime = 0;
        responseTime = 0;
        completionTime = 0;
        firstStartTime = -1;
    }
}
