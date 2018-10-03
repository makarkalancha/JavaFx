package datetimepicker.time.v2;

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableProperty;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
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
public class TimePicker2 extends ComboBoxBase<LocalTime> {

    private LocalTime lastValidTime = null;
    private Chronology lastValidChronology = IsoChronology.INSTANCE;

    /**
     * Creates a default TimePicker2 instance with a <code>null</code> date value set.
     */
    public TimePicker2() {
        this(null);

//        valueProperty().addListener(observable -> {
//            LocalTime time = getValue();
////            Chronology chrono = getChronology();
//
//            if (validateTime(chrono, time)) {
//                lastValidTime = time;
//            } else {
//                System.err.println("Restoring value to " +
//                        ((lastValidTime == null) ? "null" : getConverter().toString(lastValidTime)));
//                setValue(lastValidTime);
//            }
//        });

//        chronologyProperty().addListener(observable -> {
//            LocalDate date = getValue();
//            Chronology chrono = getChronology();
//
//            if (validateTime(chrono, date)) {
//                lastValidChronology = chrono;
//                defaultConverter = new LocalDateStringConverter(FormatStyle.SHORT, null, chrono);
//            } else {
//                System.err.println("Restoring value to " + lastValidChronology);
//                setChronology(lastValidChronology);
//            }
//        });
    }

//    private boolean validateTime(Chronology chrono, LocalTime time) {
//        try {
//            if (time != null) {
//                chrono.date(time);
//            }
//            return true;
//        } catch (DateTimeException ex) {
//            System.err.println(ex);
//            return false;
//        }
//    }

    /**
     * Creates a TimePicker2 instance and sets the
     * {@link #valueProperty() value} to the given date.
     *
     * @param localTime to be set as the currently selected date in the TimePicker2. Can be null.
     */
    public TimePicker2(LocalTime localTime) {
        setValue(localTime);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAccessibleRole(AccessibleRole.DATE_PICKER);
        setEditable(true);
    }


    /***************************************************************************
     *                                                                         *
     * Properties                                                                 *
     *                                                                         *
     **************************************************************************/


    /**
     * A custom cell factory can be provided to customize individual
     * day cells in the TimePicker2 popup. Refer to {@link DateCell}
     * and {@link Cell} for more information on cell factories.
     * Example:
     *
     * <pre><code>
     * final Callback&lt;TimePicker2, DateCell&gt; dayCellFactory = new Callback&lt;TimePicker2, DateCell&gt;() {
     *     public DateCell call(final TimePicker2 datePicker) {
     *         return new DateCell() {
     *             &#064;Override public void updateItem(LocalDate item, boolean empty) {
     *                 super.updateItem(item, empty);
     *
     *                 if (MonthDay.from(item).equals(MonthDay.of(9, 25))) {
     *                     setTooltip(new Tooltip("Happy Birthday!"));
     *                     setStyle("-fx-background-color: #ff4444;");
     *                 }
     *                 if (item.equals(LocalDate.now().plusDays(1))) {
     *                     // Tomorrow is too soon.
     *                     setDisable(true);
     *                 }
     *             }
     *         };
     *     }
     * };
     * datePicker.setDayCellFactory(dayCellFactory);
     * </code></pre>
     *
     * @defaultValue null
     */
    private ObjectProperty<Callback<TimePicker2, DateCell>> dayCellFactory;
    public final void setDayCellFactory(Callback<TimePicker2, DateCell> value) {
        dayCellFactoryProperty().set(value);
    }
    public final Callback<TimePicker2, DateCell> getDayCellFactory() {
        return (dayCellFactory != null) ? dayCellFactory.get() : null;
    }
    public final ObjectProperty<Callback<TimePicker2, DateCell>> dayCellFactoryProperty() {
        if (dayCellFactory == null) {
            dayCellFactory = new SimpleObjectProperty<Callback<TimePicker2, DateCell>>(this, "dayCellFactory");
        }
        return dayCellFactory;
    }



    /**
     * The calendar system used for parsing, displaying, and choosing
     * dates in the TimePicker2 control.
     *
     * <p>The default value is returned from a call to
     * {@code Chronology.ofLocale(Locale.getDefault(Locale.Category.FORMAT))}.
     * The default is usually {@link java.time.chrono.IsoChronology} unless
     * provided explicitly in the {@link java.util.Locale} by use of a
     * Locale calendar extension.
     *
     * Setting the value to <code>null</code> will restore the default
     * chronology.
     * @return a property representing the Chronology being used
     */
//    public final ObjectProperty<Chronology> chronologyProperty() {
//        return chronology;
//    }
//    private ObjectProperty<Chronology> chronology =
//            new SimpleObjectProperty<Chronology>(this, "chronology", null);
//    public final Chronology getChronology() {
//        Chronology chrono = chronology.get();
//        if (chrono == null) {
//            try {
//                chrono = Chronology.ofLocale(Locale.getDefault(Locale.Category.FORMAT));
//            } catch (Exception ex) {
//                System.err.println(ex);
//            }
//            if (chrono == null) {
//                chrono = IsoChronology.INSTANCE;
//            }
//            //System.err.println(chrono);
//        }
//        return chrono;
//    }
//    public final void setChronology(Chronology value) {
//        chronology.setValue(value);
//    }


    /**
     * Whether the TimePicker2 popup should display a column showing
     * week numbers.
     *
     * <p>The default value is specified in a resource bundle, and
     * depends on the country of the current locale.
     * @return true if popup should display a column showing
     * week numbers
     */
    public final BooleanProperty showWeekNumbersProperty() {
        if (showWeekNumbers == null) {
            String country = Locale.getDefault(Locale.Category.FORMAT).getCountry();
            boolean localizedDefault =
                    (!country.isEmpty() &&
                            ControlResources.getNonTranslatableString("TimePicker2.showWeekNumbers").contains(country));
            showWeekNumbers = new StyleableBooleanProperty(localizedDefault) {
                @Override public CssMetaData<TimePicker2,Boolean> getCssMetaData() {
                    return StyleableProperties.SHOW_WEEK_NUMBERS;
                }

                @Override public Object getBean() {
                    return TimePicker2.this;
                }

                @Override public String getName() {
                    return "showWeekNumbers";
                }
            };
        }
        return showWeekNumbers;
    }
    private BooleanProperty showWeekNumbers;
    public final void setShowWeekNumbers(boolean value) {
        showWeekNumbersProperty().setValue(value);
    }
    public final boolean isShowWeekNumbers() {
        return showWeekNumbersProperty().getValue();
    }


    // --- string converter
    /**
     * Converts the input text to an object of type LocalDate and vice
     * versa.
     *
     * <p>If not set by the application, the TimePicker2 skin class will
     * set a converter based on a {@link java.time.format.DateTimeFormatter}
     * for the current {@link java.util.Locale} and
     * {@link #chronologyProperty() chronology}. This formatter is
     * then used to parse and display the current date value.
     *
     * Setting the value to <code>null</code> will restore the default
     * converter.
     *
     * <p>Example using an explicit formatter:
     * <pre><code>
     * datePicker.setConverter(new StringConverter&lt;LocalDate&gt;() {
     *     String pattern = "yyyy-MM-dd";
     *     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
     *
     *     {
     *         datePicker.setPromptText(pattern.toLowerCase());
     *     }
     *
     *     {@literal @Override public String toString(LocalDate date) {
     *         if (date != null) {
     *             return dateFormatter.format(date);
     *         } else {
     *             return "";
     *         }
     *     }}
     *
     *     {@literal @Override public LocalDate fromString(String string) {
     *         if (string != null && !string.isEmpty()) {
     *             return LocalDate.parse(string, dateFormatter);
     *         } else {
     *             return null;
     *         }
     *     }}
     * });
     * </code></pre>
     * <p>Example that wraps the default formatter and catches parse exceptions:
     * <pre><code>
     *   final StringConverter&lt;LocalDate&gt; defaultConverter = datePicker.getConverter();
     *   datePicker.setConverter(new StringConverter&lt;LocalDate&gt;() {
     *       &#064;Override public String toString(LocalDate value) {
     *           return defaultConverter.toString(value);
     *       }
     *
     *       &#064;Override public LocalDate fromString(String text) {
     *           try {
     *               return defaultConverter.fromString(text);
     *           } catch (DateTimeParseException ex) {
     *               System.err.println("HelloDatePicker: "+ex.getMessage());
     *               throw ex;
     *           }
     *       }
     *   });
     * </code></pre>
     *
     * <p>The default base year for parsing input containing only two digits for
     * the year is 2000 (see {@link java.time.format.DateTimeFormatter}).  This
     * default is not useful for allowing a person's date of birth to be typed.
     * The following example modifies the converter's fromString() method to
     * allow a two digit year for birth dates up to 99 years in the past.
     * <pre><code>
     *   {@literal @Override public LocalDate fromString(String text) {
     *       if (text != null && !text.isEmpty()) {
     *           Locale locale = Locale.getDefault(Locale.Category.FORMAT);
     *           Chronology chrono = datePicker.getChronology();
     *           String pattern =
     *               DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.SHORT,
     *                                                                    null, chrono, locale);
     *           String prePattern = pattern.substring(0, pattern.indexOf("y"));
     *           String postPattern = pattern.substring(pattern.lastIndexOf("y")+1);
     *           int baseYear = LocalDate.now().getYear() - 99;
     *           DateTimeFormatter df = new DateTimeFormatterBuilder()
     *                       .parseLenient()
     *                       .appendPattern(prePattern)
     *                       .appendValueReduced(ChronoField.YEAR, 2, 2, baseYear)
     *                       .appendPattern(postPattern)
     *                       .toFormatter();
     *           return LocalDate.from(chrono.date(df.parse(text)));
     *       } else {
     *           return null;
     *       }
     *   }}
     * </code></pre>
     *
     * @return the property representing the current LocalDate string converter
     * @see javafx.scene.control.ComboBox#converterProperty
     */
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
     * The editor for the TimePicker2.
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
        return new TimePicker2Skin(this);
//        return null;
    }


    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

    private static final String DEFAULT_STYLE_CLASS = "time-picker";

    private static class StyleableProperties {
        private static final String country =
                Locale.getDefault(Locale.Category.FORMAT).getCountry();
        private static final CssMetaData<TimePicker2, Boolean> SHOW_WEEK_NUMBERS =
                new CssMetaData<TimePicker2, Boolean>("-fx-show-week-numbers",
                        BooleanConverter.getInstance(),
                        (!country.isEmpty() &&
                                ControlResources.getNonTranslatableString("DatePicker.showWeekNumbers").contains(country))) {
                    @Override public boolean isSettable(TimePicker2 n) {
                        return n.showWeekNumbers == null || !n.showWeekNumbers.isBound();
                    }

                    @Override public StyleableProperty<Boolean> getStyleableProperty(TimePicker2 n) {
                        return (StyleableProperty<Boolean>)(WritableValue<Boolean>)n.showWeekNumbersProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                    SHOW_WEEK_NUMBERS
            );
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
        return TimePicker2.class.getResource("/time-picker.css").toExternalForm();
    }

}

