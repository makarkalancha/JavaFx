package datetimepicker.datetime;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.chrono.HijrahChronology;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 11:12
 */
public class DateTimePickerSkin extends ComboBoxPopupControl<LocalDateTime> {

    private DateTimePicker dateTimePicker;
    private TextField displayNode;
    private DateTimePickerContent datePickerContent;

    public DateTimePickerSkin(final DateTimePicker dateTimePicker) {
        super(dateTimePicker, new DateTimePickerBehavior(dateTimePicker));

        this.dateTimePicker = dateTimePicker;

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

        registerChangeListener(dateTimePicker.chronologyProperty(), "CHRONOLOGY");
        registerChangeListener(dateTimePicker.converterProperty(), "CONVERTER");
        registerChangeListener(dateTimePicker.dayCellFactoryProperty(), "DAY_CELL_FACTORY");
        registerChangeListener(dateTimePicker.showWeekNumbersProperty(), "SHOW_WEEK_NUMBERS");
        registerChangeListener(dateTimePicker.valueProperty(), "VALUE");
    }

    @Override public Node getPopupContent() {
        if (datePickerContent == null) {
            if (dateTimePicker.getChronology() instanceof HijrahChronology) {
                datePickerContent = new DateTimePickerHijrahContent(dateTimePicker);
            } else {
                datePickerContent = new DateTimePickerContent(dateTimePicker);
            }
        }

        return datePickerContent;
    }

    @Override protected double computeMinWidth(double height,
                                               double topInset, double rightInset,
                                               double bottomInset, double leftInset) {
        return 50;
    }

    @Override protected void focusLost() {
        // do nothing
    }


    @Override public void show() {
        super.show();
        datePickerContent.clearFocus();
    }

    @Override protected void handleControlPropertyChanged(String p) {

        if ("CHRONOLOGY".equals(p) ||
                "DAY_CELL_FACTORY".equals(p)) {

            updateDisplayNode();
//             if (datePickerContent != null) {
//                 datePickerContent.refresh();
//             }
            datePickerContent = null;
            popup = null;
        } else if ("CONVERTER".equals(p)) {
            updateDisplayNode();
        } else if ("EDITOR".equals(p)) {
            getEditableInputNode();
        } else if ("SHOWING".equals(p)) {
            if (dateTimePicker.isShowing()) {
                if (datePickerContent != null) {
                    LocalDateTime date = dateTimePicker.getValue();
                    datePickerContent.displayedYearMonthProperty().set((date != null) ? YearMonth.from(date) : YearMonth.now());
                    datePickerContent.updateValues();
                }
                show();
            } else {
                hide();
            }
        } else if ("SHOW_WEEK_NUMBERS".equals(p)) {
            if (datePickerContent != null) {
                datePickerContent.updateGrid();
                datePickerContent.updateWeeknumberDateCells();
            }
        } else if ("VALUE".equals(p)) {
            updateDisplayNode();
            if (datePickerContent != null) {
                LocalDateTime date = dateTimePicker.getValue();
                datePickerContent.displayedYearMonthProperty().set((date != null) ? YearMonth.from(date) : YearMonth.now());
                datePickerContent.updateValues();
            }
            dateTimePicker.fireEvent(new ActionEvent());
        } else {
            super.handleControlPropertyChanged(p);
        }
    }

    @Override protected TextField getEditor() {
        // Use getSkinnable() here because this method is called from
        // the super constructor before dateTimePicker is initialized.
        return ((DateTimePicker)getSkinnable()).getEditor();
    }

    @Override protected StringConverter<LocalDateTime> getConverter() {
        return ((DateTimePicker)getSkinnable()).getConverter();
    }

    @Override public Node getDisplayNode() {
        if (displayNode == null) {
            displayNode = getEditableInputNode();
            displayNode.getStyleClass().add("date-picker-display-node");
            updateDisplayNode();
        }
        displayNode.setEditable(dateTimePicker.isEditable());

        return displayNode;
    }

    public void syncWithAutoUpdate() {
        if (!getPopup().isShowing() && dateTimePicker.isShowing()) {
            // Popup was dismissed. Maybe user clicked outside or typed ESCAPE.
            // Make sure DateTimePicker button is in sync.
            dateTimePicker.hide();
        }
    }
}
