package monday.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * A dialog box component displaying text.
 * Used for both user input and MONDAY's responses.
 */
public class DialogBox extends HBox {

    /**
     * Creates a new dialog box.
     *
     * @param text The text content.
     * @param isUser true if user message, false if MONDAY's response.
     */
    public DialogBox(String text, boolean isUser) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setTextFill(Color.web("#e0e0e0"));
        label.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        if (isUser) {
            label.setBackground(new Background(new BackgroundFill(
                Color.web("#3d4a5c"), new CornerRadii(5), Insets.EMPTY)));
            label.setPadding(new Insets(10));
        } else {
            label.setBackground(new Background(new BackgroundFill(
                Color.web("#2d2d2d"), new CornerRadii(5), Insets.EMPTY)));
            label.setPadding(new Insets(10));
        }

        this.getChildren().add(label);
        this.setPadding(new Insets(5));
        this.setStyle("-fx-background-color: #1e1e1e; -fx-border-color: transparent;");
    }
}
