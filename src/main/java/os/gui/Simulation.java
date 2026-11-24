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

public class Simulation extends VBox {
    Simulation(ObservableList<Process> processes){
        Label title = new Label("Simulation");
        metricsTable.setPrefHeight(250);
        metricsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        metricsTable.getColumns().addAll(
                idColumn, CompleteColumn, WaitingColumn, ResponseColumn, TurnaroundColumn
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
        CompleteColumn.setCellValueFactory(new PropertyValueFactory<>("completionTime"));
        WaitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        ResponseColumn.setCellValueFactory(new PropertyValueFactory<>("responseTime"));
        TurnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        averagesLabel.setWrapText(true);
        textarea.setEditable(false);
        getChildren().addAll(title, comboBox, textField, quantumField, runButton, textarea, metricsTable, averagesLabel);
        setPadding(new Insets(20));
        setSpacing(15);
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
            textarea.setText(String.join("\n", result.getTimeline()));
            metricsTable.setItems(javafx.collections.FXCollections.observableArrayList(result.getProcesses()));
            textarea.setEditable(false);
            averagesLabel.setText("Average Waiting Time: " + String.format("%.2f", result.getAverageWaitingTime()) +
                    "\nAverage Turnaround: " + String.format("%.2f", result.getAverageTurnaroundTime())
                    + "\nAverage Response: " + String.format("%.2f", result.getAverageResponseTime()));
        });
    }
    private Result result;
    ComboBox<String> comboBox = new ComboBox<>();
    TextField textField = new TextField();
    TextField quantumField = new TextField();
    Button runButton = new Button("Run Simulation");
    TextArea textarea = new TextArea();
    TableView<Process> metricsTable = new TableView<>();
    TableColumn<Process, String> idColumn = new TableColumn<>("Process");
    TableColumn<Process, Integer> CompleteColumn = new TableColumn<>("Complete Time");
    TableColumn<Process, Integer> WaitingColumn = new TableColumn<>("Waiting Time");
    TableColumn<Process, Integer> ResponseColumn = new TableColumn<>("Response");
    TableColumn<Process, Integer> TurnaroundColumn = new TableColumn<>("Turnaroung");
    Label averagesLabel = new Label();
}
