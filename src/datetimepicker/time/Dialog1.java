package datetimepicker.time;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 16:59
 */
@DefaultProperty("content")
public class Dialog1 {
        private StackPane contentHolder;
        private double offsetX;
        private double offsetY;
        private StackPane dialogContainer;
        private Region content;
        private Transition animation;
        EventHandler<? super MouseEvent> closeHandler;
        private BooleanProperty overlayClose;
        private static final String DEFAULT_STYLE_CLASS = "jfx-dialog";
        private StyleableObjectProperty<DialogTransition> transitionType;
        private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        private ObjectProperty<EventHandler<? super DialogEvent1>> onDialogClosedProperty;
        private ObjectProperty<EventHandler<? super DialogEvent1>> onDialogOpenedProperty;

        public Dialog1() {
            this((StackPane)null, (Region)null, Dialog1.DialogTransition.CENTER);
        }

        public Dialog1(StackPane dialogContainer, Region content, Dialog1.DialogTransition transitionType) {
            this.offsetX = 0.0D;
            this.offsetY = 0.0D;
            this.closeHandler = JFXDialog$$Lambda$1.lambdaFactory$(this);
            this.overlayClose = new SimpleBooleanProperty(true);
            this.transitionType = new SimpleStyleableObjectProperty(Dialog1.StyleableProperties.DIALOG_TRANSITION, this, "dialogTransition", Dialog1.DialogTransition.CENTER);
            this.onDialogClosedProperty = new SimpleObjectProperty(JFXDialog$$Lambda$2.lambdaFactory$());
            this.onDialogOpenedProperty = new SimpleObjectProperty(JFXDialog$$Lambda$3.lambdaFactory$());
            this.initialize();
            this.setContent(content);
            this.setDialogContainer(dialogContainer);
            this.transitionType.set(transitionType);
            this.initChangeListeners();
        }

        public Dialog1(StackPane dialogContainer, Region content, Dialog1.DialogTransition transitionType, boolean overlayClose) {
            this.offsetX = 0.0D;
            this.offsetY = 0.0D;
            this.closeHandler = JFXDialog$$Lambda$4.lambdaFactory$(this);
            this.overlayClose = new SimpleBooleanProperty(true);
            this.transitionType = new SimpleStyleableObjectProperty(Dialog1.StyleableProperties.DIALOG_TRANSITION, this, "dialogTransition", Dialog1.DialogTransition.CENTER);
            this.onDialogClosedProperty = new SimpleObjectProperty(JFXDialog$$Lambda$5.lambdaFactory$());
            this.onDialogOpenedProperty = new SimpleObjectProperty(JFXDialog$$Lambda$6.lambdaFactory$());
            this.initialize();
            this.setOverlayClose(overlayClose);
            this.setContent(content);
            this.setDialogContainer(dialogContainer);
            this.transitionType.set(transitionType);
            this.initChangeListeners();
        }

        private void initChangeListeners() {
            this.overlayCloseProperty().addListener(JFXDialog$$Lambda$7.lambdaFactory$(this));
        }

        private void initialize() {
            this.setVisible(false);
            this.getStyleClass().add("jfx-dialog");
            this.transitionType.addListener(JFXDialog$$Lambda$8.lambdaFactory$(this));
            this.contentHolder = new StackPane();
            this.contentHolder.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.WHITE, new CornerRadii(2.0D), (Insets)null)}));
            JFXDepthManager.setDepth(this.contentHolder, 4);
            this.contentHolder.setPickOnBounds(false);
            this.contentHolder.setMaxSize(-1.0D / 0.0, -1.0D / 0.0);
            this.getChildren().add(this.contentHolder);
            this.getStyleClass().add("jfx-dialog-overlay-pane");
            StackPane.setAlignment(this.contentHolder, Pos.CENTER);
            this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.rgb(0, 0, 0, 0.1D), (CornerRadii)null, (Insets)null)}));
            if(this.overlayClose.get()) {
                this.addEventHandler(MouseEvent.MOUSE_PRESSED, this.closeHandler);
            }

            this.contentHolder.addEventHandler(MouseEvent.ANY, JFXDialog$$Lambda$9.lambdaFactory$());
        }

        public StackPane getDialogContainer() {
            return this.dialogContainer;
        }

        public void setDialogContainer(StackPane dialogContainer) {
            if(dialogContainer != null) {
                this.dialogContainer = dialogContainer;
                if(this.dialogContainer.getChildren().indexOf(this) == -1 || this.dialogContainer.getChildren().indexOf(this) != this.dialogContainer.getChildren().size() - 1) {
                    this.dialogContainer.getChildren().remove(this);
                    this.dialogContainer.getChildren().add(this);
                }

                this.offsetX = this.getParent().getBoundsInLocal().getWidth();
                this.offsetY = this.getParent().getBoundsInLocal().getHeight();
                this.animation = this.getShowAnimation((Dialog1.DialogTransition)this.transitionType.get());
            }

        }

        public Region getContent() {
            return this.content;
        }

        public void setContent(Region content) {
            if(content != null) {
                this.content = content;
                this.content.setPickOnBounds(false);
                this.contentHolder.getChildren().add(content);
            }

        }

        public final BooleanProperty overlayCloseProperty() {
            return this.overlayClose;
        }

        public final boolean isOverlayClose() {
            return this.overlayCloseProperty().get();
        }

        public final void setOverlayClose(boolean overlayClose) {
            this.overlayCloseProperty().set(overlayClose);
        }

        public void show(StackPane dialogContainer) {
            this.setDialogContainer(dialogContainer);
            this.animation.play();
        }

        public void show() {
            if(this.dialogContainer == null) {
                System.err.println("ERROR: Dialog1 container is not set!");
            } else {
                this.setDialogContainer(this.dialogContainer);
                this.animation.play();
            }
        }

        public void close() {
            this.animation.setRate(-1.0D);
            this.animation.play();
            this.animation.setOnFinished(JFXDialog$$Lambda$10.lambdaFactory$(this));
        }

        private Transition getShowAnimation(Dialog1.DialogTransition transitionType) {
            Object animation = null;
            if(this.contentHolder != null) {
                switch(null.$SwitchMap$com$jfoenix$controls$JFXDialog$DialogTransition[transitionType.ordinal()]) {
                    case 1:
                        this.contentHolder.setScaleX(1.0D);
                        this.contentHolder.setScaleY(1.0D);
                        this.contentHolder.setTranslateX(-this.offsetX);
                        animation = new Dialog1.LeftTransition();
                        break;
                    case 2:
                        this.contentHolder.setScaleX(1.0D);
                        this.contentHolder.setScaleY(1.0D);
                        this.contentHolder.setTranslateX(this.offsetX);
                        animation = new Dialog1.RightTransition();
                        break;
                    case 3:
                        this.contentHolder.setScaleX(1.0D);
                        this.contentHolder.setScaleY(1.0D);
                        this.contentHolder.setTranslateY(-this.offsetY);
                        animation = new Dialog1.TopTransition();
                        break;
                    case 4:
                        this.contentHolder.setScaleX(1.0D);
                        this.contentHolder.setScaleY(1.0D);
                        this.contentHolder.setTranslateY(this.offsetY);
                        animation = new Dialog1.BottomTransition();
                        break;
                    default:
                        this.contentHolder.setScaleX(0.0D);
                        this.contentHolder.setScaleY(0.0D);
                        animation = new Dialog1.CenterTransition();
                }
            }

            if(animation != null) {
                ((Transition)animation).setOnFinished(JFXDialog$$Lambda$11.lambdaFactory$(this));
            }

            return (Transition)animation;
        }

        private void resetProperties() {
            this.setVisible(false);
            this.contentHolder.setTranslateX(0.0D);
            this.contentHolder.setTranslateY(0.0D);
            this.contentHolder.setScaleX(1.0D);
            this.contentHolder.setScaleY(1.0D);
        }

        public Dialog1.DialogTransition getTransitionType() {
            return this.transitionType == null?Dialog1.DialogTransition.CENTER:(Dialog1.DialogTransition)this.transitionType.get();
        }

        public StyleableObjectProperty<Dialog1.DialogTransition> transitionTypeProperty() {
            return this.transitionType;
        }

        public void setTransitionType(Dialog1.DialogTransition transition) {
            this.transitionType.set(transition);
        }

        public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
            if(this.STYLEABLES == null) {
                ArrayList styleables = new ArrayList(Parent.getClassCssMetaData());
                styleables.addAll(getClassCssMetaData());
                styleables.addAll(StackPane.getClassCssMetaData());
                this.STYLEABLES = Collections.unmodifiableList(styleables);
            }

            return this.STYLEABLES;
        }

        public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
            return Dialog1.StyleableProperties.CHILD_STYLEABLES;
        }

        public void setOnDialogClosed(EventHandler<? super DialogEvent1> handler) {
            this.onDialogClosedProperty.set(handler);
        }

        public EventHandler<? super DialogEvent1> getOnDialogClosed() {
            return (EventHandler)this.onDialogClosedProperty.get();
        }

        public void setOnDialogOpened(EventHandler<? super DialogEvent1> handler) {
            this.onDialogOpenedProperty.set(handler);
        }

        public EventHandler<? super DialogEvent1> getOnDialogOpened() {
            return (EventHandler)this.onDialogOpenedProperty.get();
        }

        private static class StyleableProperties {
            private static final CssMetaData<Dialog1, Dialog1.DialogTransition> DIALOG_TRANSITION;
            private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

            private StyleableProperties() {
            }

            static {
                DIALOG_TRANSITION = new CssMetaData("-jfx-dialog-transition", DialogTransitionConverter.getInstance(), Dialog1.DialogTransition.CENTER) {
                    public boolean isSettable(Dialog1 control) {
                        return control.transitionType == null || !control.transitionType.isBound();
                    }

                    public StyleableProperty<DialogTransition> getStyleableProperty(Dialog1 control) {
                        return control.transitionTypeProperty();
                    }
                };
                ArrayList styleables = new ArrayList(Parent.getClassCssMetaData());
                Collections.addAll(styleables, new CssMetaData[]{DIALOG_TRANSITION});
                CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
            }
        }

        private class CenterTransition extends CachedTransition {
            public CenterTransition() {
                super(Dialog1.this.contentHolder, new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.scaleXProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.contentHolder.scaleYProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(false), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(10.0D), new KeyValue[]{new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(true), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1000.0D), new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.scaleXProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.contentHolder.scaleYProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})}));
                this.setCycleDuration(Duration.seconds(0.4D));
                this.setDelay(Duration.seconds(0.0D));
            }
        }

        private class BottomTransition extends CachedTransition {
            public BottomTransition() {
                super(Dialog1.this.contentHolder, new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateYProperty(), Double.valueOf(Dialog1.this.offsetY), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(false), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(10.0D), new KeyValue[]{new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(true), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1000.0D), new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateYProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})}));
                this.setCycleDuration(Duration.seconds(0.4D));
                this.setDelay(Duration.seconds(0.0D));
            }
        }

        private class TopTransition extends CachedTransition {
            public TopTransition() {
                super(Dialog1.this.contentHolder, new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateYProperty(), Double.valueOf(-Dialog1.this.offsetY), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(false), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(10.0D), new KeyValue[]{new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(true), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1000.0D), new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateYProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})}));
                this.setCycleDuration(Duration.seconds(0.4D));
                this.setDelay(Duration.seconds(0.0D));
            }
        }

        private class RightTransition extends CachedTransition {
            public RightTransition() {
                super(Dialog1.this.contentHolder, new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateXProperty(), Double.valueOf(Dialog1.this.offsetX), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(false), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(10.0D), new KeyValue[]{new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(true), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1000.0D), new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateXProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})}));
                this.setCycleDuration(Duration.seconds(0.4D));
                this.setDelay(Duration.seconds(0.0D));
            }
        }

        private class LeftTransition extends CachedTransition {
            public LeftTransition() {
                super(Dialog1.this.contentHolder, new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateXProperty(), Double.valueOf(-Dialog1.this.offsetX), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(false), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(10.0D), new KeyValue[]{new KeyValue(Dialog1.this.visibleProperty(), Boolean.valueOf(true), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1000.0D), new KeyValue[]{new KeyValue(Dialog1.this.contentHolder.translateXProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(Dialog1.this.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})}));
                this.setCycleDuration(Duration.seconds(0.4D));
                this.setDelay(Duration.seconds(0.0D));
            }
        }

        public static enum DialogTransition {
            CENTER,
            TOP,
            RIGHT,
            BOTTOM,
            LEFT;

            private DialogTransition() {
            }
        }
    }
