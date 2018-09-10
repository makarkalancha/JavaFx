package datetimepicker.time;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.LocalTime;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 16:59
 */
public class TimePickerSkin1  extends ComboBoxPopupControl<LocalTime> {
    private TimePicker1 jfxTimePicker;
    private TextField editorNode;
    private TextField displayNode;
    private JFXTimePickerContent content;
    private Dialog dialog;

    public JFXTimePickerSkin(TimePicker1 timePicker) {
        super(timePicker, new TimePickerBehavior(timePicker));
        this.jfxTimePicker = timePicker;
        this.editorNode = new TextField();
        this.editorNode.focusColorProperty().bind(timePicker.defaultColorProperty());
        this.editorNode.setOnAction(JFXTimePickerSkin$$Lambda$1.lambdaFactory$());
        this.editorNode.focusedProperty().addListener(JFXTimePickerSkin$$Lambda$2.lambdaFactory$(this));
        this.arrow = new SVGGlyph1(0, "clock", "M512 310.857v256q0 8-5.143 13.143t-13.143 5.143h-182.857q-8 0-13.143-5.143t-5.143-13.143v-36.571q0-8 5.143-13.143t13.143-5.143h128v-201.143q0-8 5.143-13.143t13.143-5.143h36.571q8 0 13.143 5.143t5.143 13.143zM749.714 512q0-84.571-41.714-156t-113.143-113.143-156-41.714-156 41.714-113.143 113.143-41.714 156 41.714 156 113.143 113.143 156 41.714 156-41.714 113.143-113.143 41.714-156zM877.714 512q0 119.429-58.857 220.286t-159.714 159.714-220.286 58.857-220.286-58.857-159.714-159.714-58.857-220.286 58.857-220.286 159.714-159.714 220.286-58.857 220.286 58.857 159.714 159.714 58.857 220.286z", Color.BLACK);
        ((SVGGlyph)this.arrow).fillProperty().bind(timePicker.defaultColorProperty());
        ((SVGGlyph)this.arrow).setSize(20.0D, 20.0D);
        this.arrowButton.getChildren().setAll(new Node[]{this.arrow});
        this.arrowButton.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)}));
        this.registerChangeListener(timePicker.converterProperty(), "CONVERTER");
        this.registerChangeListener(timePicker.valueProperty(), "VALUE");
    }

    protected Node getPopupContent() {
        if(this.content == null) {
            this.content = new JFXTimePickerContent(this.jfxTimePicker);
        }

        return this.content;
    }

    public void show() {
        if(!this.jfxTimePicker.isOverLay()) {
            super.show();
        }

        if(this.content != null) {
            this.content.init();
            this.content.clearFocus();
        }

        if(this.jfxTimePicker.isOverLay() && this.dialog == null) {
            StackPane dialogParent = this.jfxTimePicker.getDialogParent();
            if(dialogParent == null) {
                dialogParent = (StackPane)((ComboBoxBase)this.getSkinnable()).getScene().getRoot();
            }

            this.dialog = new JFXDialog(dialogParent, (Region)this.getPopupContent(), DialogTransition.CENTER, true);
            this.arrowButton.setOnMouseClicked(JFXTimePickerSkin$$Lambda$3.lambdaFactory$(this));
        }

    }

    protected void handleControlPropertyChanged(String p) {
        if("CONVERTER".equals(p)) {
            this.updateDisplayNode();
        } else if("EDITOR".equals(p)) {
            this.getEditableInputNode();
        } else if("SHOWING".equals(p)) {
            if(this.jfxTimePicker.isShowing()) {
                this.show();
            } else {
                this.hide();
            }
        } else if("VALUE".equals(p)) {
            this.updateDisplayNode();
            this.jfxTimePicker.fireEvent(new ActionEvent());
        } else {
            super.handleControlPropertyChanged(p);
        }

    }

    protected TextField getEditor() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        if(caller.getClassName().equals(this.getClass().getName())) {
            caller = Thread.currentThread().getStackTrace()[3];
        }

        boolean parentListenerCall = caller.getMethodName().contains("lambda") && caller.getClassName().equals(this.getClass().getSuperclass().getName());
        return parentListenerCall?null:this.editorNode;
    }

    protected StringConverter<LocalTime> getConverter() {
        return this.jfxTimePicker.getConverter();
    }

    public Node getDisplayNode() {
        if(this.displayNode == null) {
            this.displayNode = this.getEditableInputNode();
            this.displayNode.getStyleClass().add("time-picker-display-node");
            this.updateDisplayNode();
        }

        this.displayNode.setEditable(this.jfxTimePicker.isEditable());
        return this.displayNode;
    }

    public void syncWithAutoUpdate() {
        if(!this.getPopup().isShowing() && this.jfxTimePicker.isShowing()) {
            this.jfxTimePicker.hide();
        }

    }
}

