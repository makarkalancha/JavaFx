package datetimepicker.time;

import com.sun.javafx.binding.ExpressionHelper;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import java.lang.reflect.Field;
import java.time.LocalTime;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 16:59
 */
public class TimePickerSkin1 extends ComboBoxPopupControl<LocalTime> {
    private TimePicker1 jfxTimePicker;
    // displayNode is the same as editorNode
    private TextField displayNode;
    private TimePickerContent1 content;
    private TimeDialog1 dialog;

    public TimePickerSkin1(TimePicker1 timePicker) {
        super(timePicker, new TimePickerBehavior1(timePicker));
        this.jfxTimePicker = timePicker;
        try {
            Field helper = timePicker.focusedProperty().getClass().getSuperclass()
                    .getDeclaredField("helper");
            helper.setAccessible(true);
            ExpressionHelper value = (ExpressionHelper) helper.get(timePicker.focusedProperty());
            Field changeListenersField = value.getClass().getDeclaredField("changeListeners");
            changeListenersField.setAccessible(true);
            ChangeListener[] changeListeners = (ChangeListener[]) changeListenersField.get(value);
            // remove parent focus listener to prevent editor class cast exception
            for (int i = changeListeners.length - 1; i > 0; i--) {
                if (changeListeners[i] != null && changeListeners[i].getClass().getName().contains("ComboBoxPopupControl")) {
                    timePicker.focusedProperty().removeListener(changeListeners[i]);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // add focus listener on editor node
        timePicker.focusedProperty().addListener((obj, oldVal, newVal) -> {
            if (getEditor() != null && !newVal) {
                setTextFromTextFieldIntoComboBoxValue();
            }
        });

//        // create calender or clock button
//        clock is set here svg to glyph
        //this is clock image instead of arrow in combobox
//        arrow = new SVGGlyph1(0,
//                "clock",
//                "M512 310.857v256q0 8-5.143 13.143t-13.143 5.143h-182.857q-8 "
//                        + "0-13.143-5.143t-5.143-13.143v-36.571q0-8 "
//                        + "5.143-13.143t13.143-5.143h128v-201.143q0-8 5.143-13.143t13.143-5.143h36.571q8 0 "
//                        + "13.143 5.143t5.143 13.143zM749.714 "
//                        + "512q0-84.571-41.714-156t-113.143-113.143-156-41.714-156 41.714-113.143 "
//                        + "113.143-41.714 156 41.714 156 113.143 113.143 156 41.714 156-41.714 "
//                        + "113.143-113.143 41.714-156zM877.714 512q0 119.429-58.857 220.286t-159.714 "
//                        + "159.714-220.286 58.857-220.286-58.857-159.714-159.714-58.857-220.286 "
//                        + "58.857-220.286 159.714-159.714 220.286-58.857 220.286 58.857 159.714 159.714 "
//                        + "58.857 220.286z",
//                null);
//        ((SVGGlyph1) arrow).setFill(timePicker.getDefaultColor());
//        ((SVGGlyph1) arrow).setSize(20, 20);
//        ImageView clockImg = new ImageView("if_42_311148_edited_32x32.png");
//        clockImg.setFitWidth(20d);
//        clockImg.setFitHeight(20d);
//        BackgroundImage clockImg = new BackgroundImage(
////                new Image("if_42_311148.png", 20d, 20d, true, true),
//                new Image("if_42_311148_edited_32x32.png", 15d, 15d, true, true),
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                new BackgroundSize(20d, 20d, false, false, false, false)
////                BackgroundSize.DEFAULT
//        );
//        BackgroundFill clockImg = new BackgroundFill(
//                Color.RED,
//                new CornerRadii(1),
//                new Insets(0d, 0d, 0d, 0d)
//        );
//
////        arrow = new Button();
////        arrow.setStyle("-fx-background-color: TRANSPARENT; -fx-padding: 1 8 1 8;");
//        arrow = new Pane();
////        arrow.setFocusTraversable(false);
////        arrow.setId("arrow");
//        arrow.setMaxWidth(Region.USE_PREF_SIZE);
//        arrow.setMaxHeight(Region.USE_PREF_SIZE);
//        arrow.setMinSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
//        arrow.setPrefSize(25d, 25d);
//        arrow.setMaxSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
////        arrow.setMouseTransparent(true);
////        ((Button)arrow).setGraphic(clockImg);
//        arrow.setBackground(new Background(clockImg));
////        arrow.setPrefHeight(-1d);
////        arrow.setPrefWidth(-1d);
//
////        arrowButton.setStyle("-fx-background-color: TRANSPARENT; -fx-padding: 1 8 1 8;");
////        arrowButton.getChildren().setAll(arrow);
////        arrowButton = new StackPane();
////        arrowButton.setFocusTraversable(false);
////        arrowButton.setId("arrow-button");
////        arrowButton.getStyleClass().setAll("arrow-button");
//        arrowButton.getChildren().setAll(arrow);
////        arrowButton.setBackground(new Background(clockImg));

//        ((TimeTextField1) getEditor()).setFocusColor(timePicker.getDefaultColor());

        //dialog = new TimeDialog1(null, content, transitionType, overlayClose)
        registerChangeListener(timePicker.converterProperty(), "CONVERTER");
        registerChangeListener(timePicker.valueProperty(), "VALUE");
//        registerChangeListener(timePicker.defaultColorProperty(), "DEFAULT_COLOR");
    }

    @Override
    protected Node getPopupContent() {
        if (content == null) {
            content = new TimePickerContent1(jfxTimePicker);
        }
        return content;
    }

    @Override
    public void show() {
//        if (!jfxTimePicker.isOverLay()) {
        if (false) {
            super.show();
        }
        if (content != null) {
            content.init();
            content.clearFocus();
        }
//        if (dialog == null && jfxTimePicker.isOverLay()) {
        if (dialog == null && true) {
            StackPane dialogParent = jfxTimePicker.getDialogParent();
            if (dialogParent == null) {
                dialogParent = (StackPane) jfxTimePicker.getScene().getRoot();
            }
            dialog = new TimeDialog1(dialogParent, (Region) getPopupContent(),
                    TimeDialog1.DialogTransition.CENTER, true);
            arrowButton.setOnMouseClicked((click) -> {
//                if (jfxTimePicker.isOverLay()) {
                if (true) {
                    StackPane parent = jfxTimePicker.getDialogParent();
                    if (parent == null) {
                        parent = (StackPane) jfxTimePicker.getScene().getRoot();
                    }
                    dialog.show(parent);
                }
            });
        }
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
//        break point here
        /*if ("DEFAULT_COLOR".equals(p)) {
            System.out.println("TimePickerSkin1.handleControlPropertyChanged->DEFAULT_COLOR");
//            ((TimeTextField1) getEditor()).setFocusColor(jfxTimePicker.getDefaultColor());
        } else*/ if ("CONVERTER".equals(p)) {
            System.out.println("TimePickerSkin1.handleControlPropertyChanged->CONVERTER");
            updateDisplayNode();
        } else if ("EDITOR".equals(p)) {
            System.out.println("TimePickerSkin1.handleControlPropertyChanged->EDITOR");
            getEditableInputNode();
        } else if ("SHOWING".equals(p)) {
            if (jfxTimePicker.isShowing()) {
                System.out.println("TimePickerSkin1.handleControlPropertyChanged->SHOWING.show");
                show();
            } else {
                System.out.println("TimePickerSkin1.handleControlPropertyChanged->SHOWING.hide");
                hide();
            }
        } else if ("VALUE".equals(p)) {
            System.out.println("TimePickerSkin1.handleControlPropertyChanged->VALUE");
            updateDisplayNode();
            jfxTimePicker.fireEvent(new ActionEvent());
        } else {
            System.out.println("TimePickerSkin1.handleControlPropertyChanged->else.super");
            super.handleControlPropertyChanged(p);
        }
    }


    @Override
    protected TextField getEditor() {
        return ((TimePicker1) getSkinnable()).getEditor();
    }

    @Override
    protected StringConverter<LocalTime> getConverter() {
        return ((TimePicker1) getSkinnable()).getConverter();
    }

    @Override
    public Node getDisplayNode() {
        if (displayNode == null) {
            displayNode = getEditableInputNode();
            displayNode.getStyleClass().add("time-picker-display-node");
            updateDisplayNode();
        }
        displayNode.setEditable(jfxTimePicker.isEditable());
        return displayNode;
    }

    /*
     * this method is called from the behavior class to make sure
     * DatePicker button is in sync after the popup is being dismissed
     */
    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && jfxTimePicker.isShowing()) {
            jfxTimePicker.hide();
        }
    }
}

