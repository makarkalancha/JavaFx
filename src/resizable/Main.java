package resizable;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by mcalancea
 * Date: 25 Jan 2018
 * Time: 12:22
 * varren/JavaFX-Resizable-Draggable-Node
 * https://github.com/varren/JavaFX-Resizable-Draggable-Node
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drag Resize Demo");
        Pane root = new Pane();

//        Rectangle rectangle = new Rectangle(50, 50);
//        rectangle.setFill(Color.BLACK);
//        DragResizeMod.makeResizable(rectangle, null);
//
//        Rectangle rectangle2 = new Rectangle(50, 50);
//        rectangle.setFill(Color.RED);
//        DragResizeMod.makeResizable(rectangle2);

        TableView<String> tableView = new TableView<>();
        DragResizeMod.makeResizable(tableView);

        PieChart pieChart = new PieChart();
        pieChart.setTitle("hello pie");
        pieChart.setStyle("-fx-border-color: red");
        DragResizeMod.makeResizable(pieChart);

        root.getChildren().addAll(/*rectangle, rectangle2, */tableView, pieChart);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
