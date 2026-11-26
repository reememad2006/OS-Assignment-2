package os.gui;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Header extends HBox {
    Label title = new Label("cpu scheduling");
    Header() {
        Label title = new Label("Cloud-Cluster CPU Scheduling");
        Label subtitle = new Label("CPU Scheduling");
        VBoxContainer container = new VBoxContainer(title, subtitle);
        getChildren().add(container);
        setPadding(new Insets(18, 25, 18, 25));
        setAlignment(Pos.CENTER_LEFT);
    }
    private static class VBoxContainer extends javafx.scene.layout.VBox{
        VBoxContainer(Label title, Label subtitle){
            getChildren().addAll(title, subtitle);
        }
    }

}
