package datetimepicker.time;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * User: Makar Kalancha
 * Date: 11/09/2018
 * Time: 17:01
 */
public class DialogEvent1 extends Event {
    private static final long serialVersionUID = 1L;
    public static final EventType<DialogEvent1> CLOSED;
    public static final EventType<DialogEvent1> OPENED;

    public DialogEvent1(EventType<? extends Event> eventType) {
        super(eventType);
    }

    static {
        CLOSED = new EventType(Event.ANY, "DIALOG_CLOSED");
        OPENED = new EventType(Event.ANY, "DIALOG_OPENED");
    }
}
