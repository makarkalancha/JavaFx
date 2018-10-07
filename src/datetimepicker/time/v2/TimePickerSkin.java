package datetimepicker.time.v2;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalTime;

/**
 * Created by mcalancea
 * Date: 03 Oct 2018
 * Time: 18:11
 */
public class TimePickerSkin extends ComboBoxPopupControl<LocalTime> {

    /***************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final TimePicker timePicker;
    private TextField displayNode;
    private TimePickerContent timePickerContent;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new DatePickerSkin instance, installing the necessary child
     * nodes into the Control {@link Control#getChildren() children} list, as
     * well as the necessary input mappings for handling key, mouse, etc events.
     *
     * @param control The control that this skin should be installed onto.
     */
    public TimePickerSkin(final TimePicker control) {
        super(control, new TimePickerBehavior(control));

        this.timePicker = control;

        // The "arrow" is actually a rectangular svg icon resembling a calendar.
        // Round the size of the icon to whole integers to get sharp edges.
        arrow.paddingProperty().addListener(new InvalidationListener() {
            // This boolean protects against unwanted recursion.
            private boolean rounding = false;
            @Override public void invalidated(Observable observable) {
                if (!rounding) {
                    Insets padding = arrow.getPadding();
                    Insets rounded = new Insets(Math.round(padding.getTop()), Math.round(padding.getRight()),
                            Math.round(padding.getBottom()), Math.round(padding.getLeft()));
                    if (!rounded.equals(padding)) {
                        rounding = true;
                        arrow.setPadding(rounded);
                        rounding = false;
                    }
                }
            }
        });

        registerChangeListener(timePicker.converterProperty(), "CONVERTER");
        registerChangeListener(timePicker.valueProperty(), "VALUE");

        if (control.isShowing()) {
            show();
        }
    }

    /** {@inheritDoc} */
    @Override public Node getPopupContent() {
        if (timePickerContent == null) {
            timePickerContent = new TimePickerContent(timePicker);
        }

        return timePickerContent;
    }

    /** {@inheritDoc} */
    @Override protected double computeMinWidth(double height,
                                               double topInset, double rightInset,
                                               double bottomInset, double leftInset) {
        return 50;
    }

    /** {@inheritDoc} */
    @Override
    public void show() {
        super.show();
        timePickerContent.clearFocus();
    }

    /** {@inheritDoc} */
    @Override protected TextField getEditor() {
        // Use getSkinnable() here because this method is called from
        // the super constructor before timePicker is initialized.
        return ((TimePicker)getSkinnable()).getEditor();
    }

    /** {@inheritDoc} */
    @Override protected StringConverter<LocalTime> getConverter() {
        return ((TimePicker)getSkinnable()).getConverter();
    }

    /** {@inheritDoc} */
    @Override public Node getDisplayNode() {
        if (displayNode == null) {
            displayNode = getEditableInputNode();
            displayNode.getStyleClass().add("date-picker-display-node");
            updateDisplayNode();
        }
        displayNode.setEditable(timePicker.isEditable());

        return displayNode;
    }



    /***************************************************************************
     *                                                                         *
     * Private implementation                                                  *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override protected void focusLost() {
        // do nothing
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
        if ("CONVERTER".equals(p)) {
            updateDisplayNode();
        } else if ("EDITOR".equals(p)) {
            getEditableInputNode();
        } else if ("SHOWING".equals(p)) {
            if (timePicker.isShowing()) {
                show();
            } else {
                hide();
            }
        } else if ("VALUE".equals(p)) {
            updateDisplayNode();
            timePicker.fireEvent(new ActionEvent());
        } else {
            super.handleControlPropertyChanged(p);
        }
    }

    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && timePicker.isShowing()) {
            // Popup was dismissed. Maybe user clicked outside or typed ESCAPE.
            // Make sure DatePicker button is in sync.
            timePicker.hide();
        }
    }
}
