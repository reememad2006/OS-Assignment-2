
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
        Label subtitle = new Label(
                "Monitor your CPU scheduling experiments."
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

        processesButton.setOnAction(e -> mainLayout.ProcessView());
        simulationButton.setOnAction(e -> mainLayout.SimulationView());
        analyticsButton.setOnAction(e -> mainLayout.AnalyticsView());

        HBox navigation = new HBox(10,
                processesButton, simulationButton, analyticsButton
        );

        getChildren().addAll(
                title, subtitle,
                new Label("Overview"),
                statistics,
                new Label("Quick Actions"),
                navigation
        );

        setPadding(new Insets(30));
        setSpacing(20);

        updateCounts(processes, testCases);
    }

    private VBox createCard(String title, Label value) {
        Label cardTitle = new Label(title);

        VBox card = new VBox(12, cardTitle, value);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setMinHeight(110);

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
