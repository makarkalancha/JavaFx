package datetimepicker.time;

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 16:55
 */
public class TextField1 extends TextField {
    private static final String DEFAULT_STYLE_CLASS = "jfx-text-field";
    private ReadOnlyObjectWrapper<ValidatorBase> activeValidator = new ReadOnlyObjectWrapper();
    private ObservableList<ValidatorBase> validators = FXCollections.observableArrayList();
    private StyleableBooleanProperty labelFloat;
    private StyleableObjectProperty<Paint> unFocusColor;
    private StyleableObjectProperty<Paint> focusColor;
    private StyleableBooleanProperty disableAnimation;
    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    public TextField1() {
        this.labelFloat = new SimpleStyleableBooleanProperty(TextField1.StyleableProperties.LABEL_FLOAT, this, "lableFloat", false);
        this.unFocusColor = new SimpleStyleableObjectProperty(TextField1.StyleableProperties.UNFOCUS_COLOR, this, "unFocusColor", Color.rgb(77, 77, 77));
        this.focusColor = new SimpleStyleableObjectProperty(TextField1.StyleableProperties.FOCUS_COLOR, this, "focusColor", Color.valueOf("#4059A9"));
        this.disableAnimation = new SimpleStyleableBooleanProperty(TextField1.StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
        this.initialize();
    }

    public TextField1(String text) {
        super(text);
        this.labelFloat = new SimpleStyleableBooleanProperty(TextField1.StyleableProperties.LABEL_FLOAT, this, "lableFloat", false);
        this.unFocusColor = new SimpleStyleableObjectProperty(TextField1.StyleableProperties.UNFOCUS_COLOR, this, "unFocusColor", Color.rgb(77, 77, 77));
        this.focusColor = new SimpleStyleableObjectProperty(TextField1.StyleableProperties.FOCUS_COLOR, this, "focusColor", Color.valueOf("#4059A9"));
        this.disableAnimation = new SimpleStyleableBooleanProperty(TextField1.StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
        this.initialize();
    }

    protected Skin<?> createDefaultSkin() {
        return new TextFieldSkin1(this);
    }

    private void initialize() {
        this.getStyleClass().add("jfx-text-field");
        if("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
            this.setStyle("-fx-skin: \"com.jfoenix.android.skins.JFXTextFieldSkinAndroid\";");
        }

    }

    public ValidatorBase getActiveValidator() {
        return this.activeValidator == null?null:(ValidatorBase)this.activeValidator.get();
    }

    public ReadOnlyObjectProperty<ValidatorBase> activeValidatorProperty() {
        return this.activeValidator.getReadOnlyProperty();
    }

    public ObservableList<ValidatorBase> getValidators() {
        return this.validators;
    }

    public void setValidators(ValidatorBase... validators) {
        this.validators.addAll(validators);
    }

    public boolean validate() {
        Iterator var1 = this.validators.iterator();

        ValidatorBase validator;
        do {
            if(!var1.hasNext()) {
                this.activeValidator.set((Object)null);
                return true;
            }

            validator = (ValidatorBase)var1.next();
            if(validator.getSrcControl() == null) {
                validator.setSrcControl(this);
            }

            validator.validate();
        } while(!validator.getHasErrors());

        this.activeValidator.set(validator);
        return false;
    }

    public void resetValidation() {
        this.getStyleClass().remove(this.activeValidator.get() == null?"":((ValidatorBase)this.activeValidator.get()).getErrorStyleClass());
        this.pseudoClassStateChanged(ValidatorBase.PSEUDO_CLASS_ERROR, false);
        this.activeValidator.set((Object)null);
    }

    public final StyleableBooleanProperty labelFloatProperty() {
        return this.labelFloat;
    }

    public final boolean isLabelFloat() {
        return this.labelFloatProperty().get();
    }

    public final void setLabelFloat(boolean labelFloat) {
        this.labelFloatProperty().set(labelFloat);
    }

    public Paint getUnFocusColor() {
        return (Paint)(this.unFocusColor == null?Color.rgb(77, 77, 77):(Paint)this.unFocusColor.get());
    }

    public StyleableObjectProperty<Paint> unFocusColorProperty() {
        return this.unFocusColor;
    }

    public void setUnFocusColor(Paint color) {
        this.unFocusColor.set(color);
    }

    public Paint getFocusColor() {
        return (Paint)(this.focusColor == null?Color.valueOf("#4059A9"):(Paint)this.focusColor.get());
    }

    public StyleableObjectProperty<Paint> focusColorProperty() {
        return this.focusColor;
    }

    public void setFocusColor(Paint color) {
        this.focusColor.set(color);
    }

    public final StyleableBooleanProperty disableAnimationProperty() {
        return this.disableAnimation;
    }

    public final Boolean isDisableAnimation() {
        return Boolean.valueOf(this.disableAnimation != null && this.disableAnimationProperty().get());
    }

    public final void setDisableAnimation(Boolean disabled) {
        this.disableAnimationProperty().set(disabled.booleanValue());
    }

    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        if(this.STYLEABLES == null) {
            ArrayList styleables = new ArrayList(Control.getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(TextField.getClassCssMetaData());
            this.STYLEABLES = Collections.unmodifiableList(styleables);
        }

        return this.STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return TextField1.StyleableProperties.CHILD_STYLEABLES;
    }

    private static class StyleableProperties {
        private static final CssMetaData<TextField1, Paint> UNFOCUS_COLOR = new CssMetaData("-jfx-unfocus-color", PaintConverter.getInstance(), Color.valueOf("#A6A6A6")) {
            public boolean isSettable(TextField1 control) {
                return control.unFocusColor == null || !control.unFocusColor.isBound();
            }

            public StyleableProperty<Paint> getStyleableProperty(TextField1 control) {
                return control.unFocusColorProperty();
            }
        };
        private static final CssMetaData<TextField1, Paint> FOCUS_COLOR = new CssMetaData("-jfx-focus-color", PaintConverter.getInstance(), Color.valueOf("#3f51b5")) {
            public boolean isSettable(TextField1 control) {
                return control.focusColor == null || !control.focusColor.isBound();
            }

            public StyleableProperty<Paint> getStyleableProperty(TextField1 control) {
                return control.focusColorProperty();
            }
        };
        private static final CssMetaData<TextField1, Boolean> LABEL_FLOAT = new CssMetaData("-jfx-label-float", BooleanConverter.getInstance(), Boolean.valueOf(false)) {
            public boolean isSettable(TextField1 control) {
                return control.labelFloat == null || !control.labelFloat.isBound();
            }

            public StyleableBooleanProperty getStyleableProperty(TextField1 control) {
                return control.labelFloatProperty();
            }
        };
        private static final CssMetaData<TextField1, Boolean> DISABLE_ANIMATION = new CssMetaData("-jfx-disable-animation", BooleanConverter.getInstance(), Boolean.valueOf(false)) {
            public boolean isSettable(TextField1 control) {
                return control.disableAnimation == null || !control.disableAnimation.isBound();
            }

            public StyleableBooleanProperty getStyleableProperty(TextField1 control) {
                return control.disableAnimationProperty();
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        private StyleableProperties() {
        }

        static {
            ArrayList styleables = new ArrayList(Control.getClassCssMetaData());
            Collections.addAll(styleables, new CssMetaData[]{UNFOCUS_COLOR, FOCUS_COLOR, LABEL_FLOAT, DISABLE_ANIMATION});
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
}
