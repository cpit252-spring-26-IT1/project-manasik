package sa.edu.kau.fcit.cpit252.project.service;

import java.util.HashSet;
import java.util.Set;

public class RitualProgressManager {

    private int currentIndex;
    private final Set<Integer> completedSteps;

    public RitualProgressManager(){
        this.currentIndex = 0;
        this.completedSteps = new HashSet<>();
    }

    // Current Step
    public int getCurrentIndex(){
        return currentIndex;
    }

    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }

    public void goToNext() {
        currentIndex++;
    }

    public void goToPrevious(){
        currentIndex--;
    }

    // Completed steps
    public Set<Integer> getCompletedSteps() {
        return completedSteps;
    }

    public void markStepDone(int index){
        completedSteps.add(index);
    }

    public boolean isStepCompleted(int index){
        return completedSteps.contains(index);
    }

    // Progress
    public double getProgressPerecentage(int totalSteps) {
        if (totalSteps == 0) return 0.0;
        return ((double) completedSteps.size() / totalSteps) *100.0;
    }

    // Reset
    public void reset() {
        currentIndex = 0;
        completedSteps.clear();
    }

    // Used by persistence to restore state
    public void restoreState(int index, Set<Integer> completed) {
        this.currentIndex = index;
        this.completedSteps.clear();
        this.completedSteps.addAll(completed);
    }
}
