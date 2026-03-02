package monday.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A dialog box component displaying a message in an asymmetric chat layout.
 * User messages appear right-aligned with a "U" avatar.
 * Bot messages appear left-aligned with an "M" avatar.
 * Error responses use a distinct red-tinted style.
 */
public class DialogBox extends HBox {

    private static final Color BOT_AVATAR_COLOR = Color.web("#5a4a6e");
    private static final Color USER_AVATAR_COLOR = Color.web("#3d5a6e");
    private static final double BUBBLE_MAX_WIDTH_RATIO = 0.70;
    private static final double AVATAR_SPACING = 6.0;
    private static final Insets BOX_PADDING = new Insets(4, 8, 4, 8);

    /**
     * Creates a new dialog box.
     *
     * @param text The text content to display.
     * @param isUser true if this is a user message; false if bot response.
     * @param isError true if this is an error response (only applies when isUser is false).
     */
    public DialogBox(String text, boolean isUser, boolean isError) {
        Label bubble = createBubble(text, isUser, isError);
        Avatar avatar = isUser
                ? new Avatar("U", USER_AVATAR_COLOR)
                : new Avatar("M", BOT_AVATAR_COLOR);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bubble.maxWidthProperty().bind(this.widthProperty().multiply(BUBBLE_MAX_WIDTH_RATIO));

        this.setPadding(BOX_PADDING);
        this.setSpacing(AVATAR_SPACING);
        this.setStyle("-fx-background-color: transparent;");

        if (isUser) {
            this.setAlignment(Pos.CENTER_RIGHT);
            this.getChildren().addAll(spacer, bubble, avatar);
        } else {
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(avatar, bubble, spacer);
        }
    }

    /**
     * Creates the styled text bubble label.
     *
     * @param text The text to display.
     * @param isUser true if user bubble, false if bot bubble.
     * @param isError true if error style should be applied.
     * @return The configured Label.
     */
    private Label createBubble(String text, boolean isUser, boolean isError) {
        Label label = new Label(text);
        label.setWrapText(true);

        if (isUser) {
            label.getStyleClass().add("user-bubble");
        } else if (isError) {
            label.getStyleClass().add("error-bubble");
        } else {
            label.getStyleClass().add("bot-bubble");
        }

        return label;
    }
}
