package os.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class StatusBar extends VBox {
    StatusBar(){
        Label status = new Label("Ready");

        status.setStyle(
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FAF6F0;"
        );

        getChildren().add(status);
        setSpacing(20);
        setPadding(new Insets(10, 20, 10, 20));

        setStyle(
                        "-fx-background-color: #63483C;" +
                        "-fx-border-color: #806B5D;" +
                        "-fx-border-width: 1px 0 0 0;"
        );
    }
}