package os.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import os.scheduler.Process;

public class ProcessPage extends VBox {
    ProcessPage(ObservableList<Process> processes){
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
            Process process = new Process(idField.getText(), Integer.parseInt(arrivalField.getText()), Integer.parseInt(burstField.getText()), Integer.parseInt(priorityField.getText()));
            processes.add(process);
        });
        getChildren().addAll(title, idField,arrivalField, burstField,priorityField, runProcessPage, table);
        setPadding(new Insets(20));
        setSpacing(15);
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
}
