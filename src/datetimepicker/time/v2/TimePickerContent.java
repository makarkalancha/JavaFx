package datetimepicker.time.v2;

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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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

import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Created by mcalancea
 * Date: 04 Oct 2018
 * Time: 07:55
 */
public class TimePickerContent extends VBox {
    private enum TimeUnit {HOURS, MINUTES}

    private static final String ROBOTO = "Roboto";
    private static final String SPINNER_LABEL = "spinner-label";
    protected TimePicker timePicker;

    private Color fadedColor = Color.rgb(0, 0, 0, 0.67);
    private Color selectedColorBlue = Color.rgb(0, 100, 200)/*, 0.67)*/;
    private boolean is24HourView = false;
    private double contentCircleRadius = 100;
    private Label selectedHourLabel = new Label();
    private Label selectedMinLabel = new Label();
    private Label periodPMLabel, periodAMLabel;
    private StackPane calendarPlaceHolder = new StackPane();
    private StackPane hoursContent;
    private StackPane minutesContent;
    private Rotate hoursPointerRotate;
    private Rotate _24HourHoursPointerRotate;
    private Rotate minsPointerRotate;
    private ObjectProperty<TimeUnit> unit = new SimpleObjectProperty<>(TimeUnit.HOURS);
    private DoubleProperty angle = new SimpleDoubleProperty(Math.toDegrees(2 * Math.PI / 12));
    private StringProperty period = new SimpleStringProperty("AM");
    private ObjectProperty<Rotate> pointerRotate = new SimpleObjectProperty<>();
    private ObjectProperty<Rotate> _24HourPointerRotate = new SimpleObjectProperty<>();
    private ObjectProperty<Label> timeLabel = new SimpleObjectProperty<>();
    private NumberStringConverter unitConverter = new NumberStringConverter("#00");
    private ObjectProperty<LocalTime> selectedTime = new SimpleObjectProperty<>(this, "selectedTime");

    public TimePickerContent(final TimePicker timePicker) {
        this.timePicker = timePicker;
        LocalTime time = this.timePicker.getValue() == null ?
                LocalTime.now() : this.timePicker.getValue();
        is24HourView = this.timePicker.isMilitaryTime();

        this.timePicker.valueProperty().addListener((o, oldVal, newVal) -> goToTime(newVal));
        getStyleClass().add("date-picker-popup");

        // create the header pane
        getChildren().add(createHeaderPane(time, is24HourView));
        getChildren().add(new Separator(Orientation.HORIZONTAL));

        VBox contentHolder = new VBox();
        // create content pane
        contentHolder.getChildren().add(createContentPane(time, is24HourView));
        calendarPlaceHolder.getChildren().add(contentHolder);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(calendarPlaceHolder.widthProperty());
        clip.heightProperty().bind(calendarPlaceHolder.heightProperty());
        calendarPlaceHolder.setClip(clip);

        StackPane contentPlaceHolder = new StackPane(calendarPlaceHolder);
        getChildren().add(contentPlaceHolder);

        // add listeners
        unit.addListener((o, oldVal, newVal) -> {
            if (newVal == TimeUnit.HOURS) {
                angle.set(Math.toDegrees(2 * Math.PI / 12));
                int tmp = Integer.parseInt(selectedHourLabel.getText());
                if (is24HourView) {
                    if (tmp == 0 || tmp > 12) {
                        hoursContent.getChildren().get(0).setVisible(false);
                        hoursContent.getChildren().get(1).setVisible(true);
                    } else {
                        hoursContent.getChildren().get(1).setVisible(false);
                        hoursContent.getChildren().get(0).setVisible(true);
                    }
                }
                pointerRotate.set(_24HourHoursPointerRotate);
                _24HourPointerRotate.set(_24HourHoursPointerRotate);
                timeLabel.set(selectedHourLabel);
            } else if (newVal == TimeUnit.MINUTES) {
                angle.set(Math.toDegrees(2 * Math.PI / 60));
                pointerRotate.set(minsPointerRotate);
                timeLabel.set(selectedMinLabel);
            }
            swapLabelsColor(selectedHourLabel, selectedMinLabel);
            switchTimeUnit(newVal);
        });

        if (!is24HourView) {
            period.addListener((o, oldVal, newVal) -> {
                swapLabelsColor(periodPMLabel, periodAMLabel);
                updateValue();
            });
        }
    }

    void init() {
        calendarPlaceHolder.setOpacity(1);
        if(unit.get() == TimeUnit.HOURS){
            selectedHourLabel.setTextFill(Color.rgb(0, 0, 0, 0.87));
        }else{
            selectedMinLabel.setTextFill(Color.rgb(0, 0, 0, 0.87));
        }
    }

    public void clearFocus() {
        LocalTime focusTime = timePicker.getValue();
        if (focusTime == null) {
            focusTime = LocalTime.now();
        }
        unit.set(TimeUnit.HOURS);
        init();

        goToTime(focusTime);
    }

    private void goToTime(LocalTime time) {
        if (time != null) {
            int hour = time.getHour();
            selectedHourLabel.setText(Integer.toString(hour % (is24HourView ? 24 : 12) == 0 ?
                    (is24HourView ? 0 : 12) : hour % (is24HourView ? 24 : 12)));
            selectedMinLabel.setText(unitConverter.toString(time.getMinute()));
            if (!is24HourView) {
                period.set(hour < 12 ? "AM" : "PM");
            }
            minsPointerRotate.setAngle(180 + (time.getMinute() + 45) % 60 * Math.toDegrees(2 * Math.PI / 60));
            System.out.println("angle:" + (180 + (time.getMinute() + 45) % 60 * Math.toDegrees(2 * Math.PI / 60)));
            if (hour == 0 || hour > 12) {
                _24HourHoursPointerRotate.setAngle(180 + Math.toDegrees(2 * (hour - 3) * Math.PI / 12));
                hoursContent.getChildren().get(0).setVisible(false);
                hoursContent.getChildren().get(1).setVisible(true);
            }else {
                hoursPointerRotate.setAngle(180 + Math.toDegrees(2 * (hour - 3) * Math.PI / 12));
                hoursContent.getChildren().get(0).setVisible(true);
                hoursContent.getChildren().get(1).setVisible(false);
            }
        }
    }

    /*
     * header panel represents the selected Time
     * we keep javaFX original style classes
     */
    protected StackPane createHeaderPane(LocalTime time, boolean _24HourView) {
        int hour = time.getHour();

        selectedHourLabel.setText(String.valueOf(hour % (_24HourView ? 24 : 12) == 0 ? (_24HourView ? 0 : 12) : hour % (_24HourView ? 24 : 12)));
        selectedHourLabel.getStyleClass().add(SPINNER_LABEL);
        selectedHourLabel.setTextFill(Color.BLACK);
        selectedHourLabel.setFont(Font.font(ROBOTO, FontWeight.BOLD, 42));
        selectedHourLabel.setOnMouseClicked((click) -> unit.set(TimeUnit.HOURS));
        selectedHourLabel.setMinWidth(49);
        selectedHourLabel.setAlignment(Pos.CENTER_RIGHT);
        timeLabel.set(selectedHourLabel);

        selectedMinLabel.setText(String.valueOf(unitConverter.toString(time.getMinute())));
        selectedMinLabel.getStyleClass().add(SPINNER_LABEL);
        selectedMinLabel.setTextFill(fadedColor);
        selectedMinLabel.setFont(Font.font(ROBOTO, FontWeight.BOLD, 42));
        selectedMinLabel.setOnMouseClicked((click) -> unit.set(TimeUnit.MINUTES));

        Label separatorLabel = new Label(":");
        separatorLabel.setPadding(new Insets(0, 0, 4, 0));
        separatorLabel.setTextFill(fadedColor);
        separatorLabel.setFont(Font.font(ROBOTO, FontWeight.BOLD, 42));

        periodPMLabel = new Label("PM");
        periodPMLabel.getStyleClass().add(SPINNER_LABEL);
        periodPMLabel.setTextFill(fadedColor);
        periodPMLabel.setFont(Font.font(ROBOTO, FontWeight.BOLD, 14));
        periodPMLabel.setOnMouseClicked((click) -> period.set("PM"));

        periodAMLabel = new Label("AM");
        periodAMLabel.getStyleClass().add(SPINNER_LABEL);
        periodAMLabel.setTextFill(fadedColor);
        periodAMLabel.setFont(Font.font(ROBOTO, FontWeight.BOLD, 14));
        periodAMLabel.setOnMouseClicked((click) -> period.set("AM"));

        // init period value
        if (hour < 12) {
            periodAMLabel.setTextFill(Color.BLACK);
        } else {
            periodPMLabel.setTextFill(Color.BLACK);
        }
        period.set(hour < 12 ? "AM" : "PM");

        VBox periodContainer = new VBox();
        periodContainer.setPadding(new Insets(0, 0, 0, 4));
        periodContainer.getChildren().addAll(periodAMLabel, periodPMLabel);

        // Label container
        HBox selectedTimeContainer = new HBox();
        selectedTimeContainer.getStyleClass().add("spinner");
        selectedTimeContainer.getChildren()
                .addAll(selectedHourLabel, separatorLabel, selectedMinLabel);
        if (!_24HourView) {
            selectedTimeContainer.getChildren().add(periodContainer);
        }
        selectedTimeContainer.setAlignment(Pos.CENTER);
        selectedTimeContainer.setFillHeight(false);

        StackPane headerPanel = new StackPane();
        headerPanel.getStyleClass().add("time-pane");
        headerPanel.setPadding(new Insets(8, 24, 8, 24));
        headerPanel.getChildren().add(selectedTimeContainer);
        return headerPanel;
    }

    protected BorderPane createContentPane(LocalTime time, boolean _24HourView) {
        Circle circle = new Circle(contentCircleRadius),
                selectionCircle = new Circle(contentCircleRadius / 6);
        circle.setFill(Color.rgb(224, 224, 224, 0.67));

        EventHandler<? super MouseEvent> mouseActionHandler = (event) -> {
            double dx = event.getX();
            double dy = event.getY();
            double shift = 9;
            double theta = Math.atan2(dy, dx);
            int index = (int) Math.round((180 + Math.toDegrees(theta)) / angle.get()),
                    timeValue;
            if (_24HourView) {
                if (Point2D.distance(0, 0, dx, dy) >= (contentCircleRadius - shift - (2 * selectionCircle.getRadius()))) {
                    hoursContent.getChildren().get(1).setVisible(false);
                    hoursContent.getChildren().get(0).setVisible(true);
                    pointerRotate.get().setAngle(index * angle.get());
                    timeValue = (index + 9) % 12 == 0 ? 12 : (index + 9) % 12;
                } else {
                    hoursContent.getChildren().get(0).setVisible(false);
                    hoursContent.getChildren().get(1).setVisible(true);
                    _24HourPointerRotate.get().setAngle(index * angle.get());
                    int tmp = ((index + 21) % 24 <= 13 ? (index + 21) % 24 + 12 : (index + 21) % 24);
                    timeValue = tmp == 12 ? 0 : tmp;
                }
            } else {
                pointerRotate.get().setAngle(index * angle.get());
                timeValue = (index + 9) % 12 == 0 ? 12 : (index + 9) % 12;
            }
            if (unit.get() == TimeUnit.MINUTES) {
                timeValue = (index + 45) % 60;
            }
            timeLabel.get().setText(unit.get() == TimeUnit.MINUTES ? unitConverter.toString(timeValue) : Integer.toString(timeValue));
            updateValue();
        };

        circle.setOnMousePressed(mouseActionHandler);
        circle.setOnMouseDragged(mouseActionHandler);

        hoursContent = createHoursContent(time, _24HourView);
        hoursContent.setMouseTransparent(true);
        minutesContent = createMinutesContent(time);
        minutesContent.setOpacity(0);
        minutesContent.setMouseTransparent(true);

        StackPane contentPane = new StackPane();
        contentPane.getChildren().addAll(circle, hoursContent, minutesContent);
        contentPane.setPadding(new Insets(12));

        BorderPane contentContainer = new BorderPane();
        contentContainer.setCenter(contentPane);
        contentContainer.setMinHeight(50);
        contentContainer.setPadding(new Insets(2, 12, 2, 12));
        return contentContainer;
    }

    private StackPane createHoursContent(LocalTime time, boolean _24HourView) {
        // create hours content
        StackPane hoursPointer = new StackPane();
        StackPane _24HoursPointer = new StackPane();
        Circle selectionCircle = new Circle(contentCircleRadius / 6),
                _24HourSelectionCircle = new Circle(contentCircleRadius / 6);
        selectionCircle.setFill(selectedColorBlue);
        _24HourSelectionCircle.setFill(selectedColorBlue);

        double shift = 9, _24HourShift = 27.5;
        Line line = new Line(shift, 0, contentCircleRadius, 0);
        line.setFill(selectedColorBlue);
        line.strokeProperty().bind(line.fillProperty());
        line.setStrokeWidth(1.5);
        hoursPointer.getChildren().addAll(line, selectionCircle);
        StackPane.setAlignment(selectionCircle, Pos.CENTER_LEFT);

        Group pointerGroup = new Group();
        pointerGroup.getChildren().add(hoursPointer);
        pointerGroup.setTranslateX((-contentCircleRadius + shift) / 2);
        hoursPointerRotate = new Rotate(0, contentCircleRadius - shift, selectionCircle.getRadius());
        pointerRotate.set(hoursPointerRotate);
        pointerGroup.getTransforms().add(hoursPointerRotate);
        pointerGroup.setVisible(!is24HourView);

        Line _24HourLine = new Line(shift + _24HourShift, 0, contentCircleRadius, 0);
        _24HourLine.setFill(selectedColorBlue);
        _24HourLine.strokeProperty().bind(_24HourLine.fillProperty());
        _24HourLine.setStrokeWidth(1.5);
        _24HoursPointer.getChildren().addAll(_24HourLine, _24HourSelectionCircle);
        StackPane.setAlignment(_24HourSelectionCircle, Pos.CENTER_LEFT);

        Group pointer24HourGroup = new Group();
        pointer24HourGroup.getChildren().add(_24HoursPointer);
        pointer24HourGroup.setTranslateX(((-contentCircleRadius + shift) / 2) + (_24HourShift / 2));
        _24HourHoursPointerRotate = new Rotate(0, contentCircleRadius - shift - _24HourShift, selectionCircle.getRadius());
        _24HourPointerRotate.set(_24HourHoursPointerRotate);
        pointer24HourGroup.getTransforms().add(_24HourHoursPointerRotate);
        pointer24HourGroup.setVisible(is24HourView);

        Pane clockLabelsContainer = new Pane();
        // inner circle radius
        double radius = contentCircleRadius - shift - selectionCircle.getRadius();
        for (int i = 0; i < 12; i++) {
            // create the label and its container
            int val = (i + 3) % 12 == 0 ? 12 : (i + 3) % 12;
            Label label = new Label(Integer.toString(val));
            label.setFont(Font.font(ROBOTO, FontWeight.BOLD, 12));

            // init color
            label.setTextFill(((val == time.getHour() % 12 || (val == 12 && time.getHour() % 12 == 0)) && !is24HourView) ?
                    Color.rgb(255, 255, 255, 0.87) : Color.rgb(0, 0, 0, 0.87));
            selectedHourLabel.textProperty().addListener((o, oldVal, newVal) -> {
                if (Integer.parseInt(newVal) == Integer.parseInt(label.getText())) {
                    label.setTextFill(Color.rgb(255, 255, 255, 0.87));
                    unit.set(TimeUnit.MINUTES);
                } else {
                    label.setTextFill(Color.rgb(0, 0, 0, 0.87));
                }
            });

            // create label container
            StackPane labelContainer = new StackPane();
            labelContainer.getChildren().add(label);
            double labelSize = (selectionCircle.getRadius() / Math.sqrt(2)) * 2;
            labelContainer.setMinSize(labelSize, labelSize);

            // position the label on the circle
            double angle = 2 * i * Math.PI / 12;
            double xOffset = radius * Math.cos(angle);
            double yOffset = radius * Math.sin(angle);
            final double startx = contentCircleRadius + xOffset;
            final double starty = contentCircleRadius + yOffset;
            labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2);
            labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2);

            // add label to the parent node
            clockLabelsContainer.getChildren().add(labelContainer);

            // init pointer angle
            if (!is24HourView && (val == time.getHour() % 12 || (val == 12 && time.getHour() % 12 == 0))) {
                hoursPointerRotate.setAngle(180 + Math.toDegrees(angle));
            }
        }

        if (_24HourView) {
            radius /= 1.6;
            for (int i = 0; i < 12; i++) {
                // create the label and its container
                int val = (i + 3) % 12 == 0 ? 12 : (i + 3) % 12;
                val += (val == 12 ? -12 : 12);
                Label label = new Label(Integer.toString(val) + (val == 0 ? "0" : ""));
                label.setFont(Font.font(ROBOTO, FontWeight.NORMAL, 10));

                // init color
                label.setTextFill((val == time.getHour() % 24 || (val == 0 && time.getHour() % 24 == 0) && is24HourView) ?
                        Color.rgb(255, 255, 255, 0.54) : Color.rgb(0, 0, 0, 0.54));
                selectedHourLabel.textProperty().addListener((o, oldVal, newVal) -> {
                    if (Integer.parseInt(newVal) == Integer.parseInt(label.getText())) {
                        label.setTextFill(Color.rgb(255, 255, 255, 0.54));
                        unit.set(TimeUnit.MINUTES);
                    } else {
                        label.setTextFill(Color.rgb(0, 0, 0, 0.54));
                    }
                });

                // create label container
                StackPane labelContainer = new StackPane();
                labelContainer.getChildren().add(label);
                double labelSize = (selectionCircle.getRadius() / Math.sqrt(2)) * 2;
                labelContainer.setMinSize(labelSize, labelSize);

                // position the label on the circle
                double angle = 2 * i * Math.PI / 12;
                double xOffset = radius * Math.cos(angle);
                double yOffset = radius * Math.sin(angle);
                final double startx = contentCircleRadius + xOffset;
                final double starty = contentCircleRadius + yOffset;
                labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2);
                labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2);

                // add label to the parent node
                clockLabelsContainer.getChildren().add(labelContainer);

                // init pointer angle
                if (val == time.getHour() % 24 || (val == 24 && time.getHour() % 24 == 0)) {
                    _24HourHoursPointerRotate.setAngle(180 + Math.toDegrees(angle));
                }
            }
        }

        if (_24HourView) {
            return new StackPane(pointerGroup, pointer24HourGroup, clockLabelsContainer);
        } else {
            return new StackPane(pointerGroup, clockLabelsContainer);
        }
    }

    private StackPane createMinutesContent(LocalTime time) {
        // create minutes content
        StackPane minsPointer = new StackPane();
        Circle selectionCircle = new Circle(contentCircleRadius / 6);
        selectionCircle.setFill(selectedColorBlue);

        Circle minCircle = new Circle(selectionCircle.getRadius() / 8);
        minCircle.setFill(Color.rgb(255, 255, 255, 0.87));
        minCircle.setTranslateX(selectionCircle.getRadius() - minCircle.getRadius());
        minCircle.setVisible(time.getMinute() % 5 != 0);
        selectedMinLabel.textProperty().addListener((o, oldVal, newVal) -> {
            if (Integer.parseInt(newVal) % 5 == 0) {
                minCircle.setVisible(false);
            } else {
                minCircle.setVisible(true);
            }
        });


        double shift = 9;
        Line line = new Line(shift, 0, contentCircleRadius, 0);
        line.setFill(selectedColorBlue);
        line.strokeProperty().bind(line.fillProperty());
        line.setStrokeWidth(1.5);
        minsPointer.getChildren().addAll(line, selectionCircle, minCircle);
        StackPane.setAlignment(selectionCircle, Pos.CENTER_LEFT);
        StackPane.setAlignment(minCircle, Pos.CENTER_LEFT);

        Group pointerGroup = new Group();
        pointerGroup.getChildren().add(minsPointer);
        pointerGroup.setTranslateX((-contentCircleRadius + shift) / 2);
        minsPointerRotate = new Rotate(0, contentCircleRadius - shift, selectionCircle.getRadius());
        pointerGroup.getTransforms().add(minsPointerRotate);

        Pane clockLabelsContainer = new Pane();
        // inner circle radius
        double radius = contentCircleRadius - shift - selectionCircle.getRadius();
//        for (int i = 0; i < 12; i++) {
//            StackPane labelContainer = new StackPane();
//            int val = ((i + 3) * 5) % 60;
//            Label label = new Label(String.valueOf(unitConverter.toString(val)));
//            label.setFont(Font.font(ROBOTO, FontWeight.BOLD, 12));
//            // init label color
//            label.setTextFill(val == time.getMinute() ?
//                    Color.rgb(255, 255, 255, 0.87) : Color.rgb(0, 0, 0, 0.87));
//            selectedMinLabel.textProperty().addListener((o, oldVal, newVal) -> {
//                if (Integer.parseInt(newVal) == Integer.parseInt(label.getText())) {
//                    label.setTextFill(Color.rgb(255, 255, 255, 0.87));
//                } else {
//                    label.setTextFill(Color.rgb(0, 0, 0, 0.87));
//                }
//            });
//
//            labelContainer.getChildren().add(label);
//            double labelSize = (selectionCircle.getRadius() / Math.sqrt(2)) * 2;
//            labelContainer.setMinSize(labelSize, labelSize);
//
//            double angle = 2 * i * Math.PI / 12;
//            double xOffset = radius * Math.cos(angle);
//            double yOffset = radius * Math.sin(angle);
//            final double startx = contentCircleRadius + xOffset;
//            final double starty = contentCircleRadius + yOffset;
//            labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2);
//            labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2);
//
//            // add label to the parent node
//            clockLabelsContainer.getChildren().add(labelContainer);
//        }
        for (int i = 1; i <= 60; i++) {
            StackPane labelContainer = new StackPane();
            Label label;
            int val = (i + 15) % 60;
            if(i % 5 == 0) {
                label = new Label(String.valueOf(unitConverter.toString(val)));
                label.setTextFill(val == time.getMinute() ?
                        Color.rgb(255, 255, 255, 0.87) : Color.rgb(0, 0, 0, 0.87));
            }else {
                label = new Label("\u00B7");//u00B7 Middle Dot; u2022 Bullet
                label.setAlignment(Pos.TOP_CENTER);
                label.setUserData(val);
                label.setTextFill(val == time.getMinute() ?
                        Color.rgb(255, 255, 255, 0.87) : Color.rgb(255, 0, 0, 0.87));
            }
            label.setFont(Font.font(ROBOTO, FontWeight.BOLD, 12));
            // init label color

            selectedMinLabel.textProperty().addListener((o, oldVal, newVal) -> {
                if (!label.getText().equals("\u00B7")){
                    if(Integer.parseInt(newVal) == Integer.parseInt(label.getText())) {
                        label.setTextFill(Color.rgb(255, 255, 255, 0.87));
                    }else {
                        label.setTextFill(Color.rgb(0, 0, 0, 0.87));
                    }
                } else {
                    System.out.println("label.data:"+label.getUserData());
                    if(Integer.parseInt(newVal) == Integer.parseInt(label.getUserData().toString())) {
                        label.setTextFill(Color.rgb(255, 255, 255, 0.87));
                    }else {
                        label.setTextFill(Color.rgb(255, 0, 0, 0.87));
                    }
                }
            });

            labelContainer.getChildren().add(label);
            double labelSize = (selectionCircle.getRadius() / Math.sqrt(2)) * 2;
            labelContainer.setMinSize(labelSize, labelSize);

            double angle = 2 * i * Math.PI / 60;
            double angleDegrees = angle * (180/Math.PI);
            System.out.println(i + ") rad = " + angle + "; degree = " + angleDegrees);
            double xOffset = radius * Math.cos(angle);
            double yOffset = radius * Math.sin(angle);
            final double startx = contentCircleRadius + xOffset;
            final double starty = contentCircleRadius + yOffset;
            labelContainer.setLayoutX(startx - labelContainer.getMinWidth() / 2);
            labelContainer.setLayoutY(starty - labelContainer.getMinHeight() / 2);

            // add label to the parent node
            clockLabelsContainer.getChildren().add(labelContainer);
        }

        minsPointerRotate.setAngle(180 + (time.getMinute() + 45) % 60 * Math.toDegrees(2 * Math.PI / 60));

        return new StackPane(pointerGroup, clockLabelsContainer);
    }

    void updateValue() {
        if (is24HourView) {
            LocalTimeStringConverter localTimeStringConverter =
                    new LocalTimeStringConverter(FormatStyle.SHORT, Locale.GERMAN);
            timePicker.setValue(localTimeStringConverter.fromString(selectedHourLabel.getText()
                    + ":" + selectedMinLabel.getText()));
        } else {
            timePicker.setValue(LocalTime.parse(selectedHourLabel.getText() + ":" + selectedMinLabel.getText() + " " + period.get(), DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.ENGLISH)));
        }
    }

    private void swapLabelsColor(Label lbl1, Label lbl2) {
        Paint color = lbl1.getTextFill();
        lbl1.setTextFill(lbl2.getTextFill());
        lbl2.setTextFill(color);
    }

    private void switchTimeUnit(TimeUnit newVal) {
        if (newVal == TimeUnit.HOURS) {
            Timeline fadeout = new Timeline(new KeyFrame(Duration.millis(320),
                    new KeyValue(minutesContent.opacityProperty(),
                            0,
                            Interpolator.EASE_BOTH)));
            Timeline fadein = new Timeline(new KeyFrame(Duration.millis(320),
                    new KeyValue(hoursContent.opacityProperty(),
                            1,
                            Interpolator.EASE_BOTH)));
            new ParallelTransition(fadeout, fadein).play();
        } else {
            Timeline fadeout = new Timeline(new KeyFrame(Duration.millis(320),
                    new KeyValue(hoursContent.opacityProperty(),
                            0,
                            Interpolator.EASE_BOTH)));
            Timeline fadein = new Timeline(new KeyFrame(Duration.millis(320),
                    new KeyValue(minutesContent.opacityProperty(),
                            1,
                            Interpolator.EASE_BOTH)));
            new ParallelTransition(fadeout, fadein).play();
        }
    }

    ObjectProperty<LocalTime> displayedTimeProperty() {
        return selectedTime;
    }
}
