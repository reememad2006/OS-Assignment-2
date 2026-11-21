package edu.cu.os241.gui;

import javafx.scene.layout.BorderPane;

public class MainLayout extends BorderPane {
    MainLayout(){
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
        setCenter(new Simulation());
    }
    void ProcessView(){
        setCenter(new Process());
    }
    void AnalyticsView(){
        setCenter(new Analytics());
    }
}
