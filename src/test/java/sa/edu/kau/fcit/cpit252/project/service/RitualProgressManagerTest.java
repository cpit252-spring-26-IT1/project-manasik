package sa.edu.kau.fcit.cpit252.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sa.edu.kau.fcit.cpit252.project.observer.ProgressEvent;
import sa.edu.kau.fcit.cpit252.project.observer.ProgressObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RitualProgressManager.
 * Covers navigation, step completion, observer notifications, and state restore.
 */
class RitualProgressManagerTest {

    private RitualProgressManager manager;

    @BeforeEach
    void setUp() {
        manager = new RitualProgressManager();
    }

    // ── Initial state ─────────────────────────────────────────────────────────

    @Test
    void initialCurrentIndex_isZero() {
        assertEquals(0, manager.getCurrentIndex());
    }

    @Test
    void initialCompletedSteps_isEmpty() {
        assertTrue(manager.getCompletedSteps().isEmpty());
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    @Test
    void goToNext_incrementsCurrentIndex() {
        manager.goToNext();
        assertEquals(1, manager.getCurrentIndex());
    }

    @Test
    void goToPrevious_decrementsCurrentIndex() {
        manager.goToNext();
        manager.goToPrevious();
        assertEquals(0, manager.getCurrentIndex());
    }

    @Test
    void setCurrentIndex_updatesIndex() {
        manager.setCurrentIndex(5);
        assertEquals(5, manager.getCurrentIndex());
    }

    // ── Step completion ───────────────────────────────────────────────────────

    @Test
    void markStepDone_addsToCompletedSteps() {
        manager.markStepDone(2);
        assertTrue(manager.isStepCompleted(2));
    }

    @Test
    void undoStep_removesFromCompletedSteps() {
        manager.markStepDone(2);
        manager.undoStep(2);
        assertFalse(manager.isStepCompleted(2));
    }

    @Test
    void isStepCompleted_returnsFalse_forUntouchedStep() {
        assertFalse(manager.isStepCompleted(7));
    }

    // ── Progress percentage ───────────────────────────────────────────────────

    @Test
    void progressPercentage_isZero_whenNoStepsCompleted() {
        assertEquals(0.0, manager.getProgressPerecentage(9), 0.001);
    }

    @Test
    void progressPercentage_isCorrect_afterCompletingSteps() {
        manager.markStepDone(0);
        manager.markStepDone(1);
        // 2 out of 9 steps done ≈ 22.22 %
        assertEquals(22.22, manager.getProgressPerecentage(9), 0.01);
    }

    @Test
    void progressPercentage_isHundred_whenAllStepsDone() {
        for (int i = 0; i < 9; i++) manager.markStepDone(i);
        assertEquals(100.0, manager.getProgressPerecentage(9), 0.001);
    }

    @Test
    void progressPercentage_isZero_whenTotalStepsIsZero() {
        assertEquals(0.0, manager.getProgressPerecentage(0), 0.001);
    }

    // ── Reset ─────────────────────────────────────────────────────────────────

    @Test
    void reset_clearsCompletedStepsAndSetsIndexToZero() {
        manager.goToNext();
        manager.goToNext();
        manager.markStepDone(0);
        manager.markStepDone(1);

        manager.reset();

        assertEquals(0, manager.getCurrentIndex());
        assertTrue(manager.getCompletedSteps().isEmpty());
    }

    // ── Restore state ─────────────────────────────────────────────────────────

    @Test
    void restoreState_setsIndexAndCompletedSteps() {
        Set<Integer> previouslyDone = Set.of(0, 1, 2);
        manager.restoreState(3, previouslyDone);

        assertEquals(3, manager.getCurrentIndex());
        assertTrue(manager.isStepCompleted(0));
        assertTrue(manager.isStepCompleted(1));
        assertTrue(manager.isStepCompleted(2));
    }

    // ── Observer notification ─────────────────────────────────────────────────

    @Test
    void observer_isNotifiedOnStepChange() {
        List<ProgressEvent> received = new ArrayList<>();
        ProgressObserver obs = (event, idx) -> received.add(event);

        manager.addObserver(obs);
        manager.goToNext();

        assertEquals(1, received.size());
        assertEquals(ProgressEvent.STEP_CHANGED, received.get(0));
    }

    @Test
    void observer_isNotifiedOnStepCompleted() {
        List<ProgressEvent> received = new ArrayList<>();
        manager.addObserver((event, idx) -> received.add(event));

        manager.markStepDone(0);

        assertEquals(1, received.size());
        assertEquals(ProgressEvent.STEP_COMPLETED, received.get(0));
    }

    @Test
    void observer_isNotNotified_afterRemoval() {
        List<ProgressEvent> received = new ArrayList<>();
        ProgressObserver obs = (event, idx) -> received.add(event);

        manager.addObserver(obs);
        manager.removeObserver(obs);
        manager.goToNext();

        assertTrue(received.isEmpty());
    }

    @Test
    void observer_receivesCorrectCurrentIndex_onNavigation() {
        List<Integer> indices = new ArrayList<>();
        manager.addObserver((event, idx) -> indices.add(idx));

        manager.goToNext(); // currentIndex becomes 1
        manager.goToNext(); // currentIndex becomes 2

        assertEquals(List.of(1, 2), indices);
    }
}