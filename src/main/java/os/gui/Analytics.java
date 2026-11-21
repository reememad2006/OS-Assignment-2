package os.gui;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.chart.NumberAxis;

public class Analytics extends VBox {
    Analytics(){
        Label title = new Label("Analytics");
        lineChart.setTitle("quantum vs average");
        contextChart.setTitle("context vs average");
        x_axis.setLabel("quantum");
        y_axis.setLabel("average");
        contextXaxis.setLabel("context");
        contextYaxis.setLabel("average");
        getChildren().addAll(title,lineChart, contextChart);
        setPadding(new Insets(20));
        setSpacing(15);
    }
    NumberAxis x_axis = new NumberAxis();
    NumberAxis y_axis = new NumberAxis();
    NumberAxis contextXaxis = new NumberAxis();
    NumberAxis contextYaxis = new NumberAxis();
    LineChart<Number, Number> lineChart = new LineChart<>(x_axis, y_axis);
    LineChart<Number, Number> contextChart = new LineChart<>(contextXaxis, contextYaxis);
}