package os.scheduler;
import java.util.*;

public class ACSScheduler implements Scheduler{

    private final int quantum;
    private final int contextSwitch;

    // tiers

    private final Queue<Process> tier1 = new ArrayDeque<>(); // round-robin
    private final PriorityQueue<Process> tier2 =
            new PriorityQueue<>(Comparator.comparingInt(Process::getPriority)); // may be changed by tier2's writer.
    // aging

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

    // tier 2 & 3
    private void runTier2(){} // demoted processes will make the loop run forever until this is implemented.
    private void applyAging(){}
}
