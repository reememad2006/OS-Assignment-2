package os.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class Dashboard extends VBox {
    Dashboard(){
        Label title = new Label("Dashboard");
        getChildren().add(title);
        setPadding(new Insets(20));
        setSpacing(15);

    }
}
