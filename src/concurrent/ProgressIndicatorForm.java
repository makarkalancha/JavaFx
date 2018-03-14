package concurrent;

import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by mcalancea
 * Date: 14 Mar 2018
 * Time: 08:27
 */
public class ProgressIndicatorForm {
    private final Stage dialogStage;
    //    private final ProgressBar pb = new ProgressBar();
    private final ProgressIndicator pin = new ProgressIndicator();

    public ProgressIndicatorForm() {
        dialogStage = new Stage();
//        dialogStage.initStyle(StageStyle.UTILITY);//Defines a Stage style with a solid white background and minimal platform decorations used for a utility window.
        dialogStage.setResizable(false);
        //http://stackoverflow.com/questions/19953306/block-parent-stage-until-child-stage-closes
        dialogStage.initModality(Modality.APPLICATION_MODAL);
//        dialogStage.getIcons().add(new Image(UserInterfaceConstants.PROGRESS_INDICATOR_FORM_WINDOW_ICO));

        // PROGRESS BAR
//        final Label label = new Label();
//        label.setText("alerto");

//        pb.setProgress(-1F);
        pin.setProgress(-1F);

        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
//        hb.getChildren().addAll(pb, pin);
        hb.getChildren().addAll(pin);

        Scene scene = new Scene(hb);
        dialogStage.setScene(scene);


        //https://assylias.wordpress.com/2013/12/08/383/
        //this is where the transparency is achieved:
        //the three layers must be made transparent
        //(i)  make the VBox transparent (the 4th parameter is the alpha)
        hb.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        //(ii) set the scene fill to transparent
        scene.setFill(null);
        //(iii) set the stage background to transparent
        //Defines a Stage style with a transparent background and no decorations.
        dialogStage.initStyle(StageStyle.TRANSPARENT);
    }

    public void activateProgressBar(final Worker<?> task)  {
//        pb.progressProperty().bind(task.progressProperty());
        pin.progressProperty().bind(task.progressProperty());
//        dialogStage.show();
    }

    private Stage getDialogStage() {
        return dialogStage;
    }

    public void show(){
        dialogStage.setAlwaysOnTop(true);
        dialogStage.show();
    }

    public void close(){
        pin.progressProperty().unbind();
        dialogStage.close();
    }
}
