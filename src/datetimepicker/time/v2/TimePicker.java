package datetimepicker.time.v2;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by mcalancea
 * Date: 03 Oct 2018
 * Time: 15:58
 */
public class TimePicker extends ComboBoxBase<LocalTime> {
    private BooleanProperty _24HourView;
    /**
     * Creates a default TimePicker instance with a <code>null</code> time value set.
     */
    public TimePicker() {
        this.defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.ENGLISH);
        this._24HourView = new SimpleBooleanProperty(false);
        this.initialize();
    }

    /**
     * Creates a TimePicker instance and sets the
     * {@link #valueProperty() value} to the given date.
     *
     * @param localTime to be set as the currently selected date in the TimePicker. Can be null.
     */
    public TimePicker(LocalTime localTime) {
        this.defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.ENGLISH);
        this._24HourView = new SimpleBooleanProperty(false);

        this.setValue(localTime);

        this.initialize();
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setAccessibleRole(AccessibleRole.DATE_PICKER);
        this.setEditable(true);
    }


    /***************************************************************************
     *                                                                         *
     * Properties                                                                 *
     *                                                                         *
     **************************************************************************/

    public final ObjectProperty<StringConverter<LocalTime>> converterProperty() { return converter; }
    private ObjectProperty<StringConverter<LocalTime>> converter =
            new SimpleObjectProperty<StringConverter<LocalTime>>(this, "converter", null);
    public final void setConverter(StringConverter<LocalTime> value) { converterProperty().set(value); }
    public final StringConverter<LocalTime> getConverter() {
        StringConverter<LocalTime> converter = converterProperty().get();
        if (converter != null) {
            return converter;
        } else {
            return defaultConverter;
        }
    }

    // Create a symmetric (format/parse) converter with the default locale.
    private StringConverter<LocalTime> defaultConverter =
            new LocalTimeStringConverter(FormatStyle.SHORT, null);


    // --- Editor
    /**
     * The editor for the TimePicker.
     *
     * @see javafx.scene.control.ComboBox#editorProperty
     */
    private ReadOnlyObjectWrapper<TextField> editor;
    public final TextField getEditor() {
        return editorProperty().get();
    }
    public final ReadOnlyObjectProperty<TextField> editorProperty() {
        if (editor == null) {
            editor = new ReadOnlyObjectWrapper<>(this, "editor");
            editor.set(new ComboBoxPopupControl.FakeFocusTextField());
        }
        return editor.getReadOnlyProperty();
    }

    /** {@inheritDoc} */
    @Override protected Skin<?> createDefaultSkin() {
        return new TimePickerSkin(this);
    }


    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

    private static final String DEFAULT_STYLE_CLASS = "time-picker";

    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            Collections.addAll(styleables);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its superclasses.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     * @since JavaFX 8.0
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    /***************************************************************************
     *                                                                         *
     * Accessibility handling                                                  *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override
    public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
        switch (attribute) {
            case DATE: return getValue();
            case TEXT: {
                String accText = getAccessibleText();
                if (accText != null && !accText.isEmpty()) return accText;

                LocalTime time = getValue();
                StringConverter<LocalTime> c = getConverter();
                if (time != null && c != null) {
                    return c.toString(time);
                }
                return "";
            }
            default: return super.queryAccessibleAttribute(attribute, parameters);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return TimePicker.class.getResource("/time-picker.css").toExternalForm();
    }

    public final BooleanProperty _24HourViewProperty() {
        return this._24HourView;
    }

    public final boolean is24HourView() {
        return this._24HourViewProperty().get();
    }

    public final void setIs24HourView(boolean value) {
        this._24HourViewProperty().setValue(Boolean.valueOf(value));
    }
}