package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.facade.Ritualfacade;
import sa.edu.kau.fcit.cpit252.project.languages.LanguageManager;
import sa.edu.kau.fcit.cpit252.project.theme.ThemeManager;

import java.util.Optional;

public class RitualSelectionMenu extends Application {

    private final Ritualfacade facade = new Ritualfacade();
    private final ThemeManager theme = ThemeManager.getInstance();
    private final LanguageManager lang = LanguageManager.getInstance();

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(40);
        root.setStyle("-fx-background-color: " + theme.backgroundColor() + ";");
        root.setAlignment(Pos.TOP_CENTER);

        // --- Top Bar ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: " + theme.topBarColor() +
                "; -fx-background-radius: 0 0 25 25;");

        // App name switches with language
        String brandText = lang.isArabic() ? "مناسك" : "Manasik";
        Label brandName = new Label(brandText);
        brandName.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Theme toggle
        Button themeBtn = new Button("🌓");
        themeBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 10; -fx-cursor: hand;");
        themeBtn.setOnAction(e -> {
            theme.toggle();
            try { start(stage); } catch (Exception ex) { ex.printStackTrace(); }
        });

        // Language toggle
        Button langBtn = new Button(lang.t("btn.lang.toggle"));
        langBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10; " +
                "-fx-cursor: hand;");
        langBtn.setOnAction(e -> {
            lang.toggle();
            try { start(stage); } catch (Exception ex) { ex.printStackTrace(); }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(brandName, spacer, themeBtn, langBtn);

        // --- Center Logo ---
        VBox centerContent = new VBox(15);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(50, 0, 20, 0));

        Label kaabaIcon = new Label("🕋");
        kaabaIcon.setStyle("-fx-font-size: 80px;");

        // Center title also switches with language
        String centerText = lang.isArabic() ? "مناسك" : "Manasik";
        Label mainTitle = new Label(centerText);
        mainTitle.setStyle("-fx-text-fill: " + theme.primaryTextColor() + "; -fx-font-size: 28px;");

        centerContent.getChildren().addAll(kaabaIcon, mainTitle);

        // --- Ritual Buttons ---
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        String buttonStyle =
                "-fx-background-color: " + theme.accentColor() + "; -fx-text-fill: white; " +
                        "-fx-font-size: 16px; -fx-font-weight: bold; " +
                        "-fx-min-width: 280px; -fx-min-height: 55px; " +
                        "-fx-background-radius: 15; -fx-cursor: hand;";

        Button hajjBtn = new Button(lang.t("ritual.hajj"));
        hajjBtn.setStyle(buttonStyle);
        Button umrahBtn = new Button(lang.t("ritual.umrah"));
        umrahBtn.setStyle(buttonStyle);

        buttonContainer.getChildren().addAll(hajjBtn, umrahBtn);

        root.getChildren().addAll(topBar, centerContent, buttonContainer);

        Scene scene = new Scene(root, 360, 740);
        stage.setTitle(lang.isArabic() ? "مناسك" : "Manasik");
        stage.setScene(scene);
        stage.show();

        hajjBtn.setOnAction(e -> handleSelection("Hajj", stage));
        umrahBtn.setOnAction(e -> handleSelection("Umrah", stage));
    }

    private void handleSelection(String type, Stage stage) {
        if (facade.hasSavedProgress(type)) {
            int savedStep = facade.getSavedStepIndex(type) + 1;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(lang.t("dialog.resume.title"));
            alert.setHeaderText(lang.t("dialog.resume.header"));
            alert.setContentText(
                    lang.t("dialog.resume.content") + savedStep + lang.t("dialog.resume.suffix"));

            ButtonType resumeBtn  = new ButtonType(lang.t("btn.resume"));
            ButtonType restartBtn = new ButtonType(lang.t("btn.restart"));
            ButtonType cancelBtn  = new ButtonType(lang.t("btn.cancel"),
                    ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().setAll(resumeBtn, restartBtn, cancelBtn);

            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isEmpty() || choice.get() == cancelBtn) return;

            if (choice.get() == resumeBtn) {
                if (facade.resumeRitual(type)) {
                    new RoadmapScreen(facade).show(stage);
                }
                return;
            }
            facade.clearSavedProgress();
        }

        if (facade.startRitual(type)) {
            new RoadmapScreen(facade).show(stage);
        }
    }
}