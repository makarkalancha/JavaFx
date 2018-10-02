package datetimepicker.time;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcalancea
 * Date: 02 Oct 2018
 * Time: 13:23
 */
public class TimePickerBehavior1 extends ComboBoxBaseBehavior<LocalTime> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public TimePickerBehavior1(final TimePicker1 timePicker) {
        super(timePicker, JFX_TIME_PICKER_BINDINGS);
    }

    /***************************************************************************
     *                                                                         *
     * Key event handling                                                      *
     *                                                                         *
     **************************************************************************/

    protected static final List<KeyBinding> JFX_TIME_PICKER_BINDINGS = new ArrayList<>();

    static {
        JFX_TIME_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }

    /**************************************************************************
     *                                                                        *
     * Mouse Events handling (when losing focus)                              *
     *                                                                        *
     *************************************************************************/

    @Override
    public void onAutoHide() {
        TimePicker1 datePicker = (TimePicker1) getControl();
        TimePickerSkin1 cpSkin = (TimePickerSkin1) datePicker.getSkin();
        cpSkin.syncWithAutoUpdate();
        if (!datePicker.isShowing()) {
            super.onAutoHide();
        }
    }

}
