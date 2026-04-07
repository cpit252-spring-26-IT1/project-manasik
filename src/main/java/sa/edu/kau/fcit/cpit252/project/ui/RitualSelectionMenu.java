package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RitualSelectionMenu extends Application {

    @Override
    public void start(Stage stage) {
        // --- Root Container (The dark background) ---
        VBox root = new VBox(40);
        root.setStyle("-fx-background-color: #0B121E;"); // Dark Navy
        root.setAlignment(Pos.TOP_CENTER);

        // --- Top Navigation Bar ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setSpacing(100);
        topBar.setAlignment(Pos.CENTER_LEFT);
        // Rounded bottom corners for the header like in your Figma
        topBar.setStyle("-fx-background-color: #007A53; -fx-background-radius: 0 0 25 25;");

        Label brandName = new Label("Manasik");
        brandName.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        topBar.getChildren().add(brandName);

        // --- Logo and Center Text ---
        VBox centerContent = new VBox(15);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(50, 0, 20, 0));

        // Placeholder for Kaaba Icon (Emoji for now, or use an ImageView)
        Label kaabaIcon = new Label("🕋");
        kaabaIcon.setStyle("-fx-font-size: 80px;");

        Label mainTitle = new Label("Manasik");
        mainTitle.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-family: 'System';");

        centerContent.getChildren().addAll(kaabaIcon, mainTitle);

        // --- Buttons ---
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        String buttonStyle =
                "-fx-background-color: #00A676; " + // Bright Green
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 280px; " +
                        "-fx-min-height: 55px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-cursor: hand;";

        Button hajjBtn = new Button("Hajj");
        hajjBtn.setStyle(buttonStyle);

        Button umrahBtn = new Button("Umrah");
        umrahBtn.setStyle(buttonStyle);

        buttonContainer.getChildren().addAll(hajjBtn, umrahBtn);

        // --- Assemble Everything ---
        root.getChildren().addAll(topBar, centerContent, buttonContainer);

        Scene scene = new Scene(root, 360, 740); // Standard phone ratio
        stage.setTitle("Manasik Selection");
        stage.setScene(scene);
        stage.show();

        // --- CLICK LOGIC (Matching to other classes later) ---
        umrahBtn.setOnAction(e -> handleSelection("Umrah"));
        hajjBtn.setOnAction(e -> handleSelection("Hajj"));
    }

    private void handleSelection(String type) {
        System.out.println("Selected: " + type);
        // This is where we will call Member #1's Factory later!
    }
}