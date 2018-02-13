package web;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
public class Browser_v3 extends Region{
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
    private boolean needDocumentationButton = false;
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();
    private final Hyperlink[] hpls = new Hyperlink[captions.length];
    private final Button showPrevDoc = new Button("Toggle Previous Docs");

    public Browser_v3() {
        //apply the styles
        getStyleClass().add("browser");
        for (int i = 0; i < captions.length; i++) {
            // create hyperlinks
            Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
            final String url = urls[i];
            final boolean addButton = (hpl.getText().equals("Documentation"));

            // process event
            hpl.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    needDocumentationButton = addButton;
                    webEngine.load(url);
                }
            });
        }

// create the toolbar
        toolBar = new HBox();
        toolBar.setAlignment(Pos.CENTER);
        toolBar.getStyleClass().add("browser-toolbar");
        toolBar.getChildren().addAll(hpls);
        toolBar.getChildren().add(createSpacer());

        //set action for the button
        showPrevDoc.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
//                webEngine.executeScript("toggleDisplay('PrevRel')");
                webEngine.executeScript("$(\".innertube-10\").toggle();");
            }
        });

        // process page loading
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue<? extends State> ov,
                                        State oldState, State newState) {
                        toolBar.getChildren().remove(showPrevDoc);
                        if (newState == State.SUCCEEDED) {
                            if (needDocumentationButton) {
                                toolBar.getChildren().add(showPrevDoc);
                            }
                        }
                    }
                }
        );

        // load the home page
        webEngine.load("http://www.oracle.com/products/index.html");

        //add components
        getChildren().add(toolBar);
        getChildren().add(browser);
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
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
