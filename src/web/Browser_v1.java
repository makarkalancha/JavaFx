package web;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * Time: 16:42
 */
public class Browser_v1 extends Region{
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    public Browser_v1() {
        //apply the styles
        getStyleClass().add("browser");
        //load the web page
        webEngine.load("https://www.oracle.com/products/index.html");
        //add the web view to the scene
        getChildren().add(browser);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefHeight(double width) {
        return 750;
    }

    @Override
    protected double computePrefWidth(double height) {
        return 500;
    }
}
