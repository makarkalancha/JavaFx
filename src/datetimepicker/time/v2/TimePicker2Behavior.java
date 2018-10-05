package datetimepicker.time.v2;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcalancea
 * Date: 03 Oct 2018
 * Time: 18:39
 */
public class TimePicker2Behavior extends ComboBoxBaseBehavior<LocalTime> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     *
     */
    public TimePicker2Behavior(final TimePicker2 timePicker) {
        super(timePicker, TIME_PICKER_BINDINGS);
    }

    /***************************************************************************
     *                                                                         *
     * Key event handling                                                      *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override
//    public void onAutoHide(PopupControl popup) {
    public void onAutoHide() {
//        // when we click on some non-interactive part of the
//        // calendar - we do not want to hide.
//        if (!popup.isShowing() && getNode().isShowing()) {
//            // Popup was dismissed. Maybe user clicked outside or typed ESCAPE.
//            // Make sure DatePicker button is in sync.
//            getNode().hide();
//        }
//        // if the DatePicker is no longer showing, then invoke the super method
//        // to keep its show/hide state in sync.
//        if (!getNode().isShowing()) {
//            super.onAutoHide(popup);
//        }
    }

    protected static final List<KeyBinding> TIME_PICKER_BINDINGS = new ArrayList<>();

    static {
        TIME_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }

}
