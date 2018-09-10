package datetimepicker;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import datetimepicker.time.TimePicker1;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimePicker;
import tornadofx.control.DateTimePicker;
import web.Browser_v5;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by mcalancea
 * Date: 02 Feb 2018
 * Time: 16:39
 */
public class DateTimeSample extends Application{
    private Scene scene;

//    https://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm
//    continue from:
//    In this code, the web engine loads a URL that points to the Oracle corporate web site. The WebView object that contains this web engine is added to the application scene by using the getChildren and add methods.

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create the scene
        primaryStage.setTitle("Date time picker view");
        Scene scene = new Scene(new Group(), 450, 250);

        VBox vBox = new VBox();
        //tornado
        DateTimePicker datetimepicker = new DateTimePicker();
        datetimepicker.setDateTimeValue(LocalDateTime.of(2018, Month.SEPTEMBER, 10, 10, 5));
        vBox.getChildren().add(datetimepicker);

//        CalendarPicker2 calendarPicker = new CalendarPicker2();
//        jfxtras.icalendarfx.components.
////        calendarPicker.setShowTime(true);

//        datetimepicker.datetime.DateTimePicker datetimepicker1 = new datetimepicker.datetime.DateTimePicker();
//        vBox.getChildren().add(datetimepicker1);

        //jfoenix
        JFXTimePicker jfxTimePicker = new JFXTimePicker();
        jfxTimePicker.setIs24HourView(true);
        vBox.getChildren().add(jfxTimePicker);

        TimePicker1 timePicker1 = new TimePicker1();
        timePicker1.setIs24HourView(true);
        vBox.getChildren().add(timePicker1);

        CalendarTimePicker calendarTimePicker = new CalendarTimePicker();
        vBox.getChildren().add(calendarTimePicker);

        Group root = (Group) scene.getRoot();
        root.getChildren().add(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
