package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.facade.Ritualfacade;
import sa.edu.kau.fcit.cpit252.project.languages.LanguageManager;
import sa.edu.kau.fcit.cpit252.project.theme.ThemeManager;

import java.util.List;

public class RoadmapScreen
        implements ThemeManager.ThemeObserver, LanguageManager.LanguageObserver {

    private final Ritualfacade facade;
    private final ThemeManager theme = ThemeManager.getInstance();
    private final LanguageManager lang = LanguageManager.getInstance();
    private Stage stage;

    public RoadmapScreen(Ritualfacade facade) {
        this.facade = facade;
    }

    public void show(Stage stage) {
        this.stage = stage;
        theme.addObserver(this);
        lang.addObserver(this);

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: " + theme.backgroundColor() + ";");

        // --- Top Bar ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: " + theme.topBarColor() +
                "; -fx-background-radius: 0 0 25 25;");
        topBar.setSpacing(15);

        Button backBtn = new Button("←");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                "-fx-font-size: 20px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            unsubscribe();
            try { new RitualSelectionMenu().start(stage); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        Button startBtn = new Button(lang.t("btn.start"));
        startBtn.setStyle(
                "-fx-background-color: " + theme.accentColor() + "; -fx-text-fill: white; " +
                        "-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 12; " +
                        "-fx-padding: 8 14 8 14; -fx-cursor: hand;");

        Button themeBtn = new Button("🌓");
        themeBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 10; -fx-cursor: hand;");
        themeBtn.setOnAction(e -> theme.toggle());

        Button langBtn = new Button(lang.t("btn.lang.toggle"));
        langBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10; " +
                "-fx-cursor: hand;");
        langBtn.setOnAction(e -> lang.toggle());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String ritualKey = facade.getRitualName().equalsIgnoreCase("Hajj")
                ? "ritual.hajj" : "ritual.umrah";
        Label title = new Label(lang.t(ritualKey));
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        topBar.getChildren().addAll(backBtn, startBtn, themeBtn, langBtn, spacer, title);

        // --- Progress Bar ---
        VBox progressBox = new VBox(8);
        progressBox.setPadding(new Insets(15, 20, 5, 20));
        progressBox.setAlignment(Pos.CENTER_LEFT);

        double progress = facade.getProgressPercentage() / 100.0;
        Label progressLabel = new Label(
                lang.t("label.progress") + (int) facade.getProgressPercentage() + "%");
        progressLabel.setStyle("-fx-text-fill: " + theme.primaryTextColor() +
                "; -fx-font-size: 14px; -fx-font-weight: bold;");

        ProgressBar progressBar = new ProgressBar(progress);
        progressBar.setStyle("-fx-accent: " + theme.accentColor() + ";");
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setMinHeight(12);

        progressBox.getChildren().addAll(progressLabel, progressBar);

        // --- Steps List ---
        VBox stepsList = new VBox(10);
        stepsList.setPadding(new Insets(10, 20, 20, 20));

        List<String> steps = facade.getAllSteps();
        for (int i = 0; i < steps.size(); i++) {
            final int index = i;
            HBox stepRow = buildStepRow(i, steps.get(i));
            stepRow.setOnMouseClicked(e -> {
                unsubscribe();
                facade.jumpToStep(index);
                new StepDetailScreen(facade, index).show(stage);
            });
            stepsList.getChildren().add(stepRow);
        }

        ScrollPane scrollPane = new ScrollPane(stepsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + theme.backgroundColor() +
                "; -fx-background-color: " + theme.backgroundColor() + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        startBtn.setOnAction(e -> {
            unsubscribe();
            facade.jumpToStep(0);
            new StepDetailScreen(facade, 0).show(stage);
        });

        root.getChildren().addAll(topBar, progressBox, scrollPane);

        Scene scene = new Scene(root, 360, 740);
        stage.setTitle("Roadmap");
        stage.setScene(scene);
        stage.show();
    }

    private HBox buildStepRow(int index, String stepName) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 15, 12, 15));
        row.setStyle("-fx-background-color: " + theme.cardColor() +
                "; -fx-background-radius: 12; -fx-cursor: hand;");

        boolean done = facade.isStepCompleted(index);

        Label numLabel = new Label(done ? "✓" : String.valueOf(index + 1));
        numLabel.setStyle(
                "-fx-background-color: " + (done ? theme.accentColor() : theme.topBarColor()) + "; " +
                        "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-min-width: 30px; -fx-min-height: 30px; " +
                        "-fx-max-width: 30px; -fx-max-height: 30px; " +
                        "-fx-background-radius: 15; -fx-alignment: center;");

        Label nameLabel = new Label(stepName);
        nameLabel.setStyle("-fx-text-fill: " + theme.primaryTextColor() + "; -fx-font-size: 14px;");
        nameLabel.setNodeOrientation(
                lang.isArabic() ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        row.getChildren().addAll(numLabel, nameLabel);
        return row;
    }

    @Override
    public void onThemeChanged(ThemeManager.Theme newTheme) {
        unsubscribe();
        show(stage);
    }

    @Override
    public void onLanguageChanged(LanguageManager.Language newLanguage) {
        unsubscribe();
        show(stage);
    }

    private void unsubscribe() {
        theme.removeObserver(this);
        lang.removeObserver(this);
    }
}