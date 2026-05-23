package sa.edu.kau.fcit.cpit252.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RitualValidator.
 * Tests every public method for both valid and boundary/invalid inputs.
 */
class RitualValidatorTest {

    private RitualValidator validator;

    @BeforeEach
    void setUp() {
        validator = new RitualValidator();
    }

    // ── canGoNext ─────────────────────────────────────────────────────────────

    @Test
    void canGoNext_returnsTrue_whenNotOnLastStep() {
        assertTrue(validator.canGoNext(0, 5));
        assertTrue(validator.canGoNext(3, 5));
    }

    @Test
    void canGoNext_returnsFalse_whenOnLastStep() {
        assertFalse(validator.canGoNext(4, 5)); // index 4 = last in 5-step ritual
    }

    @Test
    void canGoNext_returnsFalse_whenNegativeIndex() {
        assertFalse(validator.canGoNext(-1, 5));
    }

    @Test
    void canGoNext_returnsFalse_whenSingleStepRitual() {
        assertFalse(validator.canGoNext(0, 1));
    }

    // ── canGoPrevious ─────────────────────────────────────────────────────────

    @Test
    void canGoPrevious_returnsTrue_whenNotOnFirstStep() {
        assertTrue(validator.canGoPrevious(1));
        assertTrue(validator.canGoPrevious(4));
    }

    @Test
    void canGoPrevious_returnsFalse_whenOnFirstStep() {
        assertFalse(validator.canGoPrevious(0));
    }

    @Test
    void canGoPrevious_returnsFalse_whenNegativeIndex() {
        assertFalse(validator.canGoPrevious(-1));
    }

    // ── isValidJump ───────────────────────────────────────────────────────────

    @Test
    void isValidJump_returnsTrue_forValidIndices() {
        assertTrue(validator.isValidJump(0, 9));
        assertTrue(validator.isValidJump(8, 9));
    }

    @Test
    void isValidJump_returnsFalse_forNegativeIndex() {
        assertFalse(validator.isValidJump(-1, 9));
    }

    @Test
    void isValidJump_returnsFalse_forIndexEqualToTotal() {
        assertFalse(validator.isValidJump(9, 9));
    }

    @Test
    void isValidJump_returnsFalse_forIndexBeyondTotal() {
        assertFalse(validator.isValidJump(100, 9));
    }

    // ── canMarkDone ───────────────────────────────────────────────────────────

    @Test
    void canMarkDone_returnsTrue_whenStepNotYetCompleted() {
        Set<Integer> completed = new HashSet<>();
        assertTrue(validator.canMarkDone(2, 9, completed));
    }

    @Test
     void canMarkDone_returnsFalse_whenStepAlreadyCompleted() {
        Set<Integer> completed = new HashSet<>(Set.of(2));
        assertFalse(validator.canMarkDone(2, 9, completed));
    }

    @Test
    void canMarkDone_returnsFalse_whenIndexOutOfRange() {
        Set<Integer> completed = new HashSet<>();
        assertFalse(validator.canMarkDone(-1, 9, completed));
        assertFalse(validator.canMarkDone(9, 9, completed));
    }

    // ── canUndo ───────────────────────────────────────────────────────────────

    @Test
    void canUndo_returnsTrue_whenStepIsCompleted() {
        Set<Integer> completed = new HashSet<>(Set.of(3));
        assertTrue(validator.canUndo(3, 9, completed));
    }

    @Test
    void canUndo_returnsFalse_whenStepNotCompleted() {
        Set<Integer> completed = new HashSet<>();
        assertFalse(validator.canUndo(3, 9, completed));
    }

    @Test
    void canUndo_returnsFalse_whenIndexOutOfRange() {
        Set<Integer> completed = new HashSet<>(Set.of(9));
        assertFalse(validator.canUndo(9, 9, completed));
        assertFalse(validator.canUndo(-1, 9, completed));
    }
}