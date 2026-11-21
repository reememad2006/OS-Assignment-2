package os.gui;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Header extends HBox {
    Label title = new Label("cpu scheduling");
    Header() {
        getChildren().add(title);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
    }

}