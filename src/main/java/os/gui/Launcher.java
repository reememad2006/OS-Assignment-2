package os.gui;
import javafx.scene.*;
import javafx.application.Application;
import javafx.stage.Stage;
public class Launcher extends Application{
    @Override
    public void start(Stage stage) {
        stage.setTitle("cpu scheduling");
        stage.setWidth(1200);
        stage.setHeight(750);
        MainLayout mainlayout = new MainLayout();
        Scene s = new Scene(mainlayout, 1200, 750);
        stage.setScene(s);
        stage.show();
    }
}
