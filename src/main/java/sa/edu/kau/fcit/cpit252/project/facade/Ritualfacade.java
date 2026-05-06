package sa.edu.kau.fcit.cpit252.project.facade;

import sa.edu.kau.fcit.cpit252.project.factory.RitualFactory;
import sa.edu.kau.fcit.cpit252.project.model.Ritual;
import sa.edu.kau.fcit.cpit252.project.service.RitualProgressManager;
import sa.edu.kau.fcit.cpit252.project.service.RitualValidator;

import java.util.List;

public class Ritualfacade {

    private Ritual ritual;
    private final RitualProgressManager progressManager;
    private final RitualValidator validator;

    public Ritualfacade() {
        this.progressManager = new RitualProgressManager();
        this.validator = new RitualValidator();
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

        // ===== NEW: Navigation (issue #18) =====

        public int getCurrentStepIndex() {
            return progressManager.getCurrentIndex();
        }

        public boolean goToNextStep() {
            if (ritual == null) return false;
            int current = progressManager.getCurrentIndex();
            int total = ritual.getSteps().size();
            if (!validator.canGoNext(current, total)) return false;
            progressManager.goToNext();
            return true;
        }

        public boolean goToPreviousStep() {
            if (ritual == null) return false;
            if (!validator.canGoPrevious(progressManager.getCurrentIndex())) return false;
            progressManager.goToPrevious();
            return true;
        }

        public boolean jumpToStep(int index) {
            if (ritual == null) return false;
            int total = ritual.getSteps().size();
            if (!validator.isValidJump(index, total)) return false;
            progressManager.setCurrentIndex(index);
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


    }


}
