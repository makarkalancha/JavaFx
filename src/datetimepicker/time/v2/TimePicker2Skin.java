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
public class TimePicker2Skin  extends ComboBoxPopupControl<LocalTime> {

    /***************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final TimePicker2 timePicker;
    private TextField displayNode;
    private TimePicker2Content timePickerContent;

//    private final DatePickerBehavior behavior;



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
    public TimePicker2Skin(final TimePicker2 control) {
        super(control, new TimePicker2Behavior(control));

        this.timePicker = control;

        // install default input map for the control
//        this.behavior = new DatePickerBehavior(control);
//        control.setInputMap(behavior.getInputMap());

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

//        registerChangeListener(control.chronologyProperty(), e -> {
//            updateDisplayNode();
//            timePickerContent = null;
//            popup = null;
//        });
//        registerChangeListener(control.converterProperty(), e -> updateDisplayNode());
//        registerChangeListener(control.dayCellFactoryProperty(), e -> {
//            updateDisplayNode();
//            timePickerContent = null;
//            popup = null;
//        });
//        registerChangeListener(control.showWeekNumbersProperty(), e -> {
//            if (timePickerContent != null) {
//                timePickerContent.updateGrid();
//                timePickerContent.updateWeeknumberDateCells();
//            }
//        });
//        registerChangeListener(control.valueProperty(), e -> {
//            updateDisplayNode();
//            if (timePickerContent != null) {
//                LocalDate date = control.getValue();
//                timePickerContent.displayedYearMonthProperty().set((date != null) ? YearMonth.from(date) : YearMonth.now());
//                timePickerContent.updateValues();
//            }
//            control.fireEvent(new ActionEvent());
//        });
//        registerChangeListener(control.showingProperty(), e -> {
//            if (control.isShowing()) {
//                if (timePickerContent != null) {
//                    LocalTime time = control.getValue();
////                    timePickerContent.displayedYearMonthProperty().set((time != null) ? YearMonth.from(time) : YearMonth.now());
//                    timePicker.setValue(time);
//                }
//                show();
//            } else {
//                hide();
//            }
//        });

        registerChangeListener(timePicker.converterProperty(), "CONVERTER");
        registerChangeListener(timePicker.valueProperty(), "VALUE");

        if (control.isShowing()) {
            show();
        }
    }



    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override public void dispose() {
        super.dispose();

//        if (behavior != null) {
//            behavior.dispose();
//        }
    }

    /** {@inheritDoc} */
    @Override public Node getPopupContent() {
        if (timePickerContent == null) {
            timePickerContent = new TimePicker2Content(timePicker);
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

//        if (timePickerContent != null) {
//            timePickerContent.init();
            timePickerContent.clearFocus();
//        }
        System.out.println("timePicker.isShowing():" + timePicker.isShowing());
    }

    /** {@inheritDoc} */
    @Override protected TextField getEditor() {
        // Use getSkinnable() here because this method is called from
        // the super constructor before timePicker is initialized.
        return ((TimePicker2)getSkinnable()).getEditor();
    }

    /** {@inheritDoc} */
    @Override protected StringConverter<LocalTime> getConverter() {
        return ((TimePicker2)getSkinnable()).getConverter();
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
            System.out.println("TimePickerSkin2.handleControlPropertyChanged->CONVERTER");
            updateDisplayNode();
        } else if ("EDITOR".equals(p)) {
            System.out.println("TimePickerSkin2.handleControlPropertyChanged->EDITOR");
            getEditableInputNode();
        } else if ("SHOWING".equals(p)) {
            if (timePicker.isShowing()) {
                System.out.println("TimePickerSkin2.handleControlPropertyChanged->SHOWING.show");
                show();
            } else {
                System.out.println("TimePickerSkin2.handleControlPropertyChanged->SHOWING.hide");
                hide();
            }
        } else if ("VALUE".equals(p)) {
            System.out.println("TimePickerSkin2.handleControlPropertyChanged->VALUE");
            updateDisplayNode();
            timePicker.fireEvent(new ActionEvent());
        } else {
            System.out.println("TimePickerSkin2.handleControlPropertyChanged->else.super");
            super.handleControlPropertyChanged(p);
        }
    }

    change arrow in clock

    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && timePicker.isShowing()) {
            // Popup was dismissed. Maybe user clicked outside or typed ESCAPE.
            // Make sure DatePicker button is in sync.
            timePicker.hide();
        }
    }
}
