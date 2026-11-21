package os.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
public class Simulation extends VBox {
    Simulation(){
        Label title = new Label("Simulation");
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
        getChildren().addAll(title, comboBox, textField, quantumField, runButton);
        setPadding(new Insets(20));
        setSpacing(15);
    }
    ComboBox<String> comboBox = new ComboBox<>();
    TextField textField = new TextField();
    TextField quantumField = new TextField();
    Button runButton = new Button("Run Simulation");
}