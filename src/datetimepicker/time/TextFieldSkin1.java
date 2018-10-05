package datetimepicker.time;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.lang.reflect.Field;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 16:57
 */
public class TextFieldSkin1 extends TextFieldSkin {
    private boolean invalid = true;
    private StackPane line = new StackPane();
    private StackPane focusedLine = new StackPane();
    private Label errorLabel = new Label();
    private StackPane errorIcon = new StackPane();
    private HBox errorContainer;
    private Pane textPane;
    private double initScale = 0.05D;
    private double oldErrorLabelHeight = -1.0D;
    private double initYLayout = -1.0D;
    private double initHeight = -1.0D;
    private boolean errorShown = false;
    private double currentFieldHeight = -1.0D;
    private double errorLabelInitHeight = 0.0D;
    private boolean heightChanged = false;
    private StackPane promptContainer;
    private Text promptText;
    private ParallelTransition transition;
    private Timeline hideErrorAnimation;
    private CachedTransition promptTextUpTransition;
    private CachedTransition promptTextDownTransition;
    private CachedTransition promptTextColorTransition;
    private Scale promptTextScale = new Scale(1.0D, 1.0D, 0.0D, 0.0D);
    private Scale scale;
    private Timeline linesAnimation;
    private Paint oldPromptTextFill;
    private BooleanBinding usePromptText;

    public JFXTextFieldSkin(JFXTextField field) {
        super(field);
        this.scale = new Scale(this.initScale, 1.0D);
        this.linesAnimation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.scale.xProperty(), Double.valueOf(this.initScale), Interpolator.EASE_BOTH), new KeyValue(this.focusedLine.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(1.0D), new KeyValue[]{new KeyValue(this.focusedLine.opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.millis(160.0D), new KeyValue[]{new KeyValue(this.scale.xProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})});
        this.usePromptText = Bindings.createBooleanBinding(JFXTextFieldSkin$$Lambda$1.lambdaFactory$(this), new Observable[]{((TextField)this.getSkinnable()).textProperty(), ((TextField)this.getSkinnable()).promptTextProperty()});
        field.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.TRANSPARENT, (CornerRadii)null, (Insets)null)}));
        field.setPadding(new Insets(4.0D, 0.0D, 4.0D, 0.0D));
        this.errorLabel.getStyleClass().add("error-label");
        this.errorLabel.setPadding(new Insets(4.0D, 0.0D, 0.0D, 0.0D));
        this.errorLabel.setWrapText(true);
        this.errorIcon.setTranslateY(3.0D);
        StackPane errorLabelContainer = new StackPane();
        errorLabelContainer.getChildren().add(this.errorLabel);
        StackPane.setAlignment(this.errorLabel, Pos.CENTER_LEFT);
        this.line.getStyleClass().add("input-line");
        this.getChildren().add(this.line);
        this.focusedLine.getStyleClass().add("input-focused-line");
        this.getChildren().add(this.focusedLine);
        this.line.setPrefHeight(1.0D);
        this.line.setTranslateY(1.0D);
        this.line.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(((JFXTextField)this.getSkinnable()).getUnFocusColor(), CornerRadii.EMPTY, Insets.EMPTY)}));
        if(((TextField)this.getSkinnable()).isDisabled()) {
            this.line.setBorder(new Border(new BorderStroke[]{new BorderStroke(((JFXTextField)this.getSkinnable()).getUnFocusColor(), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(1.0D))}));
            this.line.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)}));
        }

        this.focusedLine.setPrefHeight(2.0D);
        this.focusedLine.setTranslateY(0.0D);
        this.focusedLine.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(((JFXTextField)this.getSkinnable()).getFocusColor(), CornerRadii.EMPTY, Insets.EMPTY)}));
        this.focusedLine.setOpacity(0.0D);
        this.focusedLine.getTransforms().add(this.scale);
        this.promptContainer = new StackPane();
        this.getChildren().add(this.promptContainer);
        this.errorContainer = new HBox();
        this.errorContainer.getChildren().setAll(new Node[]{errorLabelContainer, this.errorIcon});
        HBox.setHgrow(errorLabelContainer, Priority.ALWAYS);
        this.errorContainer.setSpacing(10.0D);
        this.errorContainer.setVisible(false);
        this.errorContainer.setOpacity(0.0D);
        this.getChildren().add(this.errorContainer);
        this.errorLabel.heightProperty().addListener(JFXTextFieldSkin$$Lambda$2.lambdaFactory$(this));
        this.errorContainer.visibleProperty().addListener(JFXTextFieldSkin$$Lambda$3.lambdaFactory$(this));
        field.labelFloatProperty().addListener(JFXTextFieldSkin$$Lambda$4.lambdaFactory$(this));
        field.activeValidatorProperty().addListener(JFXTextFieldSkin$$Lambda$5.lambdaFactory$(this));
        field.focusColorProperty().addListener(JFXTextFieldSkin$$Lambda$6.lambdaFactory$(this));
        field.unFocusColorProperty().addListener(JFXTextFieldSkin$$Lambda$7.lambdaFactory$(this));
        field.focusedProperty().addListener(JFXTextFieldSkin$$Lambda$8.lambdaFactory$(this));
        field.textProperty().addListener(JFXTextFieldSkin$$Lambda$9.lambdaFactory$(this));
        field.disabledProperty().addListener(JFXTextFieldSkin$$Lambda$10.lambdaFactory$(this));
        this.promptTextFill.addListener(JFXTextFieldSkin$$Lambda$11.lambdaFactory$(this));
    }

    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        if((this.transition == null || this.transition.getStatus() == Animation.Status.STOPPED) && ((TextField)this.getSkinnable()).isFocused() && ((JFXTextField)this.getSkinnable()).isLabelFloat()) {
            this.promptTextFill.set(((JFXTextField)this.getSkinnable()).getFocusColor());
        }

        if(this.invalid) {
            this.invalid = false;
            this.textPane = (Pane)this.getChildren().get(0);
            this.createFloatingLabel();
            super.layoutChildren(x, y, w, h);
            if(((JFXTextField)this.getSkinnable()).getActiveValidator() != null) {
                this.updateValidationError();
            }

            this.createFocusTransition();
            if(((TextField)this.getSkinnable()).isFocused()) {
                this.focus();
            }
        }

        this.focusedLine.resizeRelocate(x, ((TextField)this.getSkinnable()).getHeight(), w, this.focusedLine.prefHeight(-1.0D));
        this.line.resizeRelocate(x, ((TextField)this.getSkinnable()).getHeight(), w, this.line.prefHeight(-1.0D));
        this.errorContainer.relocate(x, ((TextField)this.getSkinnable()).getHeight() + this.focusedLine.getHeight());
        this.scale.setPivotX(w / 2.0D);
    }

    private void updateValidationError() {
        if(this.hideErrorAnimation != null && this.hideErrorAnimation.getStatus() == Animation.Status.RUNNING) {
            this.hideErrorAnimation.stop();
        }

        this.hideErrorAnimation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(160.0D), new KeyValue[]{new KeyValue(this.errorContainer.opacityProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)})});
        this.hideErrorAnimation.setOnFinished(JFXTextFieldSkin$$Lambda$12.lambdaFactory$(this));
        this.hideErrorAnimation.play();
    }

    private void createFloatingLabel() {
        if(((JFXTextField)this.getSkinnable()).isLabelFloat()) {
            if(this.promptText == null) {
                boolean triggerFloatLabel = false;
                if(this.textPane.getChildren().get(0) instanceof Text) {
                    this.promptText = (Text)this.textPane.getChildren().get(0);
                } else {
                    try {
                        Field field = TextFieldSkin.class.getDeclaredField("promptNode");
                        field.setAccessible(true);
                        this.createPromptNode();
                        field.set(this, this.promptText);
                        triggerFloatLabel = true;
                    } catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException var4) {
                        var4.printStackTrace();
                    }
                }

                this.promptText.getTransforms().add(this.promptTextScale);
                this.promptContainer.getChildren().add(this.promptText);
                if(triggerFloatLabel) {
                    this.promptText.setTranslateY(-this.textPane.getHeight());
                    this.promptTextScale.setX(0.85D);
                    this.promptTextScale.setY(0.85D);
                }
            }

            this.promptTextUpTransition = new CachedTransition(this.textPane, new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(1300.0D), new KeyValue[]{new KeyValue(this.promptText.translateYProperty(), Double.valueOf(-this.textPane.getHeight()), Interpolator.EASE_BOTH), new KeyValue(this.promptTextScale.xProperty(), Double.valueOf(0.85D), Interpolator.EASE_BOTH), new KeyValue(this.promptTextScale.yProperty(), Double.valueOf(0.85D), Interpolator.EASE_BOTH)})})) {
                {
                    this.setDelay(Duration.millis(0.0D));
                    this.setCycleDuration(Duration.millis(240.0D));
                }
            };
            this.promptTextColorTransition = new CachedTransition(this.textPane, new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(1300.0D), new KeyValue[]{new KeyValue(this.promptTextFill, ((JFXTextField)this.getSkinnable()).getFocusColor(), Interpolator.EASE_BOTH)})})) {
                {
                    this.setDelay(Duration.millis(0.0D));
                    this.setCycleDuration(Duration.millis(160.0D));
                }

                protected void starting() {
                    super.starting();
                    JFXTextFieldSkin.this.oldPromptTextFill = (Paint)JFXTextFieldSkin.this.promptTextFill.get();
                }
            };
            this.promptTextDownTransition = new CachedTransition(this.textPane, new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(1300.0D), new KeyValue[]{new KeyValue(this.promptText.translateYProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH), new KeyValue(this.promptTextScale.xProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH), new KeyValue(this.promptTextScale.yProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH)})})) {
                {
                    this.setDelay(Duration.millis(0.0D));
                    this.setCycleDuration(Duration.millis(240.0D));
                }
            };
            this.promptTextDownTransition.setOnFinished(JFXTextFieldSkin$$Lambda$13.lambdaFactory$(this));
            this.promptText.visibleProperty().unbind();
            this.promptText.visibleProperty().set(true);
        }

    }

    private void createPromptNode() {
        this.promptText = new Text();
        this.promptText.setManaged(false);
        this.promptText.getStyleClass().add("text");
        this.promptText.visibleProperty().bind(this.usePromptText);
        this.promptText.fontProperty().bind(((TextField)this.getSkinnable()).fontProperty());
        this.promptText.textProperty().bind(((TextField)this.getSkinnable()).promptTextProperty());
        this.promptText.fillProperty().bind(this.promptTextFill);
        this.promptText.setLayoutX(1.0D);
    }

    private void focus() {
        if(this.textPane == null) {
            Platform.runLater(JFXTextFieldSkin$$Lambda$14.lambdaFactory$(this));
        } else {
            if(this.transition == null) {
                this.createFocusTransition();
            }

            this.transition.play();
        }

    }

    private void createFocusTransition() {
        this.transition = new ParallelTransition();
        if(((JFXTextField)this.getSkinnable()).isLabelFloat()) {
            this.transition.getChildren().add(this.promptTextUpTransition);
            this.transition.getChildren().add(this.promptTextColorTransition);
        }

        this.transition.getChildren().add(this.linesAnimation);
    }

    private void unFocus() {
        if(this.transition != null) {
            this.transition.stop();
        }

        this.scale.setX(this.initScale);
        this.focusedLine.setOpacity(0.0D);
        if(((JFXTextField)this.getSkinnable()).isLabelFloat() && this.oldPromptTextFill != null) {
            this.promptTextFill.set(this.oldPromptTextFill);
            if(this.usePromptText()) {
                this.promptTextDownTransition.play();
            }
        }

    }

    private void animateFloatingLabel(boolean up) {
        if(this.promptText == null) {
            Platform.runLater(JFXTextFieldSkin$$Lambda$15.lambdaFactory$(this, up));
        } else {
            if(this.transition != null) {
                this.transition.stop();
                this.transition.getChildren().remove(this.promptTextUpTransition);
                this.transition = null;
            }

            if(up && this.promptText.getTranslateY() == 0.0D) {
                this.promptTextDownTransition.stop();
                this.promptTextUpTransition.play();
            } else if(!up) {
                this.promptTextUpTransition.stop();
                this.promptTextDownTransition.play();
            }
        }

    }

    private boolean usePromptText() {
        String txt = ((TextField)this.getSkinnable()).getText();
        String promptTxt = ((TextField)this.getSkinnable()).getPromptText();
        return (txt == null || txt.isEmpty()) && promptTxt != null && !promptTxt.isEmpty() && !((Paint)this.promptTextFill.get()).equals(Color.TRANSPARENT);
    }

    private void showError(ValidatorBase validator) {
        this.errorLabel.setText(validator.getMessage());
        Node awsomeIcon = validator.getIcon();
        this.errorIcon.getChildren().clear();
        if(awsomeIcon != null) {
            this.errorIcon.getChildren().add(awsomeIcon);
            StackPane.setAlignment(awsomeIcon, Pos.TOP_RIGHT);
        }

        if(this.initYLayout == -1.0D) {
            this.textPane.setMaxHeight(this.textPane.getHeight());
            this.initYLayout = this.textPane.getBoundsInParent().getMinY();
            this.initHeight = ((TextField)this.getSkinnable()).getHeight();
            this.currentFieldHeight = this.initHeight;
        }

        this.errorContainer.setVisible(true);
        this.errorShown = true;
    }

    private void hideError() {
        if(this.heightChanged) {
            (new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(160.0D), new KeyValue[]{new KeyValue(this.textPane.translateYProperty(), Integer.valueOf(0), Interpolator.EASE_BOTH)})})).play();
            (new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(160.0D), new KeyValue[]{new KeyValue(((TextField)this.getSkinnable()).minHeightProperty(), Double.valueOf(this.initHeight), Interpolator.EASE_BOTH)})})).play();
            this.heightChanged = false;
        }

        this.errorLabel.setText((String)null);
        this.oldErrorLabelHeight = this.errorLabelInitHeight;
        this.errorIcon.getChildren().clear();
        this.currentFieldHeight = this.initHeight;
        this.errorContainer.setVisible(false);
        this.errorShown = false;
    }
}

