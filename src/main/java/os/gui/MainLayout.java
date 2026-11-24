package os.gui;

import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import os.scheduler.Process;
public class MainLayout extends BorderPane {
    private final ObservableList<Process> processes;

    MainLayout(){
        processes = FXCollections.observableArrayList();
        Header header = new Header();
        Sidebar sidebar = new Sidebar(this);
        StatusBar statusbar = new StatusBar();
        Dashboard dashboard = new Dashboard();
        setTop(header);
        setCenter(dashboard);
        setLeft(sidebar);
        setBottom(statusbar);
    }
    void DashboardView(){
        setCenter(new Dashboard());
    }
    void SimulationView(){
        setCenter(new Simulation(processes));
    }
    void ProcessView() {
        setCenter(new ProcessPage(processes));
    }

    void AnalyticsView(){
        setCenter(new Analytics());
    }
}
