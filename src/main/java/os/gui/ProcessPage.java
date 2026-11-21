package os.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProcessPage extends VBox {
    ProcessPage(){
        Label title = new Label("Process");
        table.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol, fourthNameCol);
        table.setItems(processes);
        getChildren().addAll(title, table);
        setPadding(new Insets(20));
        setSpacing(15);
    }

    ObservableList<Process> processes = FXCollections.observableArrayList();

    TableView<Process> table = new TableView<>();

    TableColumn<Process, String> firstNameCol = new TableColumn<>("PID");
    TableColumn<Process, String> secondNameCol = new TableColumn<>("ArrivalTime");
    TableColumn<Process, String> thirdNameCol = new TableColumn<>("BurstTime");
    TableColumn<Process, String> fourthNameCol = new TableColumn<>("Priority");

}