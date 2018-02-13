package web;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by mcalancea
 * Date: 02 Feb 2018
 * Time: 16:42
 */
public class Browser_v2 extends Region{
    private static String[] captions = new String[]{
            "Products",
            "Blogs",
            "Documentation",
            "Partners"
    };

    private static String[] urls = new String[]{
            "http://www.oracle.com/products/index.html",
            "http://blogs.oracle.com/",
            "http://docs.oracle.com/javase/index.html",
            "http://www.oracle.com/partners/index.html"
    };

    private HBox toolBar;
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();
    private final Hyperlink[] hpls = new Hyperlink[captions.length];

    public Browser_v2() {
        //apply the styles
        getStyleClass().add("browser");
        for (int i = 0; i < captions.length; i++) {
            final Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
            final String url = urls[i];

            hpl.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    webEngine.load(url);
                }
            });
        }

// load the home page
        webEngine.load("http://www.oracle.com/products/index.html");

// create the toolbar
        toolBar = new HBox();
        toolBar.getStyleClass().add("browser-toolbar");
        toolBar.getChildren().addAll(hpls);

        //add components
        getChildren().add(toolBar);
        getChildren().add(browser);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        double tbHeight = toolBar.prefHeight(w);
        layoutInArea(browser, 0, 0, w, h - tbHeight, 0, HPos.CENTER, VPos.CENTER);
        layoutInArea(toolBar, 0, h - tbHeight, w, tbHeight, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 750;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
