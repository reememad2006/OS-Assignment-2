package edu.cu.os241.gui;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class StatusBar extends VBox {
    StatusBar(){
        Label status = new Label("Ready");
        getChildren().add(status);
    }
}
