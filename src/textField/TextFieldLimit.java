package textField;

import javafx.scene.control.TextField;

/**
 * Created by Makar Kalancha
 * Date: 09 May 2017
 * Time: 10:27
 */
public class TextFieldLimit extends TextField {
    private final int maxLength;

    public TextFieldLimit(int maxLength) {
        super();
        this.maxLength = maxLength;
    }

    public TextFieldLimit(String text, int maxLength) {
        super(text);
        this.maxLength = maxLength;
    }


}
