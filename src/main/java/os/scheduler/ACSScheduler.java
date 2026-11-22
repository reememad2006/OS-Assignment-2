package os.scheduler;
import java.util.*;

public class ACSScheduler implements Scheduler{

    private final int quantum;
    private final int contextSwitch;

    // tiers
    private final Queue<Process> tier1 = new ArrayDeque<>(); // round-robin
    private final List<Process> tier2 = new ArrayList<>();  // demotion

    // aging
    private static final int agingInterval = 4;

    // simulation
    private List<Process> allProcesses;
    private List<String> timeline;
    private int time;
    private int nextArrivalIdx;
    private int finishedCount;
    private int contextSwitchCount;
    private Process lastProcess;

    public ACSScheduler(int quantum, int contextSwitch){
        this.quantum = quantum;
        this.contextSwitch = contextSwitch;
    }

    @Override
    public Result run(List<Process> processes){

        allProcesses = new ArrayList<>(processes);
        allProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));

        for(Process p : allProcesses){p.resetForSimulation();}

        // resetting scheduler's state
        tier1.clear();
        tier2.clear();
        timeline = new ArrayList<>();

        time = 0;
        nextArrivalIdx = 0;
        finishedCount = 0;
        contextSwitchCount = 0;
        lastProcess = null;

        while(finishedCount < allProcesses.size())
        {
            addArrivals(time);

            if(!tier1.isEmpty()){runTier1Slice();}

            else if(!tier2.isEmpty()){runTier2();}

            else{time = allProcesses.get(nextArrivalIdx).getArrivalTime();}
        }

        return new Result(allProcesses, timeline, time, contextSwitchCount);
    }

    // tier 1
    private void addArrivals(int currentTime){
        while(nextArrivalIdx < allProcesses.size() && allProcesses.get(nextArrivalIdx).getArrivalTime() <= currentTime)
        {
            tier1.add(allProcesses.get(nextArrivalIdx));
            nextArrivalIdx++;
        }
    }

    private void runTier1Slice(){
        Process p = tier1.poll();

        if(lastProcess != null && lastProcess != p)
        {
            time += contextSwitch;
            if(contextSwitch > 0){contextSwitchCount++;}
            addArrivals(time);
        }

        if(p.getFirstStartTime() == -1)
        {
            p.setFirstStartTime(time);
            p.setResponseTime(time - p.getArrivalTime());
        }

        int slice = Math.min(quantum, p.getRemainingTime());
        int start = time;

        time += slice;
        p.setRemainingTime(p.getRemainingTime() - slice);
        timeline.add(p.getId() + " " + start + "-" + time);

        addArrivals(time);

        if(p.getRemainingTime() == 0)
        {
            p.setCompletionTime(time);
            p.setTurnaroundTime(time - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
            finishedCount++;
        }
        else
        {
            demote(p);
        }

        lastProcess = p;
    }

    private void demote(Process p){
        p.setBatch(true);
        p.setTier2EntryTime(time);
        tier2.add(p);
    }

    // tier 2
    private void runTier2(){
        Process p = pickHighestPriority();
        tier2.remove(p);

        if (lastProcess != null && lastProcess != p) {
            time += contextSwitch;
            if (contextSwitch > 0) contextSwitchCount++;
            addArrivals(time);
        }

        int start = time;
        time += p.getRemainingTime();
        p.setRemainingTime(0);
        timeline.add(p.getId() + " " + start + "-" + time);

        addArrivals(time);

        finishProcess(p);
        lastProcess = p;
    }

    // priority handling
    private Process pickHighestPriority() {
        Process best = tier2.get(0);
        for (Process p : tier2)
            if (isBetter(p, best)) best = p;

        return best;
    }

    private boolean isBetter(Process a, Process b) {
        int pa = getEffectivePriority(a);
        int pb = getEffectivePriority(b);

        if (pa != pb) return pa < pb;
        if (a.getTier2EntryTime() != b.getTier2EntryTime())
            return a.getTier2EntryTime() < b.getTier2EntryTime();

        return a.getArrivalTime() < b.getArrivalTime();
    }

    private int getEffectivePriority(Process p) {
        return applyAging(p);
    }

    private void finishProcess(Process p) {
        p.setCompletionTime(time);
        p.setTurnaroundTime(time - p.getArrivalTime());
        p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
        finishedCount++;
    }

    // applying aging
    private int applyAging(Process p){
        int waited = time - p.getTier2EntryTime();
        int boost = waited / agingInterval;
        return p.getPriority() - boost;
    }
}
