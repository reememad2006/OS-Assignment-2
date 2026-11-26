package os.gui;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.NumberAxis;
import os.scheduler.Process;
import java.util.List;
import javafx.scene.chart.XYChart;
import os.scheduler.ACSScheduler;
import os.scheduler.Result;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;

public class Analytics extends VBox {
    Analytics(ObservableList<List<Process>> testCases) {

        // Main Background Style
        setStyle("-fx-background-color: #FBF7F2;");

        updateAnalytics(testCases);

        testCases.addListener((ListChangeListener<List<Process>>) change -> {
            updateAnalytics(testCases);
        });
    }

    private void updateAnalytics(ObservableList<List<Process>> testCases) {

        // Clear old charts and content
        lineChart.getData().clear();
        contextChart.getData().clear();
        getChildren().clear();

        Label title = new Label("Analytics");
        title.setStyle(
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        Label subtitle = new Label(
                "Analyze how Quantum and Context Switch affect scheduling performance."
        );

        subtitle.setStyle(
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: #947E6D;"
        );

        // Check if there are no saved test cases
        if (testCases.isEmpty()) {
            Label noData = new Label(
                    "No data available. Please save test cases first."
            );

            noData.setStyle(
                    "-fx-font-size: 16px;" +
                            "-fx-text-fill: #947E6D;" +
                            "-fx-background-color: #F0E3D8;" +
                            "-fx-padding: 20px;" +
                            "-fx-background-radius: 10px;"
            );

            VBox messageBox = new VBox(noData);
            messageBox.setAlignment(javafx.geometry.Pos.CENTER);
            messageBox.setMinHeight(350);

            VBox content = new VBox(
                    15,
                    title,
                    subtitle,
                    messageBox
            );

            content.setPadding(new Insets(25));
            content.setStyle("-fx-background-color: #FBF7F2;");

            getChildren().add(content);

            return;
        }

        lineChart.setTitle("Impact of Time Quantum");
        contextChart.setTitle("Impact of Context Switch");

        lineChart.setAnimated(false);
        contextChart.setAnimated(false);

        lineChart.setCreateSymbols(true);
        contextChart.setCreateSymbols(true);

        lineChart.setLegendVisible(true);
        contextChart.setLegendVisible(true);

        lineChart.setMinHeight(400);
        contextChart.setMinHeight(400);

        lineChart.setPrefHeight(450);
        contextChart.setPrefHeight(450);

        // Chart Styling
        lineChart.setStyle(
                        "-fx-background-color: #FBF7F2;" +
                        "-fx-padding: 10px;" +
                        "-fx-text-fill: #63483C;"
        );

        contextChart.setStyle(
                        "-fx-background-color: #FBF7F2;" +
                        "-fx-padding: 10px;" +
                        "-fx-text-fill: #63483C;"
        );

        lineChart.lookup(".chart-plot-background");
        contextChart.lookup(".chart-plot-background");

        for (List<Process> testCase : testCases) {

            XYChart.Series<Number, Number> waiting = new XYChart.Series<>();
            XYChart.Series<Number, Number> turnaround = new XYChart.Series<>();

            waiting.setName("Test Case " + (testCases.indexOf(testCase) + 1) + " - waiting");
            turnaround.setName("Test Case " + (testCases.indexOf(testCase) + 1) + " - turnaround");

            for (int quantum = 1; quantum <= 5; quantum++) {

                ACSScheduler scheduler = new ACSScheduler(quantum, 1);
                Result result = scheduler.run(copyProcesses(testCase));

                waiting.getData().add(new XYChart.Data<>(quantum, result.getAverageWaitingTime()));
                turnaround.getData().add(new XYChart.Data<>(quantum, result.getAverageTurnaroundTime()));
            }

            lineChart.getData().addAll(waiting, turnaround);
        }

        for (List<Process> testCase : testCases) {

            XYChart.Series<Number, Number> cswaiting = new XYChart.Series<>();
            XYChart.Series<Number, Number> csturnaround = new XYChart.Series<>();

            int caseNumber = testCases.indexOf(testCase) + 1;

            cswaiting.setName("Test Case " + caseNumber + " - Waiting");
            csturnaround.setName("Test Case " + caseNumber + " - Turnaround");

            for (int cs = 0; cs <= 4; cs++) {

                ACSScheduler scheduler = new ACSScheduler(3, cs);
                Result result = scheduler.run(copyProcesses(testCase));

                cswaiting.getData().add(new XYChart.Data<>(cs, result.getAverageWaitingTime()));
                csturnaround.getData().add(new XYChart.Data<>(cs, result.getAverageTurnaroundTime()));
            }

            contextChart.getData().addAll(cswaiting, csturnaround);
        }

        x_axis.setLabel("Time Quantum");
        y_axis.setLabel("Average Time");

        contextXaxis.setLabel("Context Switch Time");
        contextYaxis.setLabel("Average Time");

        // Axis Styling
        x_axis.setStyle("-fx-tick-label-fill: #63483C; -fx-text-fill: #63483C;");
        y_axis.setStyle("-fx-tick-label-fill: #63483C; -fx-text-fill: #63483C;");

        contextXaxis.setStyle("-fx-tick-label-fill: #63483C; -fx-text-fill: #63483C;");
        contextYaxis.setStyle("-fx-tick-label-fill: #63483C; -fx-text-fill: #63483C;");

        Label quantumTitle = new Label("Quantum Analysis");
        quantumTitle.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        Label contextTitle = new Label("Context Switch Analysis");
        contextTitle.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        VBox quantumSection = new VBox(10, quantumTitle, lineChart);
        VBox contextSection = new VBox(10, contextTitle, contextChart);

        quantumSection.setPadding(new Insets(15));
        contextSection.setPadding(new Insets(15));

        quantumSection.setStyle(
                        "-fx-background-color: #F0E3D8;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: #E2C6B2;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-border-width: 1px;"
        );

        contextSection.setStyle(
                        "-fx-background-color: #F0E3D8;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: #E2C6B2;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-border-width: 1px;"
        );

        VBox content = new VBox(
                15,
                title,
                subtitle,
                quantumSection,
                contextSection
        );

        content.setPadding(new Insets(25));
        content.setFillWidth(true);
        content.setStyle("-fx-background-color: #FBF7F2;");

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setStyle(
                        "-fx-background: #FBF7F2;" +
                        "-fx-background-color: #FBF7F2;" +
                        "-fx-border-color: transparent;"
        );

        getChildren().add(scrollPane);

        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
    }

    NumberAxis x_axis = new NumberAxis();
    NumberAxis y_axis = new NumberAxis();

    NumberAxis contextXaxis = new NumberAxis();
    NumberAxis contextYaxis = new NumberAxis();

    LineChart<Number, Number> lineChart = new LineChart<>(x_axis, y_axis);
    LineChart<Number, Number> contextChart = new LineChart<>(contextXaxis, contextYaxis);

    private List<Process> copyProcesses(List<Process> original) {
        List<Process> copies = new ArrayList<>();

        for (Process p : original) {
            copies.add(new Process(
                    p.getId(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
            ));
        }

        return copies;
    }
}