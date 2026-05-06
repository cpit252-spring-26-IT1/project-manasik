package sa.edu.kau.fcit.cpit252.project.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProgressPersistence {

    private static final String FILE_NAME = ".manasik_progress.properties";

    // determines the file path across different operating systems
    private Path getFilePath() {
        String home = System.getProperty("user.home");
        return Paths.get(home, FILE_NAME);
    }

    // saves the current ritual state to a properties file
    public void save(String ritualName, int currentIndex, Set<Integer> completedSteps) {
        Properties props = new Properties();
        props.setProperty("ritualName", ritualName == null ? "" : ritualName);
        props.setProperty("currentIndex", String.valueOf(currentIndex));
        String completed = completedSteps.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        props.setProperty("completedSteps", completed);

        try (OutputStream out = Files.newOutputStream(getFilePath())) {
            props.store(out, "Manasik saved progress");
        } catch (IOException e) {
            System.err.println("Could not save progress: " + e.getMessage());
        }
    }

   // loads saved progress from the disk if the file exists
    public SavedProgress load() {
        Path file = getFilePath();
        if (!Files.exists(file)) return null;

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Could not load progress: " + e.getMessage());
            return null;
        }

        String name = props.getProperty("ritualName", "");
        if (name.isEmpty()) return null;

        int index = 0;
        try {
            index = Integer.parseInt(props.getProperty("currentIndex", "0"));
        } catch (NumberFormatException ignored) { }

        Set<Integer> completed = new HashSet<>();
        String completedStr = props.getProperty("completedSteps", "");
        if (!completedStr.isEmpty()) {
            for (String s : completedStr.split(",")) {
                try {
                    completed.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException ignored) { }
            }
        }

        return new SavedProgress(name, index, completed);
    }

    // deletes the progress file from the disk to reset the user progress
    public void clear() {
        try {
            Files.deleteIfExists(getFilePath());
        } catch (IOException ignored) { }
    }

}
