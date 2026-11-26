package os.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import os.scheduler.*;
import os.scheduler.Process;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class Simulation extends VBox {
    Simulation(ObservableList<Process> processes, Dashboard dashboard){
        Label title = new Label("Simulation");

        metricsTable.setPrefHeight(150);
        textarea.setPrefHeight(75);
        demotionArea.setPrefHeight(75);
        metricsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        metricsTable.getColumns().addAll(
                idColumn,
                ArrivalColumn,
                CompleteColumn,
                TurnaroundColumn,
                WaitingColumn,
                ResponseColumn
        );

        textField.setPromptText("Context Switch Time");
        quantumField.setPromptText("Time Quantum");

        quantumField.setVisible(false);
        quantumField.setManaged(false);

        comboBox.getItems().addAll("SRTF", "ACS");

        comboBox.setOnAction(event -> {
            if(comboBox.getValue().equals("ACS")){
                quantumField.setVisible(true);
                quantumField.setManaged(true);
            }else{
                quantumField.setVisible(false);
                quantumField.setManaged(false);
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ArrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        CompleteColumn.setCellValueFactory(new PropertyValueFactory<>("completionTime"));
        TurnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        WaitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        ResponseColumn.setCellValueFactory(new PropertyValueFactory<>("responseTime"));

        textarea.setEditable(false);
        demotionArea.setEditable(false);

        // Simulation Settings
        Label settingsTitle = new Label("Simulation Settings");

        HBox algorithmRow = new HBox(8,
                new Label("Algorithm:"), comboBox
        );

        HBox parametersRow = new HBox(8,
                textField, quantumField, runButton, clearButton
        );

        VBox settingsBox = new VBox(8,
                settingsTitle, algorithmRow, parametersRow
        );

        settingsBox.setPadding(new Insets(10));

        // Timeline Section
        Label timelineTitle = new Label("Execution Timeline");

        VBox timelineBox = new VBox(7,
                timelineTitle, textarea
        );

        timelineBox.setPadding(new Insets(10));

        // Demotion Section
        Label demotionTitle = new Label("Demotion Matrix");

        VBox demotionBox = new VBox(7,
                demotionTitle, demotionArea
        );

        demotionBox.setPadding(new Insets(10));

        // Metrics Section
        Label metricsTitle = new Label("Performance Metrics");

        VBox metricsBox = new VBox(7,
                metricsTitle, metricsTable, averagesLabel
        );

        metricsBox.setPadding(new Insets(10));

        textarea.setPrefRowCount(3);
        demotionArea.setPrefRowCount(3);

        textarea.setWrapText(true);
        demotionArea.setWrapText(true);

        metricsTable.setPrefHeight(150);
        metricsTable.setMinHeight(120);

        settingsBox.setMaxWidth(Double.MAX_VALUE);
        timelineBox.setMaxWidth(Double.MAX_VALUE);
        demotionBox.setMaxWidth(Double.MAX_VALUE);
        metricsBox.setMaxWidth(Double.MAX_VALUE);

        title.setStyle(
                        "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        settingsTitle.setStyle(
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        timelineTitle.setStyle(
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        demotionTitle.setStyle(
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        metricsTitle.setStyle(
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #63483C;"
        );

        String sectionStyle =
                        "-fx-background-color: #F0E3D8;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: #E2C6B2;" +
                        "-fx-border-radius: 12px;";

        settingsBox.setStyle(sectionStyle);
        timelineBox.setStyle(sectionStyle);
        demotionBox.setStyle(sectionStyle);
        metricsBox.setStyle(sectionStyle);

        String inputStyle =
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-text-fill: #4B342B;" +
                        "-fx-prompt-text-fill: #947E6D;" +
                        "-fx-font-size: 12px;" +
                        "-fx-border-color: #B58B72;" +
                        "-fx-border-radius: 7px;" +
                        "-fx-background-radius: 7px;" +
                        "-fx-padding: 7px;";

        textField.setStyle(inputStyle);
        quantumField.setStyle(inputStyle);
        comboBox.setStyle(inputStyle);

        String buttonStyle =
                        "-fx-background-color: #A36F55;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px 12px;" +
                        "-fx-cursor: hand;";

        runButton.setStyle(buttonStyle);

        clearButton.setStyle(
                        "-fx-background-color: #947E6D;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px 12px;" +
                        "-fx-cursor: hand;"
        );

        String areaStyle =
                        "-fx-control-inner-background: #FFFFFF;" +
                        "-fx-text-fill: #4B342B;" +
                        "-fx-font-size: 12px;" +
                        "-fx-highlight-fill: #D6BBA8;" +
                        "-fx-highlight-text-fill: #4B342B;" +
                        "-fx-border-color: #E2C6B2;" +
                        "-fx-border-radius: 7px;" +
                        "-fx-background-radius: 7px;";

        textarea.setStyle(areaStyle);
        demotionArea.setStyle(areaStyle);

        metricsTable.setStyle(
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-control-inner-background: #FFFFFF;" +
                        "-fx-control-inner-background-alt: #F0E3D8;" +
                        "-fx-table-cell-border-color: #E2C6B2;" +
                        "-fx-table-header-border-color: #B58B72;" +
                        "-fx-font-size: 11px;" +
                        "-fx-border-color: #B58B72;" +
                        "-fx-border-radius: 7px;" +
                        "-fx-background-radius: 7px;"
        );

        averagesLabel.setStyle(
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #4B342B;" +
                        "-fx-background-color: #D6BBA8;" +
                        "-fx-padding: 10px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #B58B72;" +
                        "-fx-border-radius: 8px;"
        );

        // Hide Averages before running the simulation
        averagesLabel.setVisible(false);
        averagesLabel.setManaged(false);

        setStyle("-fx-background-color: #FBF7F2;");

        getChildren().addAll(title, settingsBox, timelineBox, demotionBox, metricsBox);

        setFillWidth(true);
        setPadding(new Insets(10));
        setSpacing(7);

        runButton.setOnAction(event -> {
            int contextSwitch = Integer.parseInt(textField.getText());
            Scheduler scheduler;

            if(comboBox.getValue().equals("SRTF")){
                scheduler = new PreemptiveSJF(contextSwitch);
            }else{
                int quantum = Integer.parseInt(quantumField.getText());
                scheduler = new ACSScheduler(quantum, contextSwitch);
            }

            result = scheduler.run(processes);

            dashboard.updateResults(
                    result.getAverageWaitingTime(),
                    result.getAverageTurnaroundTime(),
                    result.getAverageResponseTime()
            );

            textarea.setText(String.join("\n", result.getTimeline()));

            demotionArea.setText(String.join("\n\n", result.getTierMatrices()));

            metricsTable.setItems(
                    javafx.collections.FXCollections.observableArrayList(result.getProcesses())
            );

            textarea.setEditable(false);

            averagesLabel.setText(
                    "Average Waiting: " + String.format("%.2f", result.getAverageWaitingTime()) +
                            "     |     Average Turnaround: " + String.format("%.2f", result.getAverageTurnaroundTime()) +
                            "     |     Average Response: " + String.format("%.2f", result.getAverageResponseTime())
            );

            // Show Averages after running the simulation
            averagesLabel.setVisible(true);
            averagesLabel.setManaged(true);

        });

        clearButton.setOnAction(event -> {
            textarea.clear();
            demotionArea.clear();
            metricsTable.getItems().clear();
            averagesLabel.setText("");

            // Hide Averages after clearing the simulation
            averagesLabel.setVisible(false);
            averagesLabel.setManaged(false);

            dashboard.clearResults();
            result = null;
        });

    }

    private Result result;
    ComboBox<String> comboBox = new ComboBox<>();
    TextField textField = new TextField();
    TextField quantumField = new TextField();
    Button runButton = new Button("Run Simulation");
    Button clearButton = new Button("Clear Simulation");
    TextArea textarea = new TextArea();
    TableView<Process> metricsTable = new TableView<>();
    TableColumn<Process, String> idColumn = new TableColumn<>("Process");
    TableColumn<Process, Integer> ArrivalColumn = new TableColumn<>("Arrival Time");
    TableColumn<Process, Integer> CompleteColumn = new TableColumn<>("Finish Time");
    TableColumn<Process, Integer> TurnaroundColumn = new TableColumn<>("Turnaround Time");
    TableColumn<Process, Integer> WaitingColumn = new TableColumn<>("Waiting Time");
    TableColumn<Process, Integer> ResponseColumn = new TableColumn<>("Response Time");

    Label averagesLabel = new Label();

    TextArea demotionArea = new TextArea();
}