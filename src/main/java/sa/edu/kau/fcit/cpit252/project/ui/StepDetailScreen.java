package sa.edu.kau.fcit.cpit252.project.ui;

import javafx.application.Platform;
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
import sa.edu.kau.fcit.cpit252.project.observer.ProgressEvent;
import sa.edu.kau.fcit.cpit252.project.observer.ProgressObserver;
import sa.edu.kau.fcit.cpit252.project.strategy.CounterStrategy;
import sa.edu.kau.fcit.cpit252.project.strategy.CounterStrategyResolver;
import sa.edu.kau.fcit.cpit252.project.theme.ThemeManager;

public class StepDetailScreen
        implements ProgressObserver, ThemeManager.ThemeObserver {

    private final Ritualfacade facade;
    private final int stepIndex;
    private final ThemeManager theme = ThemeManager.getInstance();
    private final CounterStrategy counterStrategy;

    private int counterValue = 0;
    private Stage stage;
    private Button doneBtn;
    private Label counterValueLabel;

    public StepDetailScreen(Ritualfacade facade, int stepIndex) {
        this.facade = facade;
        this.stepIndex = stepIndex;
        // Strategy chosen ONCE per step based on the step name
        this.counterStrategy = CounterStrategyResolver.resolve(facade.getStep(stepIndex));
    }

    public void show(Stage stage) {
        this.stage = stage;
        theme.addObserver(this);
        facade.addObserver(this);

        int totalSteps = facade.getAllSteps().size();
        int stepNumber = stepIndex + 1;

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: " + theme.backgroundColor() + ";");

        // ============== TOP BAR ==============
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: " + theme.topBarColor() +
                "; -fx-background-radius: 0 0 25 25;");
        topBar.setSpacing(15);

        Button backBtn = new Button("←");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
                "-fx-font-size: 22px; -fx-font-weight: bold; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            unsubscribe();
            new RoadmapScreen(facade).show(stage);
        });

        Button themeBtn = new Button("🌓");
        themeBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                "-fx-font-size: 13px; -fx-background-radius: 10; -fx-cursor: hand;");
        themeBtn.setOnAction(e -> theme.toggle());

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Label titleLabel = new Label(facade.getRitualName());
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label stepCounter = new Label(stepNumber + " / " + totalSteps);
        stepCounter.setStyle(
                "-fx-background-color: rgba(255,255,255,0.18); -fx-text-fill: white; " +
                        "-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 5 12 5 12; " +
                        "-fx-background-radius: 12;");

        topBar.getChildren().addAll(backBtn, themeBtn, titleLabel, topSpacer, stepCounter);

        // ============== HEADER ==============
        VBox header = new VBox(15);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(35, 25, 25, 25));

        Label stepBadge = new Label(String.valueOf(stepNumber));
        stepBadge.setStyle(
                "-fx-background-color: " + theme.accentColor() + "; -fx-text-fill: white; " +
                        "-fx-font-size: 28px; -fx-font-weight: bold; " +
                        "-fx-min-width: 70px; -fx-min-height: 70px; " +
                        "-fx-max-width: 70px; -fx-max-height: 70px; " +
                        "-fx-background-radius: 35; -fx-alignment: center;");

        Label stepLabel = new Label("الخطوة " + stepNumber);
        stepLabel.setStyle("-fx-text-fill: " + theme.accentColor() +
                "; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label stepNameLabel = new Label(facade.getStep(stepIndex));
        stepNameLabel.setStyle("-fx-text-fill: " + theme.primaryTextColor() +
                "; -fx-font-size: 24px; -fx-font-weight: bold;");
        stepNameLabel.setWrapText(true);
        stepNameLabel.setAlignment(Pos.CENTER);
        stepNameLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        stepNameLabel.setMaxWidth(Double.MAX_VALUE);

        header.getChildren().addAll(stepBadge, stepLabel, stepNameLabel);

        // ============== DETAILS CARD ==============
        VBox detailsCard = buildDetailsCard();

        // ============== COUNTER CARD (Strategy pattern) ==============
        VBox counterCard = buildCounterCard();

        // ============== DONE BUTTON ==============
        doneBtn = buildDoneButton();
        HBox doneRow = new HBox(doneBtn);
        doneRow.setAlignment(Pos.CENTER);
        doneRow.setPadding(new Insets(10, 0, 0, 0));

        // ============== NAV BUTTONS ==============
        Button prevBtn = new Button("السابق ←");
        prevBtn.setStyle(
                "-fx-background-color: " + theme.cardColor() + "; " +
                        "-fx-text-fill: " + theme.primaryTextColor() + "; " +
                        "-fx-font-size: 14px; -fx-font-weight: bold; " +
                        "-fx-min-width: 130px; -fx-min-height: 45px; " +
                        "-fx-background-radius: 12; -fx-border-color: " + theme.accentColor() + "; " +
                        "-fx-border-radius: 12; -fx-border-width: 1; -fx-cursor: hand;");
        prevBtn.setDisable(stepIndex <= 0);
        prevBtn.setOnAction(e -> {
            unsubscribe();
            facade.jumpToStep(stepIndex - 1);
            new StepDetailScreen(facade, stepIndex - 1).show(stage);
        });

        Button nextBtn = new Button("→ التالي");
        nextBtn.setStyle(
                "-fx-background-color: " + theme.accentColor() + "; -fx-text-fill: white; " +
                        "-fx-font-size: 14px; -fx-font-weight: bold; " +
                        "-fx-min-width: 130px; -fx-min-height: 45px; " +
                        "-fx-background-radius: 12; -fx-cursor: hand;");
        nextBtn.setDisable(stepIndex >= totalSteps - 1);
        nextBtn.setOnAction(e -> {
            unsubscribe();
            facade.jumpToStep(stepIndex + 1);
            new StepDetailScreen(facade, stepIndex + 1).show(stage);
        });

        HBox navRow = new HBox(15, prevBtn, nextBtn);
        navRow.setAlignment(Pos.CENTER);
        navRow.setPadding(new Insets(5, 0, 0, 0));

        // ============== CONTENT ==============
        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 18, 25, 18));
        content.getChildren().add(header);
        content.getChildren().add(detailsCard);
        if (counterStrategy.hasCounter()) {
            content.getChildren().add(counterCard);
        }
        content.getChildren().addAll(doneRow, navRow);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + theme.backgroundColor() +
                "; -fx-background-color: " + theme.backgroundColor() + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(topBar, scrollPane);

        Scene scene = new Scene(root, 360, 740);
        stage.setTitle("Step Detail");
        stage.setScene(scene);
        stage.show();
    }

    private VBox buildDetailsCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20, 22, 22, 22));
        card.setStyle(
                "-fx-background-color: " + theme.cardColor() + "; " +
                        "-fx-background-radius: 18; " +
                        "-fx-border-color: " + theme.borderColor() + "; " +
                        "-fx-border-radius: 18; -fx-border-width: 1;");

        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Region accentBar = new Region();
        accentBar.setStyle("-fx-background-color: " + theme.accentColor() +
                "; -fx-background-radius: 2;");
        accentBar.setMinWidth(4);
        accentBar.setMaxWidth(4);
        accentBar.setMinHeight(18);

        Label detailsTitle = new Label("التفاصيل");
        detailsTitle.setStyle("-fx-text-fill: " + theme.primaryTextColor() +
                "; -fx-font-size: 16px; -fx-font-weight: bold;");
        detailsTitle.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        headerRow.getChildren().addAll(accentBar, detailsTitle, headerSpacer);

        Label detailsLabel = new Label(facade.getStepDetails(stepIndex));
        detailsLabel.setStyle(
                "-fx-text-fill: " + theme.secondaryTextColor() + "; " +
                        "-fx-font-size: 15px; -fx-line-spacing: 7px;");
        detailsLabel.setWrapText(true);
        detailsLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        detailsLabel.setMaxWidth(Double.MAX_VALUE);

        card.getChildren().addAll(headerRow, detailsLabel);
        return card;
    }

    /** Counter UI driven entirely by the CounterStrategy. */
    private VBox buildCounterCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(18, 22, 22, 22));
        card.setStyle(
                "-fx-background-color: " + theme.cardColor() + "; " +
                        "-fx-background-radius: 18; " +
                        "-fx-border-color: " + theme.borderColor() + "; " +
                        "-fx-border-radius: 18; -fx-border-width: 1;");
        card.setAlignment(Pos.CENTER);

        Label title = new Label(counterStrategy.getLabel());
        title.setStyle("-fx-text-fill: " + theme.primaryTextColor() +
                "; -fx-font-size: 16px; -fx-font-weight: bold;");
        title.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        counterValueLabel = new Label(counterValue + " / " + counterStrategy.getMaxCount());
        counterValueLabel.setStyle("-fx-text-fill: " + theme.accentColor() +
                "; -fx-font-size: 36px; -fx-font-weight: bold;");

        String btnStyle = "-fx-background-color: " + theme.accentColor() +
                "; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; " +
                "-fx-min-width: 55px; -fx-min-height: 55px; -fx-background-radius: 30; " +
                "-fx-cursor: hand;";

        Button minus = new Button("−");
        Button plus  = new Button("+");
        minus.setStyle(btnStyle);
        plus.setStyle(btnStyle);

        minus.setOnAction(e -> {
            if (counterValue > 0) {
                counterValue--;
                counterValueLabel.setText(counterValue + " / " + counterStrategy.getMaxCount());
            }
        });
        plus.setOnAction(e -> {
            if (counterValue < counterStrategy.getMaxCount()) {
                counterValue++;
                counterValueLabel.setText(counterValue + " / " + counterStrategy.getMaxCount());
                // Auto-mark step done when counter hits the max
                if (counterValue == counterStrategy.getMaxCount()
                        && !facade.isStepCompleted(stepIndex)) {
                    facade.completeCurrentStep();
                    // Observer callback (onProgressChanged) refreshes the Done button
                }
            }
        });

        HBox controls = new HBox(20, minus, counterValueLabel, plus);
        controls.setAlignment(Pos.CENTER);

        card.getChildren().addAll(title, controls);
        return card;
    }

    private Button buildDoneButton() {
        boolean alreadyDone = facade.isStepCompleted(stepIndex);
        Button btn = new Button(alreadyDone ? "✓ تم" : "تم");
        btn.setStyle(
                "-fx-background-color: " + (alreadyDone ? "#4a4a4a" : theme.accentColor()) + "; " +
                        "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; " +
                        "-fx-min-width: 280px; -fx-min-height: 50px; " +
                        "-fx-background-radius: 15; -fx-cursor: hand;");
        btn.setDisable(alreadyDone);
        btn.setOnAction(e -> facade.completeCurrentStep());
        return btn;
    }

    // ============== OBSERVER CALLBACKS ==============

    @Override
    public void onProgressChanged(ProgressEvent event, int currentIndex) {
        Platform.runLater(() -> {
            boolean done = facade.isStepCompleted(stepIndex);
            doneBtn.setText(done ? "✓ تم" : "تم");
            doneBtn.setDisable(done);
            doneBtn.setStyle(
                    "-fx-background-color: " + (done ? "#4a4a4a" : theme.accentColor()) + "; " +
                            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; " +
                            "-fx-min-width: 280px; -fx-min-height: 50px; " +
                            "-fx-background-radius: 15; -fx-cursor: hand;");
        });
    }

    @Override
    public void onThemeChanged(ThemeManager.Theme newTheme) {
        unsubscribe();
        show(stage);
    }

    private void unsubscribe() {
        theme.removeObserver(this);
        facade.removeObserver(this);
    }
}