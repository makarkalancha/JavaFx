package combobox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Created by mcalancea on 2016-03-09.
 */

public class FilterComboBox extends ComboBox<String> {

    private ObservableList<String> initialList;
    private ObservableList<String> bufferList = FXCollections.observableArrayList();
    private String previousValue = "";

    public FilterComboBox() {

    }

    public FilterComboBox(ObservableList<String> items) {
        super(items);
        super.setEditable(true);
        this.initialList = items;

        this.configAutoFilterListener();
    }

    //http://stackoverflow.com/questions/19010619/javafx-filtered-combobox
    private void configAutoFilterListener() {
        final FilterComboBox currentInstance = this;
//        this.valueProperty()
        this.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                previousValue = oldValue;
                final TextField editor = currentInstance.getEditor();
                final String selected = currentInstance.getSelectionModel().getSelectedItem();

//                String editorGetTextDebug = editor.getText();
//
//                if (selected == null || !selected.equals(editor.getText())) {
                System.out.println(">>>observable:"+observable);
                System.out.println(">>>oldValue:"+oldValue);
                System.out.println(">>>newValue:"+newValue);
                System.out.println(">>>selected:"+selected);
                if (selected == null || !selected.equals(newValue)) {
                    filterItems(newValue, currentInstance);
                    currentInstance.show();
//                    if (currentInstance.getItems().size() == 1) {
//                        setUserInputToOnlyOption(currentInstance, editor);
//                    }
                }
            }
        });
    }

    private void filterItems(String filter, ComboBox<String> comboBox) {
        /**
         * https://community.oracle.com/thread/2474433
         *
         * works correctly if runLater in here
         * not working if currentInstance.show(); (from method configAutoFilterListener) is in runlater
         *
         * [http://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx]:
         * Use Platform.runLater(...) for quick and simple operations and Task for complex and big operations .
         */
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                if(StringUtils.isEmpty(filter)){
                    bufferList = FilterComboBox.this.readFromList(filter, initialList);
                }else {
                    StringBuilder regex = new StringBuilder();
                    for (int i = 0; i < filter.length(); i++) {
                        regex.append(filter.charAt(i));
                        regex.append(".*");
                    }
                    bufferList.clear();
                    /**
                     * TODO accidently pasted "Platform.runLater(new Runnable() {" and regex crashes because of "("
                     * unit test for regex
                     * Exception in thread "JavaFX Application Thread" java.util.regex.PatternSyntaxException: Unclosed group near index 81
                     * j.*a.*c.*o.*b.*P.*l.*a.*t.*f.*o.*r.*m.*..*r.*u.*n.*L.*a.*t.*e.*r.*(.*n.*e.*w.* .*
                     */
                    Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
                    for (String string : initialList) {
                        Matcher matcher = pattern.matcher(string);
                        if (matcher.find()) {
                            bufferList.add(string);
                        }
                    }
                }

        //        if (filter.startsWith(previousValue) && !previousValue.isEmpty()) {
        //            ObservableList<String> filteredList = this.readFromList(filter, bufferList);
        //            bufferList.clear();
        //            bufferList = filteredList;
        //        } else {
        //            bufferList = this.readFromList(filter, initialList);
        //        }
                comboBox.setItems(bufferList);
            }
        });
    }

    private ObservableList<String> readFromList(String filter, ObservableList<String> originalList) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String item : originalList) {
            if (item.toLowerCase().startsWith(filter.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    private void setUserInputToOnlyOption(ComboBox<String> currentInstance, final TextField editor) {
        final String onlyOption = currentInstance.getItems().get(0);
        final String currentText = editor.getText();
        if (onlyOption.length() > currentText.length()) {
            editor.setText(onlyOption);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    editor.selectRange(currentText.length(), onlyOption.length());
                }
            });
        }
    }
}
