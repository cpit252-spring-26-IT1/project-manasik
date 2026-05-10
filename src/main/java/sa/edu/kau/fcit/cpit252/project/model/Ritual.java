package sa.edu.kau.fcit.cpit252.project.model;

import sa.edu.kau.fcit.cpit252.project.i18n.LanguageManager.Language;

import java.util.List;

public interface Ritual {
    /** English-only name used as the ritual identifier (e.g. "Hajj", "Umrah"). */
    String getName();

    /** Step names in the requested language. */
    List<String> getSteps(Language lang);

    /** Step details in the requested language. */
    List<String> getDetails(Language lang);

    // Backward compat — defaults to Arabic so older callers still work.
    default List<String> getSteps()   { return getSteps(Language.ARABIC); }
    default List<String> getDetails() { return getDetails(Language.ARABIC); }
}