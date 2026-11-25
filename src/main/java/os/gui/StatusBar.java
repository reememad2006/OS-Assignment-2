package os.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class StatusBar extends VBox {
    StatusBar(){
        Label status = new Label("Ready");
        getChildren().add(status);
        setSpacing(20);
        setPadding(new Insets(10, 20, 10, 20));
    }
}
