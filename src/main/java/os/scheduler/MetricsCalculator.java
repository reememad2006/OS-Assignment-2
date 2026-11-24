package os.scheduler;
import java.util.ArrayList;

public class MetricsCalculator {
    void computeTurnaroundTime(Process process){
        process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());
    }

    void computeWaitingTime(Process process){
        process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
    }

    void computeResponseTime(Process process){
        if(process.getFirstStartTime()>=0){
        process.setResponseTime(process.getFirstStartTime() - process.getArrivalTime());
        }
    }

    double computeAverageWaitingTime(ArrayList<Process> processes) {
        double sum = 0;
        if(processes.isEmpty()){
            return 0;
        }
        for (int i = 0; i < processes.size(); i++) {
            sum += processes.get(i).getWaitingTime();
        }
        return sum / processes.size();
    }

    double computeAverageTurnaroundTime(ArrayList<Process> processes){
        double sum = 0;
        if(processes.isEmpty()){
            return 0 ;
        }
        for(int i=0;i<processes.size();i++){
            sum += processes.get(i).getTurnaroundTime();
        }
        return sum / processes.size();
    }
}
