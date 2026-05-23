package sa.edu.kau.fcit.cpit252.project.factory;

import org.junit.jupiter.api.Test;
import sa.edu.kau.fcit.cpit252.project.model.Hajj;
import sa.edu.kau.fcit.cpit252.project.model.Ritual;
import sa.edu.kau.fcit.cpit252.project.model.Umrah;
import sa.edu.kau.fcit.cpit252.project.strategy.CounterStrategy;
import sa.edu.kau.fcit.cpit252.project.strategy.CounterStrategyResolver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RitualFactory (Factory Pattern — Stage 1)
 * and CounterStrategyResolver (Strategy Pattern — Stage 3).
 */
class RitualFactoryAndStrategyTest {

    // ══════════════════════════════════════════════════════════════════════════
    // RitualFactory — Factory Pattern
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void getRitual_returnsHajjInstance_forHajjType() {
        Ritual ritual = RitualFactory.getRitual("Hajj");
        assertNotNull(ritual);
        assertInstanceOf(Hajj.class, ritual);
    }

    @Test
    void getRitual_returnsUmrahInstance_forUmrahType() {
        Ritual ritual = RitualFactory.getRitual("Umrah");
        assertNotNull(ritual);
        assertInstanceOf(Umrah.class, ritual);
    }

    @Test
    void getRitual_isCaseInsensitive() {
        assertInstanceOf(Hajj.class,  RitualFactory.getRitual("hajj"));
        assertInstanceOf(Hajj.class,  RitualFactory.getRitual("HAJJ"));
        assertInstanceOf(Umrah.class, RitualFactory.getRitual("umrah"));
        assertInstanceOf(Umrah.class, RitualFactory.getRitual("UMRAH"));
    }

    @Test
    void getRitual_returnsNull_forUnknownType() {
        assertNull(RitualFactory.getRitual("Salah"));
    }

    @Test
    void getRitual_returnsNull_forNullInput() {
        assertNull(RitualFactory.getRitual(null));
    }

    @Test
    void getRitual_returnsNull_forEmptyString() {
        assertNull(RitualFactory.getRitual(""));
    }

    @Test
    void hajjRitual_hasCorrectName() {
        Ritual ritual = RitualFactory.getRitual("Hajj");
        assertNotNull(ritual);
        assertEquals("Hajj", ritual.getName());
    }

    @Test
    void umrahRitual_hasCorrectName() {
        Ritual ritual = RitualFactory.getRitual("Umrah");
        assertNotNull(ritual);
        assertEquals("Umrah", ritual.getName());
    }

    // ══════════════════════════════════════════════════════════════════════════
    // CounterStrategyResolver — Strategy Pattern
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void resolve_returnsTawafStrategy_forArabicTawafStep() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("طواف القدوم");
        assertTrue(strategy.hasCounter());
        assertEquals(7, strategy.getMaxCount());
    }

    @Test
    void resolve_returnsTawafStrategy_forExactArabicTawaf() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("الطواف");
        assertTrue(strategy.hasCounter());
    }

    @Test
    void resolve_returnsSaiStrategy_forArabicSaiStep() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("السعي بين الصفا والمروة");
        assertTrue(strategy.hasCounter());
        assertEquals(7, strategy.getMaxCount());
    }

    @Test
    void resolve_returnsTawafStrategy_forEnglishTawafStep() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("Tawaf al-Qudum (Arrival Tawaf)");
        assertTrue(strategy.hasCounter());
        assertEquals(7, strategy.getMaxCount());
    }

    @Test
    void resolve_returnsSaiStrategy_forEnglishSaiStep() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("Sa'i between Safa and Marwah");
        assertTrue(strategy.hasCounter());
        assertEquals(7, strategy.getMaxCount());
    }

    @Test
    void resolve_returnsNoCounterStrategy_forOrdinaryStep() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("Ihram");
        assertFalse(strategy.hasCounter());
        assertEquals(0, strategy.getMaxCount());
    }

    @Test
    void resolve_returnsNoCounterStrategy_forNull() {
        CounterStrategy strategy = CounterStrategyResolver.resolve(null);
        assertFalse(strategy.hasCounter());
    }

    @Test
    void resolve_returnsNoCounterStrategy_forEmptyString() {
        CounterStrategy strategy = CounterStrategyResolver.resolve("");
        assertFalse(strategy.hasCounter());
    }
}