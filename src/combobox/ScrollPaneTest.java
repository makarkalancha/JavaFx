package combobox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Makar Kalancha
 * Date: 11 Aug 2016
 * Time: 14:42
 *
 * http://docs.oracle.com/javafx/2/ui_controls/scrollpane.htm
 */
public class ScrollPaneTest extends Application{
    final ScrollPane sp = new ScrollPane();
    final Image[] images = new Image[5];
    final ImageView[] pics= new ImageView[5];
    final VBox vb = new VBox();
    final Label fileName = new Label();
    final String[] imageNames = new String[]{
      "001.jpg",
      "002.jpg",
      "003.jpg",
      "004.jpg",
      "005.jpg"
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box, 180, 180);
        primaryStage.setScene(scene);
        primaryStage.setTitle("title me");

        box.getChildren().addAll(sp, fileName);
        VBox.setVgrow(sp, Priority.ALWAYS);

        fileName.setLayoutX(30);
        fileName.setLayoutY(160);

        for (int i = 0; i < 5; i++) {
            images[i] = new Image(getClass().getResourceAsStream(imageNames[i]));
            pics[i] = new ImageView(images[i]);
            pics[i].setFitWidth(100);
            pics[i].setPreserveRatio(true);
            vb.getChildren().add(pics[i]);
        }

        sp.setVmax(440);
        sp.setPrefSize(115, 150);
        sp.setContent(vb);
        sp.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fileName.setText(imageNames[(newValue.intValue() - 1) / 100]);
            }
        });
        primaryStage.show();
    }
}
