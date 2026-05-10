package sa.edu.kau.fcit.cpit252.project.service;

import sa.edu.kau.fcit.cpit252.project.observer.ProgressEvent;
import sa.edu.kau.fcit.cpit252.project.observer.ProgressObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RitualProgressManager {

    private int currentIndex;
    private final Set<Integer> completedSteps;
    private final List<ProgressObserver> observers;

    public RitualProgressManager() {
        this.currentIndex = 0;
        this.completedSteps = new HashSet<>();
        this.observers = new ArrayList<>();
    }

    // ============== OBSERVER REGISTRATION ==============

    public void addObserver(ProgressObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ProgressObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(ProgressEvent event) {
        // copy to avoid ConcurrentModificationException
        List<ProgressObserver> snapshot = new ArrayList<>(observers);
        for (ProgressObserver o : snapshot) {
            o.onProgressChanged(event, currentIndex);
        }
    }

    // ============== CURRENT STEP ==============

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        notifyObservers(ProgressEvent.STEP_CHANGED);
    }

    public void goToNext() {
        currentIndex++;
        notifyObservers(ProgressEvent.STEP_CHANGED);
    }

    public void goToPrevious() {
        currentIndex--;
        notifyObservers(ProgressEvent.STEP_CHANGED);
    }

    // ============== COMPLETED STEPS ==============

    public Set<Integer> getCompletedSteps() {
        return completedSteps;
    }

    public void markStepDone(int index) {
        completedSteps.add(index);
        notifyObservers(ProgressEvent.STEP_COMPLETED);
    }

    public boolean isStepCompleted(int index) {
        return completedSteps.contains(index);
    }

    // ============== PROGRESS ==============

    public double getProgressPerecentage(int totalSteps) {
        if (totalSteps == 0) return 0.0;
        return ((double) completedSteps.size() / totalSteps) * 100.0;
    }

    // ============== RESET / RESTORE ==============

    public void reset() {
        currentIndex = 0;
        completedSteps.clear();
        notifyObservers(ProgressEvent.PROGRESS_RESET);
    }

    public void restoreState(int index, Set<Integer> completed) {
        this.currentIndex = index;
        this.completedSteps.clear();
        this.completedSteps.addAll(completed);
        notifyObservers(ProgressEvent.RITUAL_STARTED);
    }
}