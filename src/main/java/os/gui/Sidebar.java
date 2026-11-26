package os.gui;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
public class Sidebar extends VBox{
    MainLayout mainLayout;
    Sidebar(MainLayout mainLayout){
        this.mainLayout =  mainLayout;
        Button dashboardButton = new Button("dashboardButton");
        Button simulationButton = new Button("simulationButton");
        Button processButton = new Button("processButton");
        Button analyticsButton = new Button("analyticsButton");
        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        simulationButton.setMaxWidth(Double.MAX_VALUE);
        processButton.setMaxWidth(Double.MAX_VALUE);
        analyticsButton.setMaxWidth(Double.MAX_VALUE);
        dashboardButton.setOnAction(e -> mainLayout.DashboardView());
        simulationButton.setOnAction(e -> mainLayout.SimulationView());
        processButton.setOnAction(e -> mainLayout.ProcessView());
        analyticsButton.setOnAction(e -> mainLayout.AnalyticsView());
        setSpacing(12);
        setPadding(new Insets(20));
        setPrefWidth(190);
        getChildren().addAll(dashboardButton, simulationButton, processButton, analyticsButton);
    }
}
