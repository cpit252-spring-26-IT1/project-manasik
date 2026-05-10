package sa.edu.kau.fcit.cpit252.project.facade;

import sa.edu.kau.fcit.cpit252.project.factory.RitualFactory;
import sa.edu.kau.fcit.cpit252.project.model.Ritual;
import sa.edu.kau.fcit.cpit252.project.service.ProgressPersistence;
import sa.edu.kau.fcit.cpit252.project.service.RitualProgressManager;
import sa.edu.kau.fcit.cpit252.project.service.RitualValidator;
import sa.edu.kau.fcit.cpit252.project.service.SavedProgress;
import sa.edu.kau.fcit.cpit252.project.observer.ProgressObserver;

import java.util.List;

public class Ritualfacade {

    private Ritual ritual;
    private final RitualProgressManager progressManager;
    private final RitualValidator validator;
    private final ProgressPersistence persistence;

    public Ritualfacade() {
        this.progressManager = new RitualProgressManager();
        this.validator = new RitualValidator();
        this.persistence = new ProgressPersistence();
    }




    // UI screens call this to subscribe to progress changes.
    public void addObserver(ProgressObserver observer) {
        progressManager.addObserver(observer);
    }

    // UI screens call this when they are no longer visible.
    public void removeObserver(ProgressObserver observer) {
        progressManager.removeObserver(observer);
    }



    // When the user picks Hajj or Umrah
    public boolean startRitual(String type){
        ritual = RitualFactory.getRitual(type);
        return ritual != null;
    }

    // Return all steps for the RoadMap
    public List<String> getAllSteps(){
        if (ritual == null) return List.of();
        return ritual.getSteps();
    }

    // Return the name of the step
    public String getStep(int index) {
        if (ritual == null) return "";
        return ritual.getSteps().get(index);
    }

    // Return the description of the step
    public String getStepDetails(int index) {
        if (ritual == null) return "";
        List<String> details = ritual.getDetails();
        if (index < 0 || index >= details.size()) return "";
        return details.get(index);
    }

    // Return ritual name (Hajj or Umrah)
    public String getRitualName(){
        if (ritual == null) return"";
        return ritual.getName();
    }

    // Progress Tracking
    // Mark current step as done
    public boolean completeCurrentStep() {
        if (ritual == null) return false;
        int current = progressManager.getCurrentIndex();
        int total = ritual.getSteps().size();

        if (!validator.canMarkDone(current, total, progressManager.getCompletedSteps())) {
            return false;
        }
        progressManager.markStepDone(current);
        persist();
        return true;
    }

    // Check if step is completed
    public boolean isStepCompleted(int index) {
        return progressManager.isStepCompleted(index);
    }

    // Return progress perecentage
    public double getProgressPercentage() {
        if (ritual == null) return 0.0;
        return progressManager.getProgressPerecentage(ritual.getSteps().size());
    }


        public int getCurrentStepIndex() {
            return progressManager.getCurrentIndex();
        }

        public boolean goToNextStep() {
            if (ritual == null) return false;
            int current = progressManager.getCurrentIndex();
            int total = ritual.getSteps().size();
            if (!validator.canGoNext(current, total)) return false;
            progressManager.goToNext();
            persist();
            return true;
        }

        public boolean goToPreviousStep() {
            if (ritual == null) return false;
            if (!validator.canGoPrevious(progressManager.getCurrentIndex())) return false;
            progressManager.goToPrevious();
            persist();
            return true;
        }

        public boolean jumpToStep(int index) {
            if (ritual == null) return false;
            int total = ritual.getSteps().size();
            if (!validator.isValidJump(index, total)) return false;
            progressManager.setCurrentIndex(index);
            persist();
            return true;
        }

        public boolean canGoNext() {
            if (ritual == null) return false;
            return validator.canGoNext(progressManager.getCurrentIndex(), ritual.getSteps().size());
        }

        public boolean canGoPrevious() {
            if (ritual == null) return false;
            return validator.canGoPrevious(progressManager.getCurrentIndex());
        }


        // saves current ritual state to disk
        private void persist() {
        if (ritual == null) return;
        persistence.save(ritual.getName(),
                progressManager.getCurrentIndex(),
                progressManager.getCompletedSteps());
    }


        // restores ritual state from disk if it matches the type
        public boolean resumeRitual(String type) {
        if (!startRitual(type)) return false;
        SavedProgress saved = persistence.load();
        if (saved != null && saved.getRitualName().equalsIgnoreCase(ritual.getName())) {
            int total = ritual.getSteps().size();
            int idx = (saved.getCurrentIndex() >= 0 && saved.getCurrentIndex() < total)
                    ? saved.getCurrentIndex() : 0;
            progressManager.restoreState(idx, saved.getCompletedSteps());
            return true;
        }
        return false;
    }

       // checks if progress exists for the given ritual type
        public boolean hasSavedProgress(String type) {
        SavedProgress saved = persistence.load();
        return saved != null && saved.getRitualName().equalsIgnoreCase(type);
    }

        // gets the saved step index for a ritual
        public int getSavedStepIndex(String type) {
        SavedProgress saved = persistence.load();
        if (saved != null && saved.getRitualName().equalsIgnoreCase(type)) {
            return saved.getCurrentIndex();
        }
        return -1;
    }

        // deletes the saved progress file
        public void clearSavedProgress() {
        persistence.clear();
    }


    }



