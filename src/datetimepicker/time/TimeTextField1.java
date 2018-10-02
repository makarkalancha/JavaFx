package datetimepicker.time;

import javafx.scene.control.TextField;

/**
 * Created by mcalancea
 * Date: 02 Oct 2018
 * Time: 11:56
 */
public class TimeTextField1 extends TextField {

//    /**
//     * {@inheritDoc}
//     */
//    public TimeTextField1() {
//        initialize();
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public TimeTextField1(String text) {
//        super(text);
//        initialize();
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected Skin<?> createDefaultSkin() {
//        return new TimeTextField1Skin<>(this);
//    }
//
//    private void initialize() {
//        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
//        if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
//            this.setStyle("-fx-skin: \"com.jfoenix.android.skins.TimeTextField1SkinAndroid\";");
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public String getUserAgentStylesheet() {
//        return USER_AGENT_STYLESHEET;
//    }
//
//    /***************************************************************************
//     *                                                                         *
//     * Properties                                                              *
//     *                                                                         *
//     **************************************************************************/
//    /**
//     * wrapper for validation properties / methods
//     */
//    protected ValidationControl validationControl = new ValidationControl(this);
//
//    @Override
//    public TimeValidatorBase1 getActiveValidator() {
//        return validationControl.getActiveValidator();
//    }
//
//    @Override
//    public ReadOnlyObjectProperty<TimeValidatorBase1> activeValidatorProperty() {
//        return validationControl.activeValidatorProperty();
//    }
//
//    @Override
//    public ObservableList<TimeValidatorBase1> getValidators() {
//        return validationControl.getValidators();
//    }
//
//    @Override
//    public void setValidators(TimeValidatorBase1... validators) {
//        validationControl.setValidators(validators);
//    }
//
//    @Override
//    public boolean validate() {
//        return validationControl.validate();
//    }
//
//    @Override
//    public void resetValidation() {
//        validationControl.resetValidation();
//    }
//
//    /***************************************************************************
//     *                                                                         *
//     * Styleable Properties                                                    *
//     *                                                                         *
//     **************************************************************************/
//
//    /**
//     * Initialize the style class to 'jfx-text-field'.
//     * <p>
//     * This is the selector class from which CSS can be used to style
//     * this control.
//     */
//    private static final String DEFAULT_STYLE_CLASS = "jfx-text-field";
//    private static final String USER_AGENT_STYLESHEET = TimeTextField1.class.getResource("/css/controls/jfx-text-field.css").toExternalForm();
//
//
//    /**
//     * set true to show a float the prompt text when focusing the field
//     */
//    private StyleableBooleanProperty labelFloat = new SimpleStyleableBooleanProperty(StyleableProperties.LABEL_FLOAT,
//            TimeTextField1.this,
//            "lableFloat",
//            false);
//
//    @Override
//    public final StyleableBooleanProperty labelFloatProperty() {
//        return this.labelFloat;
//    }
//
//    @Override
//    public final boolean isLabelFloat() {
//        return this.labelFloatProperty().get();
//    }
//
//    @Override
//    public final void setLabelFloat(final boolean labelFloat) {
//        this.labelFloatProperty().set(labelFloat);
//    }
//
//    /**
//     * default color used when the field is unfocused
//     */
//    private StyleableObjectProperty<Paint> unFocusColor = new SimpleStyleableObjectProperty<>(StyleableProperties.UNFOCUS_COLOR,
//            TimeTextField1.this,
//            "unFocusColor",
//            Color.rgb(77,
//                    77,
//                    77));
//
//    @Override
//    public Paint getUnFocusColor() {
//        return unFocusColor == null ? Color.rgb(77, 77, 77) : unFocusColor.get();
//    }
//
//    @Override
//    public StyleableObjectProperty<Paint> unFocusColorProperty() {
//        return this.unFocusColor;
//    }
//
//    @Override
//    public void setUnFocusColor(Paint color) {
//        this.unFocusColor.set(color);
//    }
//
//    /**
//     * default color used when the field is focused
//     */
//    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(StyleableProperties.FOCUS_COLOR,
//            TimeTextField1.this,
//            "focusColor",
//            Color.valueOf("#4059A9"));
//
//    @Override
//    public Paint getFocusColor() {
//        return focusColor == null ? Color.valueOf("#4059A9") : focusColor.get();
//    }
//
//    @Override
//    public StyleableObjectProperty<Paint> focusColorProperty() {
//        return this.focusColor;
//    }
//
//    @Override
//    public void setFocusColor(Paint color) {
//        this.focusColor.set(color);
//    }
//
//    /**
//     * disable animation on validation
//     */
//    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(StyleableProperties.DISABLE_ANIMATION,
//            TimeTextField1.this,
//            "disableAnimation",
//            false);
//
//    @Override
//    public final StyleableBooleanProperty disableAnimationProperty() {
//        return this.disableAnimation;
//    }
//
//    @Override
//    public final Boolean isDisableAnimation() {
//        return disableAnimation != null && this.disableAnimationProperty().get();
//    }
//
//    @Override
//    public final void setDisableAnimation(final Boolean disabled) {
//        this.disableAnimationProperty().set(disabled);
//    }
//
//
//    private static class StyleableProperties {
//        private static final CssMetaData<TimeTextField1, Paint> UNFOCUS_COLOR = new CssMetaData<TimeTextField1, Paint>(
//                "-jfx-unfocus-color",
//                PaintConverter.getInstance(),
//                Color.valueOf("#A6A6A6")) {
//            @Override
//            public boolean isSettable(TimeTextField1 control) {
//                return control.unFocusColor == null || !control.unFocusColor.isBound();
//            }
//
//            @Override
//            public StyleableProperty<Paint> getStyleableProperty(TimeTextField1 control) {
//                return control.unFocusColorProperty();
//            }
//        };
//        private static final CssMetaData<TimeTextField1, Paint> FOCUS_COLOR = new CssMetaData<TimeTextField1, Paint>(
//                "-jfx-focus-color",
//                PaintConverter.getInstance(),
//                Color.valueOf("#3f51b5")) {
//            @Override
//            public boolean isSettable(TimeTextField1 control) {
//                return control.focusColor == null || !control.focusColor.isBound();
//            }
//
//            @Override
//            public StyleableProperty<Paint> getStyleableProperty(TimeTextField1 control) {
//                return control.focusColorProperty();
//            }
//        };
//        private static final CssMetaData<TimeTextField1, Boolean> LABEL_FLOAT = new CssMetaData<TimeTextField1, Boolean>(
//                "-jfx-label-float",
//                BooleanConverter.getInstance(),
//                false) {
//            @Override
//            public boolean isSettable(TimeTextField1 control) {
//                return control.labelFloat == null || !control.labelFloat.isBound();
//            }
//
//            @Override
//            public StyleableBooleanProperty getStyleableProperty(TimeTextField1 control) {
//                return control.labelFloatProperty();
//            }
//        };
//
//        private static final CssMetaData<TimeTextField1, Boolean> DISABLE_ANIMATION =
//                new CssMetaData<TimeTextField1, Boolean>("-jfx-disable-animation",
//                        BooleanConverter.getInstance(), false) {
//                    @Override
//                    public boolean isSettable(TimeTextField1 control) {
//                        return control.disableAnimation == null || !control.disableAnimation.isBound();
//                    }
//
//                    @Override
//                    public StyleableBooleanProperty getStyleableProperty(TimeTextField1 control) {
//                        return control.disableAnimationProperty();
//                    }
//                };
//
//
//        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;
//
//        static {
//            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
//                    Control.getClassCssMetaData());
//            Collections.addAll(styleables, UNFOCUS_COLOR, FOCUS_COLOR, LABEL_FLOAT, DISABLE_ANIMATION);
//            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
//        }
//    }
//
//    // inherit the styleable properties from parent
//    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
//
//    @Override
//    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
//        if (STYLEABLES == null) {
//            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
//                    Control.getClassCssMetaData());
//            styleables.addAll(getClassCssMetaData());
//            styleables.addAll(TextField.getClassCssMetaData());
//            STYLEABLES = Collections.unmodifiableList(styleables);
//        }
//        return STYLEABLES;
//    }
//
//    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
//        return StyleableProperties.CHILD_STYLEABLES;
//    }
}
