package sa.edu.kau.fcit.cpit252.project.model;

import sa.edu.kau.fcit.cpit252.project.languages.LanguageManager.Language;
import sa.edu.kau.fcit.cpit252.project.languages.RitualContent;

import java.util.List;

public class Hajj implements Ritual {

    private static final String KEY = "hajj";

    @Override
    public String getName() {
        return "Hajj";
    }

    @Override
    public List<String> getSteps(Language lang) {
        return RitualContent.loadStepNames(KEY, lang);
    }

    @Override
    public List<String> getDetails(Language lang) {
        return RitualContent.loadStepDetails(KEY, lang);
    }
}