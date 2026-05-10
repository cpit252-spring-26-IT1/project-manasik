package sa.edu.kau.fcit.cpit252.project.theme;

import java.util.ArrayList;
import java.util.List;


public class ThemeManager {

    public enum Theme { DARK, LIGHT }

    public interface ThemeObserver {
        void onThemeChanged(Theme newTheme);
    }

    private static final ThemeManager INSTANCE = new ThemeManager();

    private Theme current = Theme.DARK;
    private final List<ThemeObserver> observers = new ArrayList<>();

    private ThemeManager() {}

    public static ThemeManager getInstance() {
        return INSTANCE;
    }

    public Theme getTheme() { return current; }
    public boolean isDark() { return current == Theme.DARK; }

    public void toggle() {
        current = (current == Theme.DARK) ? Theme.LIGHT : Theme.DARK;
        notifyObservers();
    }

    public void addObserver(ThemeObserver o) {
        if (o != null && !observers.contains(o)) observers.add(o);
    }

    public void removeObserver(ThemeObserver o) { observers.remove(o); }

    private void notifyObservers() {
        for (ThemeObserver o : new ArrayList<>(observers)) {
            o.onThemeChanged(current);
        }
    }

    // ============== COLOR PALETTE PER THEME ==============

    public String backgroundColor()    { return isDark() ? "#0B121E" : "#F5F7FA"; }
    public String cardColor()          { return isDark() ? "#152033" : "#FFFFFF"; }
    public String primaryTextColor()   { return isDark() ? "white"   : "#0B121E"; }
    public String secondaryTextColor() { return isDark() ? "#d8dde6" : "#3a4252"; }
    public String accentColor()        { return "#00A676"; } // brand green stays
    public String topBarColor()        { return "#007A53"; } // brand green stays
    public String borderColor()        { return isDark() ? "#1f2d44" : "#dfe3eb"; }
}