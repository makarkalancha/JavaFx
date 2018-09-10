package web;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by mcalancea
 * Date: 02 Feb 2018
 * Time: 16:39
 */
public class WebViewSample extends Application{
    private Scene scene;

//    https://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm
//    continue from:
//    In this code, the web engine loads a URL that points to the Oracle corporate web site. The WebView object that contains this web engine is added to the application scene by using the getChildren and add methods.

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create the scene
        primaryStage.setTitle("Web view");
        scene = new Scene(new Browser_v5(), 750, 500, Color.web("#dc143c"));
//        scene = new Scene(new Browser_v1(), 750, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
