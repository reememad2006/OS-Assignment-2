package os.gui;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

public class Sidebar extends VBox {
    MainLayout mainLayout;

    Sidebar(MainLayout mainLayout) {
        this.mainLayout = mainLayout;

        Button dashboardButton = new Button("🏠 Home");
        Button simulationButton = new Button("▶ Simulation");
        Button processButton = new Button("📋 Processes");
        Button analyticsButton = new Button("📊 Analytics");

        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        simulationButton.setMaxWidth(Double.MAX_VALUE);
        processButton.setMaxWidth(Double.MAX_VALUE);
        analyticsButton.setMaxWidth(Double.MAX_VALUE);

        dashboardButton.setOnAction(e -> mainLayout.DashboardView());
        simulationButton.setOnAction(e -> mainLayout.SimulationView());
        processButton.setOnAction(e -> mainLayout.ProcessView());
        analyticsButton.setOnAction(e -> mainLayout.AnalyticsView());

        String buttonStyle =
                        "-fx-background-color: #D6BBA8;" +
                        "-fx-text-fill: #4B342B;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #B58B72;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-cursor: hand;";

        dashboardButton.setStyle(buttonStyle);
        simulationButton.setStyle(buttonStyle);
        processButton.setStyle(buttonStyle);
        analyticsButton.setStyle(buttonStyle);

        setSpacing(12);
        setPadding(new Insets(20));
        setPrefWidth(190);

        setStyle("-fx-background-color: #E8D5C5;");

        getChildren().addAll(
                dashboardButton,
                simulationButton,
                processButton,
                analyticsButton
        );
    }
}
