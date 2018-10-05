package datetimepicker.time;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 16:51
 */
public class TimePickerBehavior1  extends ComboBoxBaseBehavior<LocalTime> {
    protected static final List<KeyBinding> JFX_TIME_PICKER_BINDINGS = new ArrayList();

    public TimePickerBehavior1(TimePicker1 timePicker) {
        super(timePicker, JFX_TIME_PICKER_BINDINGS);
    }

    public void onAutoHide() {
        TimePicker1 datePicker = (TimePicker1)this.getControl();
        TimePickerSkin1 cpSkin = (TimePickerSkin1)datePicker.getSkin();
        cpSkin.syncWithAutoUpdate();
        if(!datePicker.isShowing()) {
            super.onAutoHide();
        }

    }

    static {
        JFX_TIME_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }
}
