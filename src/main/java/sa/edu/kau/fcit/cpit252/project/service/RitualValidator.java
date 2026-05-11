package sa.edu.kau.fcit.cpit252.project.service;

import java.util.Set;

public class RitualValidator {

    // Can move forward only if not on last step
    public boolean canGoNext(int currentIndex, int totalSteps) {
        return currentIndex >= 0 && currentIndex < totalSteps - 1;
    }

    // Can move back only if not on the first step
    public boolean canGoPrevious(int currentIndex) {
        return currentIndex > 0;
    }

    // A jump is valid only if the target index is in range
    public boolean isValidJump(int targetIndex, int totalSteps) {
        return targetIndex >= 0 && targetIndex < totalSteps;
    }

    // Can mark as done only if not already completed and index is valid
    public boolean canMarkDone(int currentIndex, int totalSteps, Set<Integer> completedSteps) {
       if (currentIndex < 0 || currentIndex >= totalSteps) return false;
       return !completedSteps.contains(currentIndex);
    }

    // can undo only if the step is currently completed and index is valid
    public boolean canUndo(int currentIndex, int totalSteps, Set<Integer> completedSteps) {
        if (currentIndex < 0 || currentIndex >= totalSteps) return false;
        return completedSteps.contains(currentIndex);
    }

}
