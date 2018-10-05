package datetimepicker.time;

import javafx.animation.Transition;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mcalancea
 * Date: 02 Oct 2018
 * Time: 12:35
 */
@DefaultProperty(value = "content")
public class TimeDialog1 extends StackPane {

    //	public static enum JFXDialogLayout{PLAIN, HEADING, ACTIONS, BACKDROP};
    public enum DialogTransition {
        CENTER, TOP, RIGHT, BOTTOM, LEFT
    }

    private StackPane contentHolder;

    private double offsetX = 0;
    private double offsetY = 0;

    private StackPane dialogContainer;
    private Region content;
    private Transition animation;

    EventHandler<? super MouseEvent> closeHandler = e -> close();

    /**
     * creates empty TimeDialog1 control with CENTER animation type
     */
    public TimeDialog1() {
        this(null, null, DialogTransition.CENTER);
    }

    /**
     * creates TimeDialog1 control with a specified animation type, the animation type
     * can be one of the following:
     * <ul>
     * <li>CENTER</li>
     * <li>TOP</li>
     * <li>RIGHT</li>
     * <li>BOTTOM</li>
     * <li>LEFT</li>
     * </ul>
     *
     * @param dialogContainer is the parent of the dialog, it
     * @param content         the content of dialog
     * @param transitionType  the animation type
     */

    public TimeDialog1(StackPane dialogContainer, Region content, DialogTransition transitionType) {
        initialize();
        setContent(content);
        setDialogContainer(dialogContainer);
        this.transitionType.set(transitionType);
        // init change listeners
        initChangeListeners();
    }

    /**
     * creates TimeDialog1 control with a specified animation type that
     * is closed when clicking on the overlay, the animation type
     * can be one of the following:
     * <ul>
     * <li>CENTER</li>
     * <li>TOP</li>
     * <li>RIGHT</li>
     * <li>BOTTOM</li>
     * <li>LEFT</li>
     * </ul>
     *
     * @param dialogContainer
     * @param content
     * @param transitionType
     * @param overlayClose
     */
    public TimeDialog1(StackPane dialogContainer, Region content, DialogTransition transitionType, boolean overlayClose) {
        setOverlayClose(overlayClose);
        initialize();
        setContent(content);
        setDialogContainer(dialogContainer);
        this.transitionType.set(transitionType);
        // init change listeners
        initChangeListeners();
    }

    private void initChangeListeners() {
        overlayCloseProperty().addListener((o, oldVal, newVal) -> {
            if (newVal) {
                this.addEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);
            } else {
                this.removeEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);
            }
        });
    }

    private void initialize() {
        this.setVisible(false);
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.transitionType.addListener((o, oldVal, newVal) -> {
            animation = getShowAnimation(transitionType.get());
        });

        contentHolder = new StackPane();
        contentHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), null)));
//        JFXDepthManager.setDepth(contentHolder, 4);
        contentHolder.setPickOnBounds(false);
        // ensure stackpane is never resized beyond it's preferred size
        contentHolder.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.getChildren().add(contentHolder);
        this.getStyleClass().add("jfx-dialog-overlay-pane");
        StackPane.setAlignment(contentHolder, Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.1), null, null)));
        // close the dialog if clicked on the overlay pane
        if (overlayClose.get()) {
            this.addEventHandler(MouseEvent.MOUSE_PRESSED, closeHandler);
        }
        // prevent propagating the events to overlay pane
        contentHolder.addEventHandler(MouseEvent.ANY, e -> e.consume());
    }

    /***************************************************************************
     *                                                                         *
     * Setters / Getters                                                       *
     *                                                                         *
     **************************************************************************/

    /**
     * @return the dialog container
     */
    public StackPane getDialogContainer() {
        return dialogContainer;
    }

    /**
     * set the dialog container
     * Note: the dialog container must be StackPane, its the container for the dialog to be shown in.
     *
     * @param dialogContainer
     */
    public void setDialogContainer(StackPane dialogContainer) {
        if (dialogContainer != null) {
            this.dialogContainer = dialogContainer;
            if (this.dialogContainer.getChildren().indexOf(this) == -1
                    || this.dialogContainer.getChildren().indexOf(this) != this.dialogContainer.getChildren().size() - 1) {
                this.dialogContainer.getChildren().remove(this);
                this.dialogContainer.getChildren().add(this);
            }
            // FIXME: need to be improved to consider only the parent boundary
            offsetX = this.getParent().getBoundsInLocal().getWidth();
            offsetY = this.getParent().getBoundsInLocal().getHeight();
            animation = getShowAnimation(transitionType.get());
        }
    }

    /**
     * @return dialog content node
     */
    public Region getContent() {
        return content;
    }

    /**
     * set the content of the dialog
     *
     * @param content
     */
    public void setContent(Region content) {
        if (content != null) {
            this.content = content;
            this.content.setPickOnBounds(false);
            contentHolder.getChildren().setAll(content);
        }
    }

    /**
     * indicates whether the dialog will close when clicking on the overlay or not
     *
     * @return
     */
    private BooleanProperty overlayClose = new SimpleBooleanProperty(true);

    public final BooleanProperty overlayCloseProperty() {
        return this.overlayClose;
    }

    public final boolean isOverlayClose() {
        return this.overlayCloseProperty().get();
    }

    public final void setOverlayClose(final boolean overlayClose) {
        this.overlayCloseProperty().set(overlayClose);
    }

    /**
     * it will show the dialog in the specified container
     *
     * @param dialogContainer
     */
    public void show(StackPane dialogContainer) {
        this.setDialogContainer(dialogContainer);
        animation.play();
    }

    /**
     * show the dialog inside its parent container
     */
    public void show() {
        if (dialogContainer == null) {
            System.err.println("ERROR: TimeDialog1 container is not set!");
            return;
        }
        this.setDialogContainer(dialogContainer);
        animation.play();
    }

    /**
     * close the dialog
     */
    public void close() {
        animation.setRate(-1);
        animation.play();
        animation.setOnFinished(e -> {
            resetProperties();
            Event.fireEvent(TimeDialog1.this, new Event(new EventType<>(Event.ANY, "JFX_DIALOG_CLOSED")));
            dialogContainer.getChildren().remove(this);
        });
    }

    /***************************************************************************
     *                                                                         *
     * Transitions                                                             *
     *                                                                         *
     **************************************************************************/

    private Transition getShowAnimation(DialogTransition transitionType) {
        Transition animation = null;
        if (contentHolder != null) {
            switch (transitionType) {
                case LEFT:
                    contentHolder.setScaleX(1);
                    contentHolder.setScaleY(1);
                    contentHolder.setTranslateX(-offsetX);
//                    animation = new LeftTransition();
                    break;
                case RIGHT:
                    contentHolder.setScaleX(1);
                    contentHolder.setScaleY(1);
                    contentHolder.setTranslateX(offsetX);
//                    animation = new RightTransition();
                    break;
                case TOP:
                    contentHolder.setScaleX(1);
                    contentHolder.setScaleY(1);
                    contentHolder.setTranslateY(-offsetY);
//                    animation = new TopTransition();
                    break;
                case BOTTOM:
                    contentHolder.setScaleX(1);
                    contentHolder.setScaleY(1);
                    contentHolder.setTranslateY(offsetY);
//                    animation = new BottomTransition();
                    break;
                default:
                    contentHolder.setScaleX(0);
                    contentHolder.setScaleY(0);
//                    animation = new CenterTransition();
                    break;
            }
        }
        if (animation != null) {
//            animation.setOnFinished(finish ->
//                    Event.fireEvent(TimeDialog1.this, new Event(new EventType<>(Event.ANY, "JFX_DIALOG_OPENED"))));
        }
        return animation;
    }

    private void resetProperties() {
        this.setVisible(false);
        contentHolder.setTranslateX(0);
        contentHolder.setTranslateY(0);
        contentHolder.setScaleX(1);
        contentHolder.setScaleY(1);
    }

//    private class LeftTransition extends CachedTransition {
//        LeftTransition() {
//            super(contentHolder, new Timeline(
//                    new KeyFrame(Duration.ZERO,
//                            new KeyValue(contentHolder.translateXProperty(), -offsetX, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), false, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(10),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), true, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(1000),
//                            new KeyValue(contentHolder.translateXProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 1, Interpolator.EASE_BOTH)
//                    ))
//            );
//            // reduce the number to increase the shifting , increase number to reduce shifting
//            setCycleDuration(Duration.seconds(0.4));
//            setDelay(Duration.seconds(0));
//        }
//    }
//
//    private class RightTransition extends CachedTransition {
//        RightTransition() {
//            super(contentHolder, new Timeline(
//                    new KeyFrame(Duration.ZERO,
//                            new KeyValue(contentHolder.translateXProperty(), offsetX, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), false, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(10),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), true, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(1000),
//                            new KeyValue(contentHolder.translateXProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 1, Interpolator.EASE_BOTH)))
//            );
//            // reduce the number to increase the shifting , increase number to reduce shifting
//            setCycleDuration(Duration.seconds(0.4));
//            setDelay(Duration.seconds(0));
//        }
//    }
//
//    private class TopTransition extends CachedTransition {
//        TopTransition() {
//            super(contentHolder, new Timeline(
//                    new KeyFrame(Duration.ZERO,
//                            new KeyValue(contentHolder.translateYProperty(), -offsetY, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), false, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(10),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), true, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(1000),
//                            new KeyValue(contentHolder.translateYProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 1, Interpolator.EASE_BOTH)))
//            );
//            // reduce the number to increase the shifting , increase number to reduce shifting
//            setCycleDuration(Duration.seconds(0.4));
//            setDelay(Duration.seconds(0));
//        }
//    }
//
//    private class BottomTransition extends CachedTransition {
//        BottomTransition() {
//            super(contentHolder, new Timeline(
//                    new KeyFrame(Duration.ZERO,
//                            new KeyValue(contentHolder.translateYProperty(), offsetY, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), false, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(10),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), true, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(1000),
//                            new KeyValue(contentHolder.translateYProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 1, Interpolator.EASE_BOTH)))
//            );
//            // reduce the number to increase the shifting , increase number to reduce shifting
//            setCycleDuration(Duration.seconds(0.4));
//            setDelay(Duration.seconds(0));
//        }
//    }
//
//    private class CenterTransition extends CachedTransition {
//        CenterTransition() {
//            super(contentHolder, new Timeline(
//                    new KeyFrame(Duration.ZERO,
//                            new KeyValue(contentHolder.scaleXProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(contentHolder.scaleYProperty(), 0, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), false, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(10),
//                            new KeyValue(TimeDialog1.this.visibleProperty(), true, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 0, Interpolator.EASE_BOTH)
//                    ),
//                    new KeyFrame(Duration.millis(1000),
//                            new KeyValue(contentHolder.scaleXProperty(), 1, Interpolator.EASE_BOTH),
//                            new KeyValue(contentHolder.scaleYProperty(), 1, Interpolator.EASE_BOTH),
//                            new KeyValue(TimeDialog1.this.opacityProperty(), 1, Interpolator.EASE_BOTH)
//                    ))
//            );
//            // reduce the number to increase the shifting , increase number to reduce shifting
//            setCycleDuration(Duration.seconds(0.4));
//            setDelay(Duration.seconds(0));
//        }
//    }


    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/
    /**
     * Initialize the style class to 'jfx-dialog'.
     * <p>
     * This is the selector class from which CSS can be used to style
     * this control.
     */
    private static final String DEFAULT_STYLE_CLASS = "jfx-dialog";

    /**
     * dialog transition type property, it can be one of the following:
     * <ul>
     * <li>CENTER</li>
     * <li>TOP</li>
     * <li>RIGHT</li>
     * <li>BOTTOM</li>
     * <li>LEFT</li>
     * </ul>
     */
    private StyleableObjectProperty<DialogTransition> transitionType = new SimpleStyleableObjectProperty<>(
            StyleableProperties.DIALOG_TRANSITION,
            TimeDialog1.this,
            "dialogTransition",
            DialogTransition.CENTER);

    public DialogTransition getTransitionType() {
        return transitionType == null ? DialogTransition.CENTER : transitionType.get();
    }

    public StyleableObjectProperty<DialogTransition> transitionTypeProperty() {
        return this.transitionType;
    }

    public void setTransitionType(DialogTransition transition) {
        this.transitionType.set(transition);
    }

    private static class StyleableProperties {
        private static final CssMetaData<TimeDialog1, DialogTransition> DIALOG_TRANSITION = null;
//                new CssMetaData<TimeDialog1, DialogTransition>("-jfx-dialog-transition",
//                        DialogTransitionConverter.getInstance(),
//                        DialogTransition.CENTER) {
//                    @Override
//                    public boolean isSettable(TimeDialog1 control) {
//                        return control.transitionType == null || !control.transitionType.isBound();
//                    }
//
//                    @Override
//                    public StyleableProperty<DialogTransition> getStyleableProperty(TimeDialog1 control) {
//                        return control.transitionTypeProperty();
//                    }
//                };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(Parent.getClassCssMetaData());
            Collections.addAll(styleables,
                    DIALOG_TRANSITION
            );
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    // inherit the styleable properties from parent
    private List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        if (STYLEABLES == null) {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(Parent.getClassCssMetaData());
            styleables.addAll(getClassCssMetaData());
            styleables.addAll(StackPane.getClassCssMetaData());
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
        return STYLEABLES;
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }


    /***************************************************************************
     *                                                                         *
     * Custom Events                                                           *
     *                                                                         *
     **************************************************************************/

    private final ObjectProperty<EventHandler<? super Event>> onDialogClosedProperty = new ObjectPropertyBase<EventHandler<? super Event>>() {
        @Override
        protected void invalidated() {
            setEventHandler(new EventType<>(Event.ANY, "JFX_DIALOG_CLOSED"), get());
        }

        @Override
        public Object getBean() {
            return TimeDialog1.this;
        }

        @Override
        public String getName() {
            return "onClosed";
        }
    };

    /**
     * Defines a function to be called when the dialog is closed.
     * Note: it will be triggered after the close animation is finished.
     */
    public ObjectProperty<EventHandler<? super Event>> onDialogClosedProperty() {
        return onDialogClosedProperty;
    }

    public void setOnDialogClosed(EventHandler<? super Event> handler) {
        onDialogClosedProperty().set(handler);
    }

    public EventHandler<? super Event> getOnDialogClosed() {
        return onDialogClosedProperty().get();
    }


    private ObjectProperty<EventHandler<? super Event>> onDialogOpenedProperty = new ObjectPropertyBase<EventHandler<? super Event>>() {
        @Override
        protected void invalidated() {
            setEventHandler(new EventType<>(Event.ANY, "JFX_DIALOG_OPENED"), get());
        }

        @Override
        public Object getBean() {
            return TimeDialog1.this;
        }

        @Override
        public String getName() {
            return "onOpened";
        }
    };

    /**
     * Defines a function to be called when the dialog is opened.
     * Note: it will be triggered after the show animation is finished.
     */
    public ObjectProperty<EventHandler<? super Event>> onDialogOpenedProperty() {
        return onDialogOpenedProperty;
    }

    public void setOnDialogOpened(EventHandler<? super Event> handler) {
        onDialogOpenedProperty().set(handler);
    }

    public EventHandler<? super Event> getOnDialogOpened() {
        return onDialogOpenedProperty().get();
    }
}
