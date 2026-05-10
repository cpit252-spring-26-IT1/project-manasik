package sa.edu.kau.fcit.cpit252.project.languages;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LanguageManager {
    // supported languages in the application
    public enum Language {
        ARABIC, ENGLISH
    }

    // components that need to react to language changes
    public interface LanguageObserver {
       void onLanguageChanged(Language newLanguage);
    }

    private static final LanguageManager INSTANCE = new LanguageManager();
    private Language current = Language.ARABIC;
    private final List<LanguageObserver> observers = new ArrayList<>();
    private final Map<String, String> arabic  = new HashMap<>();
    private final Map<String, String> english = new HashMap<>();

    // private consteuctor to initialize ui strings
    private LanguageManager() {
        loadStrings();
    }

    // returns the single instance of the LanguageManager
    public static LanguageManager getInstance() {
        return INSTANCE;
    }

    // get the active language
    public Language getLanguage() {
        return current;
    }

    // check if the current language is arabic
    public boolean isArabic() {
        return current == Language.ARABIC;
    }

    // switches between Arabic and English and notifies observers
    public void toggle() {
        current = (current == Language.ARABIC) ? Language.ENGLISH : Language.ARABIC;
        notifyObservers();
    }

    // registers a new observer listen for language changes
    public void addObserver(LanguageObserver o) {
        if (o != null && !observers.contains(o)) observers.add(o);
    }

    // unregisters an observer
    public void removeObserver(LanguageObserver o) {
        observers.remove(o);
    }
    // notifies all registered observers of a language change
    private void notifyObservers() {
        for (LanguageObserver o : new ArrayList<>(observers)) {
            o.onLanguageChanged(current);
        }
    }

    // translates a ui key based on the current language
    public String t(String key) {
        Map<String, String> table = isArabic() ? arabic : english;
        return table.getOrDefault(key, key);
    }

    // initializes the ui label maps for both languages
    private void loadStrings() {

        // Arabic ui labels
        arabic.put("ritual.hajj", "الحج");
        arabic.put("ritual.umrah", "العمرة");
        arabic.put("btn.start", "▶ البدأ");
        arabic.put("btn.next", "→ التالي");
        arabic.put("btn.previous", "السابق ←");
        arabic.put("btn.done", "تم");
        arabic.put("btn.done.completed", "✓ تم");
        arabic.put("btn.resume", "متابعة");
        arabic.put("btn.restart", "البدء من جديد");
        arabic.put("btn.cancel", "إلغاء");
        arabic.put("label.progress", "التقدم: ");
        arabic.put("label.details", "التفاصيل");
        arabic.put("label.step", "الخطوة ");
        arabic.put("dialog.resume.title", "متابعة الرحلة");
        arabic.put("dialog.resume.header", "لديك تقدم محفوظ");
        arabic.put("dialog.resume.content", "هل تريد المتابعة من الخطوة ");
        arabic.put("dialog.resume.suffix", "؟");
        arabic.put("btn.lang.toggle", "EN");

        // English ui labels
        english.put("ritual.hajj", "Hajj");
        english.put("ritual.umrah", "Umrah");
        english.put("btn.start", "▶ Start");
        english.put("btn.next", "Next →");
        english.put("btn.previous", "← Previous");
        english.put("btn.done", "Done");
        english.put("btn.done.completed", "✓ Done");
        english.put("btn.resume", "Resume");
        english.put("btn.restart", "Restart");
        english.put("btn.cancel", "Cancel");
        english.put("label.progress", "Progress: ");
        english.put("label.details", "Details");
        english.put("label.step", "Step ");
        english.put("dialog.resume.title", "Resume Journey");
        english.put("dialog.resume.header", "You have saved progress");
        english.put("dialog.resume.content", "Continue from step ");
        english.put("dialog.resume.suffix", "?");
        english.put("btn.lang.toggle", "ع");
    }
}