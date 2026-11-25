package os.gui;

import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import os.scheduler.Process;
import java.util.List;

public class MainLayout extends BorderPane {
    private final ObservableList<Process> processes;
    private final ObservableList<List<Process>> testCases;
    private final Dashboard dashboard;

    MainLayout(){
        processes = FXCollections.observableArrayList();
        testCases = FXCollections.observableArrayList();
        Header header = new Header();
        Sidebar sidebar = new Sidebar(this);
        StatusBar statusbar = new StatusBar();
        dashboard = new Dashboard(this, processes, testCases);
        setTop(header);
        setCenter(dashboard);
        setLeft(sidebar);
        setBottom(statusbar);
        setStyle("-fx-background-color: #F4F7FB;");
    }
    void DashboardView(){
        dashboard.updateCounts(processes, testCases);
        setCenter(dashboard);
    }
    void SimulationView(){
        setCenter(new Simulation(processes, dashboard));
    }
    void ProcessView() {
        setCenter(new ProcessPage(processes, testCases));
    }
    void AnalyticsView(){
        setCenter(new Analytics(testCases));
    }
}
