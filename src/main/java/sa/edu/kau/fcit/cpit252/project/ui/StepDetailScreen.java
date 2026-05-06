package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.facade.Ritualfacade;

public class StepDetailScreen {

    private final Ritualfacade facade;
    private final int stepIndex;

    public StepDetailScreen(Ritualfacade facade, int stepIndex) {
        this.facade = facade;
        this.stepIndex = stepIndex;
    }

    public void show(Stage stage) {
        int totalSteps = facade.getAllSteps().size();
        int stepNumber = stepIndex + 1;

        // ============== ROOT ==============
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #0B121E;");

        // ============== TOP BAR ==============
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #007A53; -fx-background-radius: 0 0 25 25;");
        topBar.setSpacing(15);

        Button backBtn = new Button("←");
        backBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 22px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand;");
        backBtn.setOnAction(e -> new RoadmapScreen(facade).show(stage));

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Label titleLabel = new Label(facade.getRitualName());
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Step counter pill (e.g. "3 / 9")
        Label stepCounter = new Label(stepNumber + " / " + totalSteps);
        stepCounter.setStyle(
                "-fx-background-color: rgba(255,255,255,0.18); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 5 12 5 12; " +
                        "-fx-background-radius: 12;");

        topBar.getChildren().addAll(backBtn, titleLabel, topSpacer, stepCounter);

        // ============== HEADER (Step Number Badge + Step Name) ==============
        VBox header = new VBox(15);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(35, 25, 25, 25));

        // Big circular step number badge
        Label stepBadge = new Label(String.valueOf(stepNumber));
        stepBadge.setStyle(
                "-fx-background-color: #00A676; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 28px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 70px; -fx-min-height: 70px; " +
                        "-fx-max-width: 70px; -fx-max-height: 70px; " +
                        "-fx-background-radius: 35; " +
                        "-fx-alignment: center;");

        Label stepLabel = new Label("الخطوة " + stepNumber);
        stepLabel.setStyle("-fx-text-fill: #00A676; -fx-font-size: 13px; -fx-font-weight: bold;");

        // Step name (Arabic title)
        Label stepNameLabel = new Label(facade.getStep(stepIndex));
        stepNameLabel.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold;");
        stepNameLabel.setWrapText(true);
        stepNameLabel.setAlignment(Pos.CENTER);
        stepNameLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        stepNameLabel.setMaxWidth(Double.MAX_VALUE);

        header.getChildren().addAll(stepBadge, stepLabel, stepNameLabel);

        // ============== DETAILS CARD ==============
        VBox detailsCard = new VBox(15);
        detailsCard.setPadding(new Insets(20, 22, 22, 22));
        detailsCard.setStyle(
                "-fx-background-color: #152033; " +
                        "-fx-background-radius: 18; " +
                        "-fx-border-color: #1f2d44; " +
                        "-fx-border-radius: 18; " +
                        "-fx-border-width: 1;");

        // "Details" section heading with accent line
        HBox detailsHeader = new HBox(10);
        detailsHeader.setAlignment(Pos.CENTER_LEFT);

        Region accentBar = new Region();
        accentBar.setStyle("-fx-background-color: #00A676; -fx-background-radius: 2;");
        accentBar.setMinWidth(4);
        accentBar.setMaxWidth(4);
        accentBar.setMinHeight(18);

        Label detailsTitle = new Label("التفاصيل");
        detailsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        detailsTitle.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        detailsHeader.getChildren().addAll(accentBar, detailsTitle, headerSpacer);

        // Details body text
        Label detailsLabel = new Label(facade.getStepDetails(stepIndex));
        detailsLabel.setStyle(
                "-fx-text-fill: #d8dde6; " +
                        "-fx-font-size: 15px; " +
                        "-fx-line-spacing: 7px;");
        detailsLabel.setWrapText(true);
        detailsLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        detailsLabel.setMaxWidth(Double.MAX_VALUE);

        detailsCard.getChildren().addAll(detailsHeader, detailsLabel);

        // ============== MARK AS DONE BUTTON ==============
        Button doneBtn = new Button();
        boolean alreadyDone = facade.isStepCompleted(stepIndex);
        doneBtn.setText(alreadyDone ? "✓ تم" : "تم");
        doneBtn.setStyle(
                "-fx-background-color: " + (alreadyDone ? "#4a4a4a" : "#00A676") + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 280px; " +
                        "-fx-min-height: 50px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-cursor: hand;");
        doneBtn.setDisable(alreadyDone);
        doneBtn.setOnAction(e -> {
            if (facade.completeCurrentStep()) {
                // Refresh screen to show new state
                new StepDetailScreen(facade, stepIndex).show(stage);
            }
        });

        HBox doneRow = new HBox(doneBtn);
        doneRow.setAlignment(Pos.CENTER);
        doneRow.setPadding(new Insets(10, 0, 0, 0));

        // ============== NEXT / PREVIOUS BUTTONS (issue #18) ==============
        Button prevBtn = new Button("السابق ←");
        prevBtn.setStyle(
                "-fx-background-color: #152033; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 130px; " +
                        "-fx-min-height: 45px; " +
                        "-fx-background-radius: 12; " +
                        "-fx-border-color: #00A676; " +
                        "-fx-border-radius: 12; " +
                        "-fx-border-width: 1; " +
                        "-fx-cursor: hand;");
        prevBtn.setDisable(stepIndex <= 0);
        prevBtn.setOnAction(e -> {
            facade.jumpToStep(stepIndex - 1);
            new StepDetailScreen(facade, stepIndex - 1).show(stage);
        });

        Button nextBtn = new Button("→ التالي");
        nextBtn.setStyle(
                "-fx-background-color: #00A676; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-min-width: 130px; " +
                        "-fx-min-height: 45px; " +
                        "-fx-background-radius: 12; " +
                        "-fx-cursor: hand;");
        nextBtn.setDisable(stepIndex >= totalSteps - 1);
        nextBtn.setOnAction(e -> {
            facade.jumpToStep(stepIndex + 1);
            new StepDetailScreen(facade, stepIndex + 1).show(stage);
        });

        HBox navRow = new HBox(15, prevBtn, nextBtn);
        navRow.setAlignment(Pos.CENTER);
        navRow.setPadding(new Insets(5, 0, 0, 0));

        // ============== CONTENT WRAPPER ==============
        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 18, 25, 18));
        content.getChildren().addAll(header, detailsCard, doneRow, navRow);

        // ============== SCROLLABLE ==============
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #0B121E; -fx-background-color: #0B121E;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // ============== ASSEMBLE ==============
        root.getChildren().addAll(topBar, scrollPane);

        Scene scene = new Scene(root, 360, 740);
        stage.setTitle("Step Detail");
        stage.setScene(scene);
        stage.show();
    }
}