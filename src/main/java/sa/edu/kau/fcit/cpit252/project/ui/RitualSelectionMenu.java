package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.facade.Ritualfacade;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class RitualSelectionMenu extends Application {

    private final Ritualfacade facade = new Ritualfacade();

    @Override
    public void start(Stage stage) {
        // --- Root Container ---
        VBox root = new VBox(40);
        root.setStyle("-fx-background-color: #0B121E;");
        root.setAlignment(Pos.TOP_CENTER);

        // --- Top Navigation Bar ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setSpacing(100);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #007A53; -fx-background-radius: 0 0 25 25;");

        Label brandName = new Label("Manasik");
        brandName.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        topBar.getChildren().add(brandName);

        // --- Logo and Center Text ---
        VBox centerContent = new VBox(15);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(50, 0, 20, 0));

        Label kaabaIcon = new Label("🕋");
        kaabaIcon.setStyle("-fx-font-size: 80px;");

        Label mainTitle = new Label("Manasik");
        mainTitle.setStyle("-fx-text-fill: white; -fx-font-size: 28px;");

        centerContent.getChildren().addAll(kaabaIcon, mainTitle);

        // --- Buttons ---
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        String buttonStyle =
                "-fx-background-color: #00A676; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 280px; " +
                        "-fx-min-height: 55px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-cursor: hand;";

        Button hajjBtn = new Button("الحج");
        hajjBtn.setStyle(buttonStyle);

        Button umrahBtn = new Button("العمرة");
        umrahBtn.setStyle(buttonStyle);

        buttonContainer.getChildren().addAll(hajjBtn, umrahBtn);

        // --- Assemble ---
        root.getChildren().addAll(topBar, centerContent, buttonContainer);

        Scene scene = new Scene(root, 360, 740);
        stage.setTitle("Manasik");
        stage.setScene(scene);
        stage.show();

        // --- Button Actions ---
        hajjBtn.setOnAction(e -> handleSelection("Hajj", stage));
        umrahBtn.setOnAction(e -> handleSelection("Umrah", stage));
    }

    private void handleSelection(String type, Stage stage) {
        // Check if there's saved progress for this ritual (issue #19)
        if (facade.hasSavedProgress(type)) {
            int savedStep = facade.getSavedStepIndex(type) + 1;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("متابعة الرحلة");
            alert.setHeaderText("لديك تقدم محفوظ");
            alert.setContentText("هل تريد المتابعة من الخطوة " + savedStep + "؟");

            ButtonType resumeBtn = new ButtonType("متابعة");
            ButtonType restartBtn = new ButtonType("البدء من جديد");
            ButtonType cancelBtn = new ButtonType("إلغاء", ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().setAll(resumeBtn, restartBtn, cancelBtn);

            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isEmpty() || choice.get() == cancelBtn) return;

            if (choice.get() == resumeBtn) {
                if (facade.resumeRitual(type)) {
                    new RoadmapScreen(facade).show(stage);
                }
                return;
            }
            // Restart: clear saved progress and start fresh
            facade.clearSavedProgress();
        }

        // No saved progress (or user chose restart) → start fresh
        if (facade.startRitual(type)) {
            new RoadmapScreen(facade).show(stage);
        }
    }
}