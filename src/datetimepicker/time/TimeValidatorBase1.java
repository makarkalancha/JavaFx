package datetimepicker.time;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

import java.util.function.Supplier;

/**
 * Created by mcalancea
 * Date: 02 Oct 2018
 * Time: 11:57
 */
public abstract class TimeValidatorBase1 extends Parent {

    /**
     * this style class will be activated when a validation error occurs
     */
    public static final PseudoClass PSEUDO_CLASS_ERROR = PseudoClass.getPseudoClass("error");

    private Tooltip tooltip = null;
    private Tooltip errorTooltip = null;

    public TimeValidatorBase1(String message) {
        this();
        this.setMessage(message);
    }

    public TimeValidatorBase1() {
        parentProperty().addListener((o, oldVal, newVal) -> parentChanged());
        errorTooltip = new Tooltip();
        errorTooltip.getStyleClass().add("error-tooltip");
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    private void parentChanged() {
        updateSrcControl();
    }

    private void updateSrcControl() {
        Parent parent = getParent();
        if (parent != null) {
            Node control = parent.lookup(getSrc());
            srcControl.set(control);
        }
    }

    /**
     * will validate the source control
     */
    public void validate() {
        eval();
        onEval();
    }

    /**
     * will evaluate the validation condition once calling validate method
     */
    protected abstract void eval();

    /**
     * this method will update the source control after evaluating the validation condition
     */
    protected void onEval() {
        Node control = getSrcControl();
        if (hasErrors.get()) {
            control.pseudoClassStateChanged(PSEUDO_CLASS_ERROR, true);

            if (control instanceof Control) {
                Tooltip controlTooltip = ((Control) control).getTooltip();
                if (controlTooltip != null && !controlTooltip.getStyleClass().contains("error-tooltip")) {
                    tooltip = ((Control) control).getTooltip();
                }
                errorTooltip.setText(getMessage());
                ((Control) control).setTooltip(errorTooltip);
            }
        } else {
            if (control instanceof Control) {
                Tooltip controlTooltip = ((Control) control).getTooltip();
                if ((controlTooltip != null && controlTooltip.getStyleClass().contains("error-tooltip"))
                        || (controlTooltip == null && tooltip != null)) {
                    ((Control) control).setTooltip(tooltip);
                }
                tooltip = null;
            }
            control.pseudoClassStateChanged(PSEUDO_CLASS_ERROR, false);
        }
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    /***** srcControl *****/
    protected SimpleObjectProperty<Node> srcControl = new SimpleObjectProperty<>();

    public void setSrcControl(Node srcControl) {
        this.srcControl.set(srcControl);
    }

    public Node getSrcControl() {
        return this.srcControl.get();
    }

    public ObjectProperty<Node> srcControlProperty() {
        return this.srcControl;
    }


    /***** src *****/
    protected SimpleStringProperty src = new SimpleStringProperty() {
        @Override
        protected void invalidated() {
            updateSrcControl();
        }
    };

    public void setSrc(String src) {
        this.src.set(src);
    }

    public String getSrc() {
        return this.src.get();
    }

    public StringProperty srcProperty() {
        return this.src;
    }


    /***** hasErrors *****/
    protected ReadOnlyBooleanWrapper hasErrors = new ReadOnlyBooleanWrapper(false);

    public boolean getHasErrors() {
        return hasErrors.get();
    }

    public ReadOnlyBooleanProperty hasErrorsProperty() {
        return hasErrors.getReadOnlyProperty();
    }

    /***** Message *****/
    protected SimpleStringProperty message = new SimpleStringProperty() {
        @Override
        protected void invalidated() {
            updateSrcControl();
        }
    };

    public void setMessage(String msg) {
        this.message.set(msg);
    }

    public String getMessage() {
        return this.message.get();
    }

    public StringProperty messageProperty() {
        return this.message;
    }

    /***** Icon *****/
    protected SimpleObjectProperty<Supplier<Node>> iconSupplier = new SimpleObjectProperty<Supplier<Node>>() {
        @Override
        protected void invalidated() {
            updateSrcControl();
        }
    };

    public void setIconSupplier(Supplier<Node> icon) {
        this.iconSupplier.set(icon);
    }

    public SimpleObjectProperty<Supplier<Node>> iconSupplierProperty() {
        return this.iconSupplier;
    }

    public Supplier<Node> getIconSupplier() {
        return iconSupplier.get();
    }

    public void setIcon(Node icon) {
        iconSupplier.set(() -> icon);
    }

    public Node getIcon() {
        if (iconSupplier.get() == null) {
            return null;
        }
        Node icon = iconSupplier.get().get();
        if (icon != null) {
            icon.getStyleClass().add("error-icon");
        }
        return icon;
    }
}