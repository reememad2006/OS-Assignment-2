
package os.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import os.scheduler.Process;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ProcessPage extends VBox {

    ProcessPage(ObservableList<Process> processes, ObservableList<List<Process>> testCases){

        Label title = new Label("Process");

        idField.setPromptText("PID");
        arrivalField.setPromptText("ArrivalTime");
        burstField.setPromptText("BurstTime");
        priorityField.setPromptText("Priority");

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        secondNameCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        thirdNameCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        fourthNameCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        table.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol, fourthNameCol);
        table.setItems(processes);

        runProcessPage.setOnAction(event -> {

            Process process = new Process(
                    idField.getText(),
                    Integer.parseInt(arrivalField.getText()),
                    Integer.parseInt(burstField.getText()),
                    Integer.parseInt(priorityField.getText())
            );

            processes.add(process);
        });

        deleteButton.setOnAction(event -> {
            processes.clear();
        });

        saveButton.setOnAction(event -> {

            ArrayList<Process> saved = new ArrayList<>();

            for (Process p : processes) {

                Process copy = new Process(
                        p.getId(),
                        p.getArrivalTime(),
                        p.getBurstTime(),
                        p.getPriority()
                );

                saved.add(copy);
            }

            testCases.add(saved);
        });

        clearTestCasesButton.setOnAction(event -> {
            testCases.clear();
        });

        // Page subtitle
        Label subtitle = new Label(
                "Add and manage processes for CPU scheduling."
        );

        // Input section
        Label inputTitle = new Label("Process Information");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(15);
        inputGrid.setVgap(12);

        inputGrid.add(new Label("Process ID"), 0, 0);
        inputGrid.add(idField, 1, 0);

        inputGrid.add(new Label("Arrival Time"), 2, 0);
        inputGrid.add(arrivalField, 3, 0);

        inputGrid.add(new Label("Burst Time"), 0, 1);
        inputGrid.add(burstField, 1, 1);

        inputGrid.add(new Label("Priority"), 2, 1);
        inputGrid.add(priorityField, 3, 1);

        // Actions
        runProcessPage.setText("Add Process");
        saveButton.setText("Save Test Case");
        deleteButton.setText("Clear Processes");
        clearTestCasesButton.setText("Clear Test Cases");

        HBox buttons = new HBox(12,
                runProcessPage, deleteButton, saveButton, clearTestCasesButton
        );

        // Table section
        Label tableTitle = new Label("Processes List");

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        table.setPrefHeight(350);

        getChildren().addAll(
                title,
                subtitle,
                inputTitle,
                inputGrid,
                buttons,
                tableTitle,
                table
        );

        setPadding(new Insets(25));
        setSpacing(15);
        setFillWidth(true);
    }

    TableView<Process> table = new TableView<>();

    TableColumn<Process, String> firstNameCol = new TableColumn<>("PID");
    TableColumn<Process, Integer> secondNameCol = new TableColumn<>("ArrivalTime");
    TableColumn<Process, Integer> thirdNameCol = new TableColumn<>("BurstTime");
    TableColumn<Process, Integer> fourthNameCol = new TableColumn<>("Priority");

    TextField idField = new TextField();
    TextField arrivalField = new TextField();
    TextField burstField = new TextField();
    TextField priorityField = new TextField();

    Button runProcessPage = new Button("run");
    Button deleteButton = new Button("delete");
    Button saveButton = new Button("save");
    Button clearTestCasesButton = new Button("Clear Test Cases");
}
