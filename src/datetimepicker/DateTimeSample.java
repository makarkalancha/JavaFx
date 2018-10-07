package datetimepicker;

import com.jfoenix.controls.JFXTimePicker;
import datetimepicker.time.v2.TimePicker;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        vBox.setSpacing(5d);
//        //tornado
//        DateTimePicker datetimepicker = new DateTimePicker();
//        datetimepicker.setDateTimeValue(LocalDateTime.of(2018, Month.SEPTEMBER, 10, 10, 5));
//        vBox.getChildren().add(datetimepicker);

//        CalendarPicker2 calendarPicker = new CalendarPicker2();
//        jfxtras.icalendarfx.components.
////        calendarPicker.setShowTime(true);

//        datetimepicker.datetime.DateTimePicker datetimepicker1 = new datetimepicker.datetime.DateTimePicker();
//        vBox.getChildren().add(datetimepicker1);

        //jfoenix
        JFXTimePicker jfxTimePicker = new JFXTimePicker();
        jfxTimePicker.setIs24HourView(true);
        jfxTimePicker.setDefaultColor(Color.web("#960000"));
        jfxTimePicker.setConverter(new StringConverter<LocalTime>(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            @Override
            public LocalTime fromString(String string) {
                if(StringUtils.isNotBlank(string)) {
                    return LocalTime.parse(string, formatter);
                }else {
                    return null;
                }
            }

            @Override
            public String toString(LocalTime object) {
                if(object != null) {
                    return formatter.format(object);
                }else {
                    return "";
                }
            }
        });
        vBox.getChildren().add(jfxTimePicker);

        HBox hBox = new HBox();
        TimePicker timePicker = new TimePicker();
        timePicker.setIs24HourView(true);
        timePicker.setConverter(new StringConverter<LocalTime>(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            @Override
            public LocalTime fromString(String string) {
                if(StringUtils.isNotBlank(string)) {
                    return LocalTime.parse(string, formatter);
                }else {
                    return null;
                }
            }

            @Override
            public String toString(LocalTime object) {
                if(object != null) {
                    return formatter.format(object);
                }else {
                    return "";
                }
            }
        });

        hBox.getChildren().setAll(new Label("2->"), timePicker);
        vBox.getChildren().add(hBox);

        vBox.getChildren().add(new ComboBox<>());
        vBox.getChildren().add(new DatePicker());

//        BackgroundImage clockImg = new BackgroundImage(
////                new Image("if_42_311148.png", 20d, 20d, true, true),
//                new Image("if_42_311148_edited_32x32.png", 20d, 20d, true, true),
//                BackgroundRepeat.REPEAT,
//                BackgroundRepeat.REPEAT,
//                BackgroundPosition.DEFAULT,
//                new BackgroundSize(20d, 20d, false, false, false, false)
////                BackgroundSize.DEFAULT
//        );
//        vBox.setBackground(new Background(clockImg));

//        TimePicker1 timePicker1 = new TimePicker1();
//        timePicker1.setIs24HourView(true);
//        vBox.getChildren().add(timePicker1);

//        CalendarTimePicker calendarTimePicker = new CalendarTimePicker();
//        vBox.getChildren().add(calendarTimePicker);

        Group root = (Group) scene.getRoot();
        root.getChildren().add(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
