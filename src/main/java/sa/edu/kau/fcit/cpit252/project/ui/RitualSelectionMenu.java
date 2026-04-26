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
import sa.edu.kau.fcit.cpit252.project.facade.Ritualfacade;

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
        // Use the Facade to start the ritual
        if (facade.startRitual(type)) {
            // Open the Roadmap screen
            new RoadmapScreen(facade).show(stage);
        }
    }
}