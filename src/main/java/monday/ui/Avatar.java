package monday.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A circular letter avatar for use in dialog boxes.
 * Displays a single letter centered inside a filled circle.
 */
public class Avatar extends StackPane {

    private static final double RADIUS = 14.0;
    private static final double FONT_SIZE = 12.0;

    /**
     * Creates a circular avatar with a single letter.
     *
     * @param letter The letter to display inside the circle.
     * @param circleColor The fill color of the circle.
     */
    public Avatar(String letter, Color circleColor) {
        Circle circle = new Circle(RADIUS);
        circle.setFill(circleColor);

        Text text = new Text(letter);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(FONT_SIZE));

        this.getChildren().addAll(circle, text);
        this.setAlignment(Pos.CENTER);
        this.setMinSize(RADIUS * 2, RADIUS * 2);
        this.setMaxSize(RADIUS * 2, RADIUS * 2);
    }
}
