package datetimepicker.datetime;

import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 11:14
 */
public class DateTimePickerBehavior extends ComboBoxBaseBehavior<LocalDateTime> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     *
     */
    public DateTimePickerBehavior(final DateTimePicker dateTimePicker) {
        super(dateTimePicker, DATE_PICKER_BINDINGS);
    }

    /***************************************************************************
     *                                                                         *
     * Key event handling                                                      *
     *                                                                         *
     **************************************************************************/

    protected static final List<KeyBinding> DATE_PICKER_BINDINGS = new ArrayList<KeyBinding>();
    static {
        DATE_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }

    @Override public void onAutoHide() {
        // when we click on some non-interactive part of the
        // calendar - we do not want to hide.
        DateTimePicker datePicker = (DateTimePicker)getControl();
        DatePickerSkin cpSkin = (DatePickerSkin)datePicker.getSkin();
        cpSkin.syncWithAutoUpdate();
        // if the DateTimePicker is no longer showing, then invoke the super method
        // to keep its show/hide state in sync.
        if (!datePicker.isShowing()) super.onAutoHide();
    }

}
