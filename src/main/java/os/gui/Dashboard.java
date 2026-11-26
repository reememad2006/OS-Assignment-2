package os.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import os.scheduler.Process;

public class Dashboard extends VBox {

    private Label processCount = new Label();
    private Label testCaseCount = new Label();
    private Label waitingValue = new Label("Not run yet");
    private Label turnaroundValue = new Label("Not run yet");
    private Label responseTimeValue = new Label("Not run yet");

    Dashboard(MainLayout mainLayout,
              ObservableList<Process> processes,
              ObservableList<java.util.List<Process>> testCases) {

        Label title = new Label("Welcome to Cloud Scheduler");
        title.setStyle(
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        Label subtitle = new Label(
                "Monitor your CPU scheduling experiments."
        );
        subtitle.setStyle(
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #947E6D;"
        );

        HBox statistics = new HBox(15);

        statistics.getChildren().addAll(
                createCard("Total Processes", processCount),
                createCard("Saved Test Cases", testCaseCount),
                createCard("Average Waiting", waitingValue),
                createCard("Average Turnaround", turnaroundValue),
                createCard("Average Response", responseTimeValue)
        );

        Button processesButton = new Button("Manage Processes");
        Button simulationButton = new Button("Run Simulation");
        Button analyticsButton = new Button("View Analytics");

        processesButton.setStyle(
                        "-fx-background-color: #A36F55;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 20px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        simulationButton.setStyle(
                        "-fx-background-color: #63483C;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 20px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        analyticsButton.setStyle(
                        "-fx-background-color: #947E6D;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px 20px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        processesButton.setOnAction(e -> mainLayout.ProcessView());
        simulationButton.setOnAction(e -> mainLayout.SimulationView());
        analyticsButton.setOnAction(e -> mainLayout.AnalyticsView());

        HBox navigation = new HBox(10,
                processesButton, simulationButton, analyticsButton
        );

        navigation.setAlignment(Pos.CENTER_LEFT);

        Label overviewTitle = new Label("Overview");
        overviewTitle.setStyle(
                        "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        Label quickActionsTitle = new Label("Quick Actions");
        quickActionsTitle.setStyle(
                        "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        getChildren().addAll(
                title, subtitle,
                overviewTitle,
                statistics,
                quickActionsTitle,
                navigation
        );

        setPadding(new Insets(30));
        setSpacing(20);

        setStyle("-fx-background-color: #FBF7F2;");

        updateCounts(processes, testCases);
    }

    private VBox createCard(String title, Label value) {
        Label cardTitle = new Label(title);

        cardTitle.setStyle(
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #947E6D;"
        );

        value.setStyle(
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #A36F55;"
        );

        VBox card = new VBox(12, cardTitle, value);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setMinHeight(110);

        card.setStyle(
                        "-fx-background-color: #F0E3D8;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: #E2C6B2;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-border-width: 1px;"
        );

        return card;
    }

    void updateCounts(ObservableList<Process> processes,
                      ObservableList<java.util.List<Process>> testCases) {

        processCount.setText(String.valueOf(processes.size()));
        testCaseCount.setText(String.valueOf(testCases.size()));
    }

    void updateResults(double waiting, double turnaround, double responseTime) {
        waitingValue.setText(String.format("%.2f", waiting));
        turnaroundValue.setText(String.format("%.2f", turnaround));
        responseTimeValue.setText(String.format("%.2f", responseTime));
    }

    void clearResults() {

        waitingValue.setText("0.00");
        turnaroundValue.setText("0.00");
        responseTimeValue.setText("0.00");

    }
}
