package datetimepicker.time;

import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 14:02
 */
public class TimePicker1 extends ComboBoxBase<LocalTime> {
    private ObjectProperty<StackPane> dialogParent = new SimpleObjectProperty((Object)null);
    private ObjectProperty<StringConverter<LocalTime>> converter = new SimpleObjectProperty(this, "converter", (Object)null);
    private StringConverter<LocalTime> defaultConverter;
    private BooleanProperty _24HourView;
    private ReadOnlyObjectWrapper<TextField> editor;
    private static final String DEFAULT_STYLE_CLASS = "time-picker";
//    private StyleableBooleanProperty overLay;
//    private StyleableObjectProperty<Paint> defaultColor;
    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    public TimePicker1() {
        this.defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.ENGLISH);
        this._24HourView = new SimpleBooleanProperty(false);
//        this.overLay = new SimpleStyleableBooleanProperty(TimePicker1.StyleableProperties.OVERLAY, this, "overLay", false);
//        this.defaultColor = new SimpleStyleableObjectProperty(TimePicker1.StyleableProperties.DEFAULT_COLOR, this, "defaultColor", Color.valueOf("#009688"));
        this.initialize();
    }

    public TimePicker1(LocalTime localTime) {
        this.defaultConverter = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.ENGLISH);
        this._24HourView = new SimpleBooleanProperty(false);
//        this.overLay = new SimpleStyleableBooleanProperty(TimePicker1.StyleableProperties.OVERLAY, this, "overLay", false);
//        this.defaultColor = new SimpleStyleableObjectProperty(TimePicker1.StyleableProperties.DEFAULT_COLOR, this, "defaultColor", Color.valueOf("#009688"));
        this.setValue(localTime);
        this.initialize();
    }

    private void initialize() {
//        this.getStyleClass().add("jfx-time-picker");
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setAccessibleRole(AccessibleRole.DATE_PICKER);
        this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)}));
        this.setEditable(true);
    }

    protected Skin<?> createDefaultSkin() {
        return new TimePickerSkin1(this);
    }

    public final ObjectProperty<StackPane> dialogParentProperty() {
        return this.dialogParent;
    }

    public final StackPane getDialogParent() {
        return (StackPane)this.dialogParentProperty().get();
    }

    public final void setDialogParent(StackPane dialogParent) {
        this.dialogParentProperty().set(dialogParent);
    }

    public final ObjectProperty<StringConverter<LocalTime>> converterProperty() {
        return this.converter;
    }

    public final void setConverter(StringConverter<LocalTime> value) {
        this.converterProperty().set(value);
    }

    public final StringConverter<LocalTime> getConverter() {
        StringConverter converter = (StringConverter)this.converterProperty().get();
        return converter != null?converter:this.defaultConverter;
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

    public final TextField getEditor() {
        return (TextField)this.editorProperty().get();
    }

    public final ReadOnlyObjectProperty<TextField> editorProperty() {
        if(this.editor == null) {
            this.editor = new ReadOnlyObjectWrapper(this, "editor");
            this.editor.set(new ComboBoxPopupControl.FakeFocusTextField());
        }

        return this.editor.getReadOnlyProperty();
    }

//    public final StyleableBooleanProperty overLayProperty() {
//        return this.overLay;
//    }

//    public final boolean isOverLay() {
//        return this.overLay != null && this.overLayProperty().get();
//    }

//    public final void setOverLay(boolean overLay) {
//        this.overLayProperty().set(overLay);
//    }

//    public Paint getDefaultColor() {
//        return (Paint)(this.defaultColor == null?Color.valueOf("#009688"):(Paint)this.defaultColor.get());
//    }

//    public StyleableObjectProperty<Paint> defaultColorProperty() {
//        return this.defaultColor;
//    }

//    public void setDefaultColor(Paint color) {
//        this.defaultColor.set(color);
//    }

//    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
//        if(this.STYLEABLES == null) {
//            ArrayList styleables = new ArrayList(Control.getClassCssMetaData());
//            styleables.addAll(getClassCssMetaData());
//            styleables.addAll(Control.getClassCssMetaData());
//            this.STYLEABLES = Collections.unmodifiableList(styleables);
//        }
//
//        return this.STYLEABLES;
//    }

//    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
//        return TimePicker1.StyleableProperties.CHILD_STYLEABLES;
//    }

//    private static class StyleableProperties {
//        private static final CssMetaData<TimePicker1, Paint> DEFAULT_COLOR =
//                new CssMetaData<TimePicker1, Paint>("-jfx-default-color", PaintConverter.getInstance(), Color.valueOf("#5A5A5A")) {
//
//                    @Override
//                    public boolean isSettable(TimePicker1 styleable) {
//                        return styleable.defaultColor == null || !styleable.defaultColor.isBound();
////                        return false;
//                    }
//
//                    @Override
//                    public StyleableProperty getStyleableProperty(TimePicker1 styleable) {
//                        return styleable.defaultColorProperty();
////                        return null;
//                    }
//                };
//
//        private static final CssMetaData<TimePicker1, Boolean> OVERLAY =
//                new CssMetaData<TimePicker1, Boolean>("-jfx-overlay", BooleanConverter.getInstance(), Boolean.valueOf(false)) {
//
//            @Override
//            public boolean isSettable(TimePicker1 control) {
//                return control.overLay == null || !control.overLay.isBound();
//            }
//
//            @Override
//            public StyleableProperty<Boolean> getStyleableProperty(TimePicker1 styleable) {
//                return styleable.overLayProperty();
//            }
//        };
//        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;
//
//        private StyleableProperties() {
//        }
//
//        static {
//            ArrayList styleables = new ArrayList(Control.getClassCssMetaData());
//            Collections.addAll(styleables, new CssMetaData[]{DEFAULT_COLOR, OVERLAY});
//            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
//        }
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return TimePicker1.class.getResource("/time-picker.css").toExternalForm();
    }
}
