package os.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Process extends VBox {
    Process(){
        Label title = new Label("Process");
        getChildren().add(title);
        setPadding(new Insets(20));
        setSpacing(15);

    }
}
