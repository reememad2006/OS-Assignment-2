package os.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Analytics extends VBox {
    Analytics(){
        Label title = new Label("Analytics");
        getChildren().add(title);
        setPadding(new Insets(20));
        setSpacing(15);
    }
}
