package datetimepicker.time;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 16:48
 */
public class TimePickerContent1 extends VBox {
    private static final String ROBOTO = "Roboto";
    private static final String SPINNER_LABEL = "spinner-label";
    protected TimePicker1 timePicker;
    private Color fadedColor = Color.rgb(255, 255, 255, 0.67D);
    private boolean is24HourView = false;
    private double contentCircleRadius = 100.0D;
    private Label selectedHourLabel = new Label();
    private Label selectedMinLabel = new Label();
    private Label periodPMLabel;
    private Label periodAMLabel;
    private StackPane calendarPlaceHolder = new StackPane();
    private StackPane hoursContent;
    private StackPane minutesContent;
    private Rotate hoursPointerRotate;
    private Rotate _24HourHoursPointerRotate;
    private Rotate minsPointerRotate;
    private ObjectProperty<TimePickerContent1.TimeUnit> unit;
    private DoubleProperty angle;
    private StringProperty period;
    private ObjectProperty<Rotate> pointerRotate;
    private ObjectProperty<Rotate> _24HourPointerRotate;
    private ObjectProperty<Label> timeLabel;
    private NumberStringConverter unitConverter;
    private ObjectProperty<LocalTime> selectedTime;

    TimePickerContent1(TimePicker1 TimePicker1) {
        this.unit = new SimpleObjectProperty(TimePickerContent1.TimeUnit.HOURS);
        this.angle = new SimpleDoubleProperty(Math.toDegrees(0.5235987755982988D));
        this.period = new SimpleStringProperty("AM");
        this.pointerRotate = new SimpleObjectProperty();
        this._24HourPointerRotate = new SimpleObjectProperty();
        this.timeLabel = new SimpleObjectProperty();
        this.unitConverter = new NumberStringConverter("#00");
        this.selectedTime = new SimpleObjectProperty(this, "selectedTime");
        this.timePicker = TimePicker1;
        LocalTime time = this.timePicker.getValue() == null?LocalTime.now():(LocalTime)this.timePicker.getValue();
        this.is24HourView = this.timePicker.is24HourView();
        this.timePicker.valueProperty().addListener(JFXTimePickerContent$$Lambda$1.lambdaFactory$(this));
        this.getStyleClass().add("date-picker-popup");
        this.getChildren().add(this.createHeaderPane(time, this.is24HourView));
        VBox contentHolder = new VBox();
        contentHolder.getChildren().add(this.createContentPane(time, this.is24HourView));
        this.calendarPlaceHolder.getChildren().add(contentHolder);
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.calendarPlaceHolder.widthProperty());
        clip.heightProperty().bind(this.calendarPlaceHolder.heightProperty());
        this.calendarPlaceHolder.setClip(clip);
        StackPane contentPlaceHolder = new StackPane(new Node[]{this.calendarPlaceHolder});
        this.getChildren().add(contentPlaceHolder);
        this.unit.addListener(JFXTimePickerContent$$Lambda$2.lambdaFactory$(this));
        if(!this.is24HourView) {
            this.period.addListener(JFXTimePickerContent$$Lambda$3.lambdaFactory$(this));
        }

    }

    protected BorderPane createContentPane(LocalTime time, boolean _24HourView) {
        Circle circle = new Circle(this.contentCircleRadius);
        Circle selectionCircle = new Circle(this.contentCircleRadius / 6.0D);
        circle.setFill(Color.rgb(224, 224, 224, 0.67D));
        EventHandler mouseActionHandler = JFXTimePickerContent$$Lambda$4.lambdaFactory$(this, _24HourView, selectionCircle);
        circle.setOnMousePressed(mouseActionHandler);
        circle.setOnMouseDragged(mouseActionHandler);
        this.hoursContent = this.createHoursContent(time, _24HourView);
        this.hoursContent.setMouseTransparent(true);
        this.minutesContent = this.createMinutesContent(time);
        this.minutesContent.setOpacity(0.0D);
        this.minutesContent.setMouseTransparent(true);
        StackPane contentPane = new StackPane();
        contentPane.getChildren().addAll(new Node[]{circle, this.hoursContent, this.minutesContent});
        contentPane.setPadding(new Insets(12.0D));
        BorderPane contentContainer = new BorderPane();
        contentContainer.setCenter(contentPane);
        contentContainer.setMinHeight(50.0D);
        contentContainer.setPadding(new Insets(2.0D, 12.0D, 2.0D, 12.0D));
        return contentContainer;
    }

    protected StackPane createHeaderPane(LocalTime time, boolean _24HourView) {
        int hour = time.getHour();
        this.selectedHourLabel.setText((hour % (_24HourView?24:12) == 0?(_24HourView?0:12):hour % (_24HourView?24:12)) + "");
        this.selectedHourLabel.getStyleClass().add("spinner-label");
        this.selectedHourLabel.setTextFill(Color.WHITE);
        this.selectedHourLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 42.0D));
        this.selectedHourLabel.setOnMouseClicked(JFXTimePickerContent$$Lambda$5.lambdaFactory$(this));
        this.selectedHourLabel.setMinWidth(49.0D);
        this.selectedHourLabel.setAlignment(Pos.CENTER_RIGHT);
        this.timeLabel.set(this.selectedHourLabel);
        this.selectedMinLabel.setText(this.unitConverter.toString(Integer.valueOf(time.getMinute())) + "");
        this.selectedMinLabel.getStyleClass().add("spinner-label");
        this.selectedMinLabel.setTextFill(this.fadedColor);
        this.selectedMinLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 42.0D));
        this.selectedMinLabel.setOnMouseClicked(JFXTimePickerContent$$Lambda$6.lambdaFactory$(this));
        Label separatorLabel = new Label(":");
        separatorLabel.setPadding(new Insets(0.0D, 0.0D, 4.0D, 0.0D));
        separatorLabel.setTextFill(this.fadedColor);
        separatorLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 42.0D));
        this.periodPMLabel = new Label("PM");
        this.periodPMLabel.getStyleClass().add("spinner-label");
        this.periodPMLabel.setTextFill(this.fadedColor);
        this.periodPMLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 14.0D));
        this.periodPMLabel.setOnMouseClicked(JFXTimePickerContent$$Lambda$7.lambdaFactory$(this));
        this.periodAMLabel = new Label("AM");
        this.periodAMLabel.getStyleClass().add("spinner-label");
        this.periodAMLabel.setTextFill(this.fadedColor);
        this.periodAMLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 14.0D));
        this.periodAMLabel.setOnMouseClicked(JFXTimePickerContent$$Lambda$8.lambdaFactory$(this));
        if(hour < 12) {
            this.periodAMLabel.setTextFill(Color.WHITE);
        } else {
            this.periodPMLabel.setTextFill(Color.WHITE);
        }

        this.period.set(hour < 12?"AM":"PM");
        VBox periodContainer = new VBox();
        periodContainer.setPadding(new Insets(0.0D, 0.0D, 0.0D, 4.0D));
        periodContainer.getChildren().addAll(new Node[]{this.periodAMLabel, this.periodPMLabel});
        HBox selectedTimeContainer = new HBox();
        selectedTimeContainer.getStyleClass().add("spinner");
        selectedTimeContainer.getChildren().addAll(new Node[]{this.selectedHourLabel, separatorLabel, this.selectedMinLabel});
        if(!_24HourView) {
            selectedTimeContainer.getChildren().add(periodContainer);
        }

        selectedTimeContainer.setAlignment(Pos.CENTER);
        selectedTimeContainer.setFillHeight(false);
        StackPane headerPanel = new StackPane();
        headerPanel.getStyleClass().add("time-pane");
        headerPanel.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(this.timePicker.getDefaultColor(), CornerRadii.EMPTY, Insets.EMPTY)}));
        headerPanel.setPadding(new Insets(8.0D, 24.0D, 8.0D, 24.0D));
        headerPanel.getChildren().add(selectedTimeContainer);
        return headerPanel;
    }

    private StackPane createHoursContent(LocalTime time, boolean _24HourView) {
        StackPane hoursPointer = new StackPane();
        StackPane _24HoursPointer = new StackPane();
        Circle selectionCircle = new Circle(this.contentCircleRadius / 6.0D);
        Circle _24HourSelectionCircle = new Circle(this.contentCircleRadius / 6.0D);
        selectionCircle.fillProperty().bind(this.timePicker.defaultColorProperty());
        _24HourSelectionCircle.fillProperty().bind(this.timePicker.defaultColorProperty());
        double shift = 9.0D;
        double _24HourShift = 27.5D;
        Line line = new Line(shift, 0.0D, this.contentCircleRadius, 0.0D);
        line.fillProperty().bind(this.timePicker.defaultColorProperty());
        line.strokeProperty().bind(line.fillProperty());
        line.setStrokeWidth(1.5D);
        hoursPointer.getChildren().addAll(new Node[]{line, selectionCircle});
        StackPane.setAlignment(selectionCircle, Pos.CENTER_LEFT);
        Group pointerGroup = new Group();
        pointerGroup.getChildren().add(hoursPointer);
        pointerGroup.setTranslateX((-this.contentCircleRadius + shift) / 2.0D);
        this.hoursPointerRotate = new Rotate(0.0D, this.contentCircleRadius - shift, selectionCircle.getRadius());
        this.pointerRotate.set(this.hoursPointerRotate);
        pointerGroup.getTransforms().add(this.hoursPointerRotate);
        pointerGroup.setVisible(!this.is24HourView);
        Line _24HourLine = new Line(shift + _24HourShift, 0.0D, this.contentCircleRadius, 0.0D);
        _24HourLine.fillProperty().bind(this.timePicker.defaultColorProperty());
        _24HourLine.strokeProperty().bind(_24HourLine.fillProperty());
        _24HourLine.setStrokeWidth(1.5D);
        _24HoursPointer.getChildren().addAll(new Node[]{_24HourLine, _24HourSelectionCircle});
        StackPane.setAlignment(_24HourSelectionCircle, Pos.CENTER_LEFT);
        Group pointer24HourGroup = new Group();
        pointer24HourGroup.getChildren().add(_24HoursPointer);
        pointer24HourGroup.setTranslateX((-this.contentCircleRadius + shift) / 2.0D + _24HourShift / 2.0D);
        this._24HourHoursPointerRotate = new Rotate(0.0D, this.contentCircleRadius - shift - _24HourShift, selectionCircle.getRadius());
        this._24HourPointerRotate.set(this._24HourHoursPointerRotate);
        pointer24HourGroup.getTransforms().add(this._24HourHoursPointerRotate);
        pointer24HourGroup.setVisible(this.is24HourView);
        Pane clockLabelsContainer = new Pane();
        double radius = this.contentCircleRadius - shift - selectionCircle.getRadius();

        int i;
        int val;
        Label label;
        StackPane labelContainer;
        double labelSize;
        double angle;
        double xOffset;
        double yOffset;
        double startx;
        double starty;
        for(i = 0; i < 12; ++i) {
            val = (i + 3) % 12 == 0?12:(i + 3) % 12;
            label = new Label(Integer.toString(val));
            label.setFont(Font.font("Roboto", FontWeight.BOLD, 12.0D));
            label.setTextFill((val == time.getHour() % 12 || val == 12 && time.getHour() % 12 == 0) && !this.is24HourView?Color.rgb(255, 255, 255, 0.87D):Color.rgb(0, 0, 0, 0.87D));
            this.selectedHourLabel.textProperty().addListener(JFXTimePickerContent$$Lambda$9.lambdaFactory$(label));
            labelContainer = new StackPane();
            labelContainer.getChildren().add(label);
            labelSize = selectionCircle.getRadius() / Math.sqrt(2.0D) * 2.0D;
            labelContainer.setMinSize(labelSize, labelSize);
            angle = (double)(2 * i) * 3.141592653589793D / 12.0D;
            xOffset = radius * Math.cos(angle);
            yOffset = radius * Math.sin(angle);
            startx = this.contentCircleRadius + xOffset;
            starty = this.contentCircleRadius + yOffset;
            labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2.0D);
            labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2.0D);
            clockLabelsContainer.getChildren().add(labelContainer);
            if(!this.is24HourView && (val == time.getHour() % 12 || val == 12 && time.getHour() % 12 == 0)) {
                this.hoursPointerRotate.setAngle(180.0D + Math.toDegrees(angle));
            }
        }

        if(_24HourView) {
            radius /= 1.6D;

            for(i = 0; i < 12; ++i) {
                val = (i + 3) % 12 == 0?12:(i + 3) % 12;
                val += val == 12?-12:12;
                label = new Label(Integer.toString(val) + (val == 0?"0":""));
                label.setFont(Font.font("Roboto", FontWeight.NORMAL, 10.0D));
                label.setTextFill(val != time.getHour() % 24 && (val != 0 || time.getHour() % 24 != 0 || !this.is24HourView)?Color.rgb(0, 0, 0, 0.54D):Color.rgb(255, 255, 255, 0.54D));
                this.selectedHourLabel.textProperty().addListener(JFXTimePickerContent$$Lambda$10.lambdaFactory$(label));
                labelContainer = new StackPane();
                labelContainer.getChildren().add(label);
                labelSize = selectionCircle.getRadius() / Math.sqrt(2.0D) * 2.0D;
                labelContainer.setMinSize(labelSize, labelSize);
                angle = (double)(2 * i) * 3.141592653589793D / 12.0D;
                xOffset = radius * Math.cos(angle);
                yOffset = radius * Math.sin(angle);
                startx = this.contentCircleRadius + xOffset;
                starty = this.contentCircleRadius + yOffset;
                labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2.0D);
                labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2.0D);
                clockLabelsContainer.getChildren().add(labelContainer);
                if(val == time.getHour() % 24 || val == 24 && time.getHour() % 24 == 0) {
                    this._24HourHoursPointerRotate.setAngle(180.0D + Math.toDegrees(angle));
                }
            }
        }

        return _24HourView?new StackPane(new Node[]{pointerGroup, pointer24HourGroup, clockLabelsContainer}):new StackPane(new Node[]{pointerGroup, clockLabelsContainer});
    }

    private StackPane createMinutesContent(LocalTime time) {
        StackPane minsPointer = new StackPane();
        Circle selectionCircle = new Circle(this.contentCircleRadius / 6.0D);
        selectionCircle.fillProperty().bind(this.timePicker.defaultColorProperty());
        Circle minCircle = new Circle(selectionCircle.getRadius() / 8.0D);
        minCircle.setFill(Color.rgb(255, 255, 255, 0.87D));
        minCircle.setTranslateX(selectionCircle.getRadius() - minCircle.getRadius());
        minCircle.setVisible(time.getMinute() % 5 != 0);
        this.selectedMinLabel.textProperty().addListener(JFXTimePickerContent$$Lambda$11.lambdaFactory$(minCircle));
        double shift = 9.0D;
        Line line = new Line(shift, 0.0D, this.contentCircleRadius, 0.0D);
        line.fillProperty().bind(this.timePicker.defaultColorProperty());
        line.strokeProperty().bind(line.fillProperty());
        line.setStrokeWidth(1.5D);
        minsPointer.getChildren().addAll(new Node[]{line, selectionCircle, minCircle});
        StackPane.setAlignment(selectionCircle, Pos.CENTER_LEFT);
        StackPane.setAlignment(minCircle, Pos.CENTER_LEFT);
        Group pointerGroup = new Group();
        pointerGroup.getChildren().add(minsPointer);
        pointerGroup.setTranslateX((-this.contentCircleRadius + shift) / 2.0D);
        this.minsPointerRotate = new Rotate(0.0D, this.contentCircleRadius - shift, selectionCircle.getRadius());
        pointerGroup.getTransforms().add(this.minsPointerRotate);
        Pane clockLabelsContainer = new Pane();
        double radius = this.contentCircleRadius - shift - selectionCircle.getRadius();

        for(int i = 0; i < 12; ++i) {
            StackPane labelContainer = new StackPane();
            int val = (i + 3) * 5 % 60;
            Label label = new Label(this.unitConverter.toString(Integer.valueOf(val)) + "");
            label.setFont(Font.font("Roboto", FontWeight.BOLD, 12.0D));
            label.setTextFill(val == time.getMinute()?Color.rgb(255, 255, 255, 0.87D):Color.rgb(0, 0, 0, 0.87D));
            this.selectedMinLabel.textProperty().addListener(JFXTimePickerContent$$Lambda$12.lambdaFactory$(label));
            labelContainer.getChildren().add(label);
            double labelSize = selectionCircle.getRadius() / Math.sqrt(2.0D) * 2.0D;
            labelContainer.setMinSize(labelSize, labelSize);
            double angle = (double)(2 * i) * 3.141592653589793D / 12.0D;
            double xOffset = radius * Math.cos(angle);
            double yOffset = radius * Math.sin(angle);
            double startx = this.contentCircleRadius + xOffset;
            double starty = this.contentCircleRadius + yOffset;
            labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2.0D);
            labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2.0D);
            clockLabelsContainer.getChildren().add(labelContainer);
        }

        this.minsPointerRotate.setAngle(180.0D + (double)((time.getMinute() + 45) % 60) * Math.toDegrees(0.10471975511965977D));
        return new StackPane(new Node[]{pointerGroup, clockLabelsContainer});
    }

    ObjectProperty<LocalTime> displayedTimeProperty() {
        return this.selectedTime;
    }

    void init() {
        this.calendarPlaceHolder.setOpacity(1.0D);
        this.selectedHourLabel.setTextFill(Color.rgb(255, 255, 255, 0.87D));
    }

    private void swapLabelsColor(Label lbl1, Label lbl2) {
        Paint color = lbl1.getTextFill();
        lbl1.setTextFill(lbl2.getTextFill());
        lbl2.setTextFill(color);
    }

    private void switchTimeUnit(TimePickerContent1.TimeUnit newVal) {
        Timeline fadeout;
        Timeline fadein;
        if(newVal == TimePickerContent1.TimeUnit.HOURS) {
            fadeout = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(320.0D), new KeyValue[]{new KeyValue(this.minutesContent.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)})});
            fadein = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(320.0D), new KeyValue[]{new KeyValue(this.hoursContent.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})});
            (new ParallelTransition(new Animation[]{fadeout, fadein})).play();
        } else {
            fadeout = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(320.0D), new KeyValue[]{new KeyValue(this.hoursContent.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)})});
            fadein = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(320.0D), new KeyValue[]{new KeyValue(this.minutesContent.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})});
            (new ParallelTransition(new Animation[]{fadeout, fadein})).play();
        }

    }

    void updateValue() {
        if(this.is24HourView) {
            LocalTimeStringConverter localTimeStringConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.GERMAN);
            this.timePicker.setValue(localTimeStringConverter.fromString(this.selectedHourLabel.getText() + ":" + this.selectedMinLabel.getText()));
        } else {
            this.timePicker.setValue(LocalTime.parse(this.selectedHourLabel.getText() + ":" + this.selectedMinLabel.getText() + " " + (String)this.period.get(), DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.ENGLISH)));
        }

    }

    private void goToTime(LocalTime time) {
        if(time != null) {
            int hour = time.getHour();
            this.selectedHourLabel.setText(Integer.toString(hour % (this.is24HourView?24:12) == 0?(this.is24HourView?0:12):hour % (this.is24HourView?24:12)));
            this.selectedMinLabel.setText(this.unitConverter.toString(Integer.valueOf(time.getMinute())));
            if(!this.is24HourView) {
                this.period.set(hour < 12?"AM":"PM");
            }

            this.minsPointerRotate.setAngle(180.0D + (double)((time.getMinute() + 45) % 60) * Math.toDegrees(0.10471975511965977D));
            this.hoursPointerRotate.setAngle(180.0D + Math.toDegrees((double)(2 * (hour - 3)) * 3.141592653589793D / 12.0D));
            this._24HourHoursPointerRotate.setAngle(180.0D + Math.toDegrees((double)(2 * (hour - 3)) * 3.141592653589793D / 12.0D));
        }

    }

    void clearFocus() {
        LocalTime focusTime = (LocalTime)this.timePicker.getValue();
        if(focusTime == null) {
            focusTime = LocalTime.now();
        }

        this.goToTime(focusTime);
    }

    private static enum TimeUnit {
        HOURS,
        MINUTES;

        private TimeUnit() {
        }
    }
}
