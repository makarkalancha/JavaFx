package datetimepicker.time.v2;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

    private final TimePicker2 datePicker;
    private TextField displayNode;
    private DatePickerContent datePickerContent;

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

        this.datePicker = control;

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
//            datePickerContent = null;
//            popup = null;
//        });
//        registerChangeListener(control.converterProperty(), e -> updateDisplayNode());
//        registerChangeListener(control.dayCellFactoryProperty(), e -> {
//            updateDisplayNode();
//            datePickerContent = null;
//            popup = null;
//        });
//        registerChangeListener(control.showWeekNumbersProperty(), e -> {
//            if (datePickerContent != null) {
//                datePickerContent.updateGrid();
//                datePickerContent.updateWeeknumberDateCells();
//            }
//        });
//        registerChangeListener(control.valueProperty(), e -> {
//            updateDisplayNode();
//            if (datePickerContent != null) {
//                LocalDate date = control.getValue();
//                datePickerContent.displayedYearMonthProperty().set((date != null) ? YearMonth.from(date) : YearMonth.now());
//                datePickerContent.updateValues();
//            }
//            control.fireEvent(new ActionEvent());
//        });
//        registerChangeListener(control.showingProperty(), e -> {
//            if (control.isShowing()) {
//                if (datePickerContent != null) {
//                    LocalDate date = control.getValue();
//                    datePickerContent.displayedYearMonthProperty().set((date != null) ? YearMonth.from(date) : YearMonth.now());
//                    datePickerContent.updateValues();
//                }
//                show();
//            } else {
//                hide();
//            }
//        });

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
        if (datePickerContent == null) {
//            if (datePicker.getChronology() instanceof HijrahChronology) {
////                datePickerContent = new DatePickerHijrahContent(datePicker);
//            } else {
////                datePickerContent = new DatePickerContent(datePicker);
//            }
        }

        return datePickerContent;
    }

    /** {@inheritDoc} */
    @Override protected double computeMinWidth(double height,
                                               double topInset, double rightInset,
                                               double bottomInset, double leftInset) {
        return 50;
    }

    /** {@inheritDoc} */
    @Override public void show() {
        super.show();
//        datePickerContent.clearFocus();
    }

    /** {@inheritDoc} */
    @Override protected TextField getEditor() {
        // Use getSkinnable() here because this method is called from
        // the super constructor before datePicker is initialized.
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
        displayNode.setEditable(datePicker.isEditable());

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
}
