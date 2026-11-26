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
    private List<String> demotionMatrix;
    private int time;
    private int nextArrivalIdx;
    private int finishedCount;
    private int contextSwitchCount;
    private Process lastProcess;
    private final Map<Process, Integer> shownPriority = new HashMap<>();

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
        shownPriority.clear();
        timeline = new ArrayList<>();
        demotionMatrix = new ArrayList<>();

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

            else{
                logEvent(time, "IDLE", null, 0, 0);
                time = allProcesses.get(nextArrivalIdx).getArrivalTime();
            }
        }

        logEvent(time, "All processes finished", null, 0, 0);
        return new Result(allProcesses, timeline, demotionMatrix, time, contextSwitchCount);
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
        boolean switched = false;

        if(lastProcess != null && lastProcess != p)
        {
            if (contextSwitch > 0) {
                switched = true;
                logEvent(time, "Context switch saves " + lastProcess.getId()
                        + " and loads " + p.getId(), p, 1, time);
                timeline.add("CS " + time + "-" + (time + contextSwitch));
                logArrivalsDuring(time, time + contextSwitch);
            }

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

        logEvent(start, "Running: " + p.getId(), switched ? null : p, switched ? 0 : 1, start);
        logArrivalsDuring(start, start + slice);

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
            logEvent(time, "Demotion: " + p.getId() + " moved to Tier 2", null, 0,0);
        }

        lastProcess = p;
    }

    private void demote(Process p){
        p.setBatch(true);
        p.setTier2EntryTime(time);
        shownPriority.put(p, p.getPriority());
        tier2.add(p);

    }

    // tier 2
    private void runTier2(){
        logAging();
        Process p = pickHighestPriority();
        tier2.remove(p);
        boolean switched = false;

        if (lastProcess != null && lastProcess != p) {
            if (contextSwitch > 0) {
                switched = true;
                logEvent(time, "Context switch saves " + lastProcess.getId()
                        + " and loads " + p.getId(), p, 2, time);
                timeline.add("CS " + time + "-" + (time + contextSwitch));
                logArrivalsDuring(time, time + contextSwitch);
            }

            time += contextSwitch;
            if (contextSwitch > 0) contextSwitchCount++;
            addArrivals(time);
        }

        int start = time;

        logEvent(start, "Running: " + p.getId(), switched ? null : p, switched ? 0 : 2, start);
        logArrivalsDuring(start, start + p.getRemainingTime());

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

    private String describe(Process p, int remaining, int priority) {
        return p.getId() + " (remaining: " + remaining + ", Priority: " + priority + ")";
    }

    private int effectivePriorityAt(Process p, int t) {
        return p.getPriority() - (t - p.getTier2EntryTime()) / agingInterval;
    }

    private String formatTier1(int t, Process current, int tier, int remaining) {
        List<String> parts = new ArrayList<>();

        if (current != null && tier == 1)
            parts.add(describe(current, remaining, current.getPriority()));

        for (Process p : tier1)
            parts.add(describe(p, p.getRemainingTime(), p.getPriority()));

        for (int i = nextArrivalIdx; i < allProcesses.size()
                && allProcesses.get(i).getArrivalTime() <=t; i++) {
            Process p = allProcesses.get(i);
            parts.add(describe(p, p.getRemainingTime(), p.getPriority()));
        }

        return parts.isEmpty() ? "[Empty]" : "[" +
                String.join(", ", parts) + "]";
    }

    private String formatTier2(int t, Process current, int tier, int remaining, int shownSince) {
        List<Process> all = new ArrayList<>(tier2);
        if (current != null && tier == 2 && !all.contains(current))
            all.add(current);

        all.sort(Comparator.comparingInt(Process::getTier2EntryTime)
                .thenComparingInt(Process::getArrivalTime));

        List<String> parts = new ArrayList<>();
        for (Process p : all)
            parts.add(describe(p, p.getRemainingTime() == 0 && p == current ? remaining : p.getRemainingTime(),
                    shownPriority.getOrDefault(p, p.getPriority())));

        return parts.isEmpty() ? "[Empty]" : "[" + String.join(", ", parts) + "]";
    }

    private void logEvent(int t, String action, Process current, int tier, int shownSince) {
        int rem = (current == null) ? 0 : current.getRemainingTime();

        demotionMatrix.add(
                "T=" + t + "| " + action + "\n"
                        + " Tier 1: " + formatTier1(t, current, tier, rem) + "\n"
                        + " Tier 2: " + formatTier2(t, current, tier, rem, shownSince)
        );
    }

    private void logArrivalsDuring(int from, int to) {
        int lastLogged = -1;
        for (int i = nextArrivalIdx; i < allProcesses.size(); i++) {
            int a = allProcesses.get(i).getArrivalTime();
            if (a >= to) break;
            if (a > from && a != lastLogged) {
                StringBuilder ids = new StringBuilder();
                for (int j = i; j < allProcesses.size()
                        && allProcesses.get(j).getArrivalTime() == a; j++) {
                    if (ids.length() > 0) ids.append(", ");
                    ids.append(allProcesses.get(j).getId());
                }

                logEvent(a, "Arrival " + ids, null, 0, 0);
                lastLogged = a;
            }
        }
    }

    private void logAging() {
        if (tier2.isEmpty()) return;

        StringBuilder sb = new StringBuilder("--- Aging check at T = " + time + " ---\n");

        for (Process p : tier2) {
            int waited = time - p.getTier2EntryTime();
            int boost = waited / agingInterval;
            shownPriority.put(p, p.getPriority() - boost);
            sb.append(p.getId() + ": Waited from T = " + p.getTier2EntryTime() + " to T = " + time
                    + " (" + waited + " units). Aging: " + waited + "/" + agingInterval
                    + " = " + boost +". Effective Priority = " + p.getPriority()
                    + " - " + boost + " = " + (p.getPriority() - boost) + "\n"
            );
        }
        if (tier2.size() > 1) demotionMatrix.add(sb.toString());
    }
}
