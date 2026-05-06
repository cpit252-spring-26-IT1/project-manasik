package sa.edu.kau.fcit.cpit252.project.service;

import java.util.Set;

public class SavedProgress {
    public final String ritualName;
    public final int currentIndex;
    public final Set<Integer> completedSteps;


    public SavedProgress(String ritualName, int currentIndex, Set<Integer> completedSteps) {
        this.ritualName = ritualName;
        this.currentIndex = currentIndex;
        this.completedSteps = completedSteps;
    }

    // gets the name of the saved ritual
    public String getRitualName() {
        return ritualName;
    }

    // gets the last active step index
    public int getCurrentIndex() {
        return currentIndex;
    }

    // gets the set of all completed step indices
    public Set<Integer> getCompletedSteps() {
        return completedSteps;
    }
}
