package os.gui;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Header extends HBox {
    Header() {
        Label title = new Label("Cloud-Cluster CPU Scheduling");
        Label subtitle = new Label("CPU Scheduling");

        title.setStyle(
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FAF6F0;"
        );

        subtitle.setStyle(
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #E6D2C0;"
        );

        VBoxContainer container = new VBoxContainer(title, subtitle);

        getChildren().add(container);

        setPadding(new Insets(18, 25, 18, 25));
        setAlignment(Pos.CENTER_LEFT);

        // Header Background
        setStyle(
                        "-fx-background-color: #63483C;" +
                        "-fx-border-color: transparent transparent #806B5D transparent;" +
                        "-fx-border-width: 0 0 1px 0;"
        );
    }

    private static class VBoxContainer extends javafx.scene.layout.VBox {
        VBoxContainer(Label title, Label subtitle) {
            getChildren().addAll(title, subtitle);
        }
    }

}
