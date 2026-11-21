package os.gui;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
public class Sidebar extends VBox{
    MainLayout mainLayout;
    Sidebar(MainLayout mainLayout){
        this.mainLayout =  mainLayout;
        Button dashboardButton = new Button("dashboardButton");
        Button simulationButton = new Button("simulationButton");
        Button processButton = new Button("processButton");
        Button analyticsButton = new Button("analyticsButton");
        dashboardButton.setOnAction(e -> mainLayout.DashboardView());
        simulationButton.setOnAction(e -> mainLayout.SimulationView());
        processButton.setOnAction(e -> mainLayout.ProcessView());
        analyticsButton.setOnAction(e -> mainLayout.AnalyticsView());
        setSpacing(10);
        getChildren().addAll(dashboardButton, simulationButton, processButton, analyticsButton);
    }
}