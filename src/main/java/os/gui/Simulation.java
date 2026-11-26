
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;

public class Simulation extends VBox {

    Simulation(ObservableList<Process> processes, Dashboard dashboard){

        Label title = new Label("Simulation");

        metricsTable.setPrefHeight(250);
        textarea.setPrefHeight(100);
        demotionArea.setPrefHeight(100);

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

        HBox algorithmRow = new HBox(10,
                new Label("Algorithm:"), comboBox
        );

        HBox parametersRow = new HBox(10,
                textField, quantumField, runButton, clearButton
        );

        VBox settingsBox = new VBox(12,
                settingsTitle, algorithmRow, parametersRow
        );

        settingsBox.setPadding(new Insets(15));

        // Timeline Section
        Label timelineTitle = new Label("Execution Timeline");

        VBox timelineBox = new VBox(10,
                timelineTitle, textarea
        );

        timelineBox.setPadding(new Insets(15));

        // Demotion Section
        Label demotionTitle = new Label("Demotion Matrix");

        VBox demotionBox = new VBox(10,
                demotionTitle, demotionArea
        );

        demotionBox.setPadding(new Insets(15));

        // Metrics Section
        Label metricsTitle = new Label("Performance Metrics");

        VBox metricsBox = new VBox(10,
                metricsTitle, metricsTable, averagesLabel
        );

        metricsBox.setPadding(new Insets(15));

        textarea.setPrefRowCount(4);
        demotionArea.setPrefRowCount(4);

        textarea.setWrapText(true);
        demotionArea.setWrapText(true);

        metricsTable.setPrefHeight(220);
        metricsTable.setMinHeight(180);

        settingsBox.setMaxWidth(Double.MAX_VALUE);
        timelineBox.setMaxWidth(Double.MAX_VALUE);
        demotionBox.setMaxWidth(Double.MAX_VALUE);
        metricsBox.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(title, settingsBox, timelineBox, demotionBox, metricsBox);

        setFillWidth(true);
        setPadding(new Insets(15));
        setSpacing(10);

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

            dashboard.updateResults(result.getAverageWaitingTime(), result.getAverageTurnaroundTime(), result.getAverageResponseTime());

            textarea.setText(String.join("\n", result.getTimeline()));

            demotionArea.setText(String.join("\n\n", result.getTierMatrices()));

            metricsTable.setItems(javafx.collections.FXCollections.observableArrayList(result.getProcesses()));

            textarea.setEditable(false);

            averagesLabel.setText(
                    "Average Waiting: " + String.format("%.2f", result.getAverageWaitingTime()) +
                            "     |     Average Turnaround: " + String.format("%.2f", result.getAverageTurnaroundTime()) +
                            "     |     Average Response: " + String.format("%.2f", result.getAverageResponseTime())
            );

        });

        clearButton.setOnAction(event -> {
            textarea.clear();
            demotionArea.clear();
            metricsTable.getItems().clear();
            averagesLabel.setText("");
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
