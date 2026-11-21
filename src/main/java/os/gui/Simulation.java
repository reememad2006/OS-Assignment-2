package os.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Simulation extends VBox {
    Simulation(){
        Label title = new Label("Simulation");
        getChildren().add(title);
        setPadding(new Insets(20));
        setSpacing(15);

    }
}
