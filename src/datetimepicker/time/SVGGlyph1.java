package datetimepicker.time;

import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

/**
 * Created by mcalancea
 * Date: 10 Sep 2018
 * Time: 17:02
 */
public class SVGGlyph1  extends Pane {
    private static final String DEFAULT_STYLE_CLASS = "jfx-svg-glyph";
    private final int glyphId;
    private final String name;
    private static final int DEFAULT_PREF_SIZE = 64;
    private ObjectProperty<Paint> fill = new SimpleObjectProperty();

    public SVGGlyph1(int glyphId, String name, String svgPathContent, Paint fill) {
        this.glyphId = glyphId;
        this.name = name;
        this.getStyleClass().add("jfx-svg-glyph");
        this.fill.addListener(new ChangeListener<Paint>() {
            @Override
            public void changed(ObservableValue<? extends Paint> observable, Paint oldValue, Paint newValue) {
                System.out.println("hello ");
//                SVGGlyph.lambda$new$0(observable, (Paint)oldValue, (Paint)newValue);
            }
        });
        SVGPath shape = new SVGPath();
        shape.setContent(svgPathContent);
        this.setShape(shape);
        this.setFill(fill);
        this.setPrefSize(64.0D, 64.0D);
    }

    public int getGlyphId() {
        return this.glyphId;
    }

    public String getName() {
        return this.name;
    }

    public void setFill(Paint fill) {
        this.fill.setValue(fill);
    }

    public ObjectProperty<Paint> fillProperty() {
        return this.fill;
    }

    public Paint getFill() {
        return (Paint)this.fill.getValue();
    }

    public void setSize(double width, double height) {
        this.setMinSize(-1.0D / 0.0, -1.0D / 0.0);
        this.setPrefSize(width, height);
        this.setMaxSize(-1.0D / 0.0, -1.0D / 0.0);
    }
}
