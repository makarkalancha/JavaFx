package combobox;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.font.TrueTypeFont;

/**
 * Created by Makar Kalancha
 * Date: 16 Aug 2016
 * Time: 13:36
 */
public class SplitPaneTest extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hBox = new HBox(20);
        hBox.setTranslateX(20);
        hBox.setTranslateY(20);

        SplitPane splitPane1 = new SplitPane();
        splitPane1.setPrefSize(200, 200);
//        splitPane1.setOrientation(Orientation.VERTICAL);
        final Button left = new Button("Left button");
        final Button right = new Button("Right button");
        splitPane1.getItems().addAll(left, right);
        hBox.getChildren().addAll(splitPane1);

        SplitPane splitPane2 = new SplitPane();
        splitPane2.setPrefSize(300, 200);
//        splitPane2.setOrientation(Orientation.VERTICAL);
        final Button left2 = new Button("Left Button");
        final Button center2 = new Button("Center Button");
        final Button right2 = new Button("Right Button");
        ScrollPane scrollPaneL2 = new ScrollPane();
        ScrollPane scrollPaneC2 = new ScrollPane();
        ScrollPane scrollPaneR2 = new ScrollPane();
        scrollPaneL2.setContent(left2);
        scrollPaneC2.setContent(center2);
        scrollPaneR2.setContent(right2);
        splitPane2.getItems().addAll(scrollPaneL2, scrollPaneC2, scrollPaneR2);
        hBox.getChildren().addAll(splitPane2);

        Scene scene = new Scene(new Group(hBox), 560, 240);
        scene.setFill(Color.GHOSTWHITE);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Split Pane");
        primaryStage.show();
    }
}
