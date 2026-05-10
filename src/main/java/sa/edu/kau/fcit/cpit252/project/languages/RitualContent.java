package sa.edu.kau.fcit.cpit252.project.languages;

import sa.edu.kau.fcit.cpit252.project.languages.LanguageManager.Language;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class RitualContent {

    // Returns the list of step names for a ritual in the given language.
    public static List<String> loadStepNames(String ritualKey, Language lang) {
        return loadList(ritualKey, lang, "name");
    }

    // Returns the list of step details for a ritual in the given language.
    public static List<String> loadStepDetails(String ritualKey, Language lang) {
        return loadList(ritualKey, lang, "details");
    }

    private static List<String> loadList(String ritualKey, Language lang, String suffix) {
        Properties props = loadProperties(ritualKey, lang);
        List<String> result = new ArrayList<>();
        int i = 0;
        while (true) {
            String value = props.getProperty("step." + i + "." + suffix);
            if (value == null) break;
            result.add(value);
            i++;
        }
        return result;
    }

    private static Properties loadProperties(String ritualKey, Language lang) {
        String langCode = (lang == Language.ENGLISH) ? "en" : "ar";
        String path = "/languages/" + ritualKey + "_" + langCode + ".properties";

        Properties props = new Properties();
        try (InputStream in = RitualContent.class.getResourceAsStream(path)) {
            if (in == null) {
                System.err.println("Missing resource: " + path);
                return props;
            }
            // UTF-8 explicitly so Arabic characters load correctly
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Failed to load " + path + ": " + e.getMessage());
        }
        return props;
    }
}