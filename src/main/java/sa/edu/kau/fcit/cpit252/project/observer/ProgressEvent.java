package sa.edu.kau.fcit.cpit252.project.observer;


public enum ProgressEvent {
    STEP_CHANGED,      // user moved to a different step
    STEP_COMPLETED,    // user marked the current step as done
    PROGRESS_RESET,    // progress was cleared
    RITUAL_STARTED     // a fresh ritual was started or resumed
}