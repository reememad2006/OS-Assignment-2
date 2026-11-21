
package os.scheduler;

import java.util.List;

public interface Scheduler {

    Result run(List<Process> processes);

}
