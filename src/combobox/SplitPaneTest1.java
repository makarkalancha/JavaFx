package combobox;

import javafx.application.Application;
<<<<<<< HEAD
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
public class SplitPaneTest1 extends Application {

=======
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

/**
 * User: Makar Kalancha
 * Date: 13/08/2016
 * Time: 01:14
 */
public class SplitPaneTest extends Application {
>>>>>>> 1e7c4518dd63890bd4638bd4911240b4ad5da5e3
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
<<<<<<< HEAD
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
=======
        primaryStage.setTitle("Scroll test");

        Group root = new Group();
        Scene scene = new Scene(root, 350, 250, Color.WHITE);

        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(scene.widthProperty());
        splitPane.prefHeightProperty().bind(scene.heightProperty());

        VBox leftArea = new VBox(10);
        HBox rowBox = new HBox(20);
        final Text leftText = TextBuilder.create()
                .text("Left ")
                .translateX(20)
                .fill(Color.RED)
                .font(Font.font(null, FontWeight.BOLD, 20))
                .build();

        rowBox.getChildren().add(leftText);
        leftArea.getChildren().add(rowBox);

        leftArea.setAlignment(Pos.CENTER);

        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.VERTICAL);
        splitPane2.prefWidthProperty().bind(scene.widthProperty());
        splitPane2.prefHeightProperty().bind(scene.heightProperty());

        HBox centerArea = new HBox();

        final Text upperRight = TextBuilder.create()
                .text("Text")
                .x(100)
                .y(50)
                .fill(Color.RED)
                .font(Font.font(null, FontWeight.BOLD, 35))
                .translateY(50)
                .build();
        centerArea.getChildren().add(upperRight);

        HBox rightArea = new HBox();

        final Text lowerRight = TextBuilder.create()
                .text("Lower Right")
                .x(100)
                .y(50)
                .fill(Color.RED)
                .font(Font.font(null, FontWeight.BOLD, 35))
                .translateY(50)
                .build();
        rightArea.getChildren().add(lowerRight);

        splitPane2.getItems().add(centerArea);
        splitPane2.getItems().add(rightArea);

        splitPane.getItems().add(leftArea);

        splitPane.getItems().add(splitPane2);

        ObservableList<SplitPane.Divider> dividers = splitPane.getDividers();
        for (int i = 0; i < dividers.size(); i++) {
            dividers.get(i).setPosition((i + 1.0) / 3);
        }
        HBox hbox = new HBox();
        hbox.getChildren().add(splitPane);
        root.getChildren().add(hbox);

        primaryStage.setScene(scene);
>>>>>>> 1e7c4518dd63890bd4638bd4911240b4ad5da5e3
        primaryStage.show();
    }
}
