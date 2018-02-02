package web;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by mcalancea
 * Date: 02 Feb 2018
 * Time: 16:39
 */
public class WebViewSample extends Application{
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //crate the scene
        primaryStage.setTitle("Web view");
        scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
        primaryStage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
