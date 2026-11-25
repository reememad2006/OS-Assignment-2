package os.gui;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.NumberAxis;
import os.scheduler.Process;
import java.util.List;
import javafx.scene.chart.XYChart;
import os.scheduler.ACSScheduler;
import os.scheduler.Result;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;
public class Analytics extends VBox {
    Analytics(ObservableList<List<Process>> testCases){
        Label title = new Label("Analytics");
        Label subtitle = new Label(
                "Analyze how Quantum and Context Switch affect scheduling performance."
        );

        lineChart.setTitle("Impact of Time Quantum");
        contextChart.setTitle("Impact of Context Switch");

        lineChart.setAnimated(false);
        contextChart.setAnimated(false);

        lineChart.setCreateSymbols(true);
        contextChart.setCreateSymbols(true);

        lineChart.setLegendVisible(true);
        contextChart.setLegendVisible(true);

        lineChart.setMinHeight(400);
        contextChart.setMinHeight(400);

        lineChart.setPrefHeight(450);
        contextChart.setPrefHeight(450);

        for (List<Process> testCase : testCases) {

            XYChart.Series<Number, Number> waiting = new XYChart.Series<>();
            XYChart.Series<Number, Number> turnaround = new XYChart.Series<>();
            waiting.setName("Test Case " + (testCases.indexOf(testCase) + 1) + " - waiting");
            turnaround.setName("Test Case " + (testCases.indexOf(testCase) +1) +  " - turnaround");

            for (int quantum = 1; quantum <= 5; quantum++) {
                ACSScheduler scheduler = new ACSScheduler(quantum, 1);
                Result result = scheduler.run(copyProcesses(testCase));
                waiting.getData().add(new XYChart.Data<>(quantum, result.getAverageWaitingTime()));
                turnaround.getData().add(new XYChart.Data<>(quantum, result.getAverageTurnaroundTime()));
            }
            lineChart.getData().addAll(waiting, turnaround);
        }
        for(List<Process> testCase:testCases){
            XYChart.Series<Number, Number> cswaiting = new XYChart.Series<>();
            XYChart.Series<Number, Number> csturnaround = new XYChart.Series<>();
            int caseNumber = testCases.indexOf(testCase) + 1;
            cswaiting.setName("Test Case " + caseNumber + " - Waiting");
            csturnaround.setName("Test Case " + caseNumber + " - Turnaround");
            for(int cs=0; cs<=4;cs++){
                ACSScheduler scheduler = new ACSScheduler(3, cs);
                Result result = scheduler.run(copyProcesses(testCase));
                cswaiting.getData().add(new XYChart.Data<>(cs, result.getAverageWaitingTime()));
                csturnaround.getData().add(new XYChart.Data<>(cs, result.getAverageTurnaroundTime()));
            }
            contextChart.getData().addAll(cswaiting, csturnaround);
        }
        x_axis.setLabel("Time Quantum");
        y_axis.setLabel("Average Time");

        contextXaxis.setLabel("Context Switch Time");
        contextYaxis.setLabel("Average Time");
        Label quantumTitle = new Label("Quantum Analysis");
        Label contextTitle = new Label("Context Switch Analysis");

        VBox quantumSection = new VBox(10, quantumTitle, lineChart);
        VBox contextSection = new VBox(10, contextTitle, contextChart);

        quantumSection.setPadding(new Insets(15));
        contextSection.setPadding(new Insets(15));

        VBox content = new VBox(
                15,
                title,
                subtitle,
                quantumSection,
                contextSection
        );

        content.setPadding(new Insets(25));
        content.setFillWidth(true);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        getChildren().add(scrollPane);

        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
    }
    NumberAxis x_axis = new NumberAxis();
    NumberAxis y_axis = new NumberAxis();
    NumberAxis contextXaxis = new NumberAxis();
    NumberAxis contextYaxis = new NumberAxis();
    LineChart<Number, Number> lineChart = new LineChart<>(x_axis, y_axis);
    LineChart<Number, Number> contextChart = new LineChart<>(contextXaxis, contextYaxis);
    private List<Process> copyProcesses(List<Process> original){
        List<Process> copies=new ArrayList<>();
        for(Process p : original){
            copies.add(new Process(
                    p.getId(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
            ));
        }
        return copies;
    }
}
