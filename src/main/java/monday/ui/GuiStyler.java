package monday.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Handles styling for GUI components.
 * Applies the dark theme and consistent styling across all UI elements.
 */
public class GuiStyler {

    // Color constants for the dark theme
    private static final String BACKGROUND_COLOR = "#1e1e1e";
    private static final String SECONDARY_COLOR = "#2d2d2d";
    private static final String TERTIARY_COLOR = "#3d3d3d";
    private static final String BORDER_COLOR = "#404040";
    private static final String BUTTON_BORDER_COLOR = "#505050";
    private static final String TEXT_COLOR = "#e0e0e0";
    private static final String PROMPT_TEXT_COLOR = "#888888";

    /**
     * Applies the dark theme to the dialog container.
     *
     * @param dialogContainer The VBox to style.
     */
    public void applyDialogContainerStyle(VBox dialogContainer) {
        dialogContainer.setPadding(new Insets(10));
        dialogContainer.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
    }

    /**
     * Applies the dark theme to the scroll pane.
     *
     * @param scrollPane The ScrollPane to style.
     */
    public void applyScrollPaneStyle(ScrollPane scrollPane) {
        scrollPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + "; "
            + "-fx-border-color: " + SECONDARY_COLOR + "; "
            + "-fx-background: " + BACKGROUND_COLOR + "; "
            + "-fx-control-inner-background: " + BACKGROUND_COLOR + "; "
            + "-fx-background-insets: 0;");
    }

    /**
     * Applies the dark theme to the input field.
     *
     * @param userInput The TextField to style.
     */
    public void applyInputFieldStyle(TextField userInput) {
        userInput.setStyle("-fx-background-color: " + SECONDARY_COLOR + "; "
            + "-fx-text-fill: " + TEXT_COLOR + "; "
            + "-fx-prompt-text-fill: " + PROMPT_TEXT_COLOR + "; "
            + "-fx-border-color: " + BORDER_COLOR + "; "
            + "-fx-border-radius: 4px; "
            + "-fx-background-radius: 4px;");
    }

    /**
     * Applies the dark theme to the send button.
     *
     * @param sendButton The Button to style.
     */
    public void applyButtonStyle(Button sendButton) {
        sendButton.setStyle("-fx-background-color: " + TERTIARY_COLOR + "; "
            + "-fx-text-fill: " + TEXT_COLOR + "; "
            + "-fx-border-color: " + BUTTON_BORDER_COLOR + "; "
            + "-fx-border-radius: 4px; "
            + "-fx-background-radius: 4px;");
    }

    /**
     * Applies the dark theme to the main layout.
     *
     * @param mainLayout The AnchorPane to style.
     */
    public void applyMainLayoutStyle(AnchorPane mainLayout) {
        mainLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
    }

    /**
     * Applies the dark theme to the scene.
     *
     * @param scene The Scene to style.
     */
    public void applySceneStyle(Scene scene) {
        scene.setFill(Color.web(BACKGROUND_COLOR));
    }

    /**
     * Applies custom scrollbar styling to the scene.
     *
     * @param scene The Scene to add scrollbar styling to.
     */
    public void applyScrollbarStyle(Scene scene) {
        String scrollbarCss = ".scroll-bar {"
            + "  -fx-background-color: " + BACKGROUND_COLOR + ";"
            + "}"
            + ".scroll-bar .thumb {"
            + "  -fx-background-color: " + BORDER_COLOR + ";"
            + "  -fx-background-radius: 4px;"
            + "}"
            + ".scroll-bar .track {"
            + "  -fx-background-color: " + SECONDARY_COLOR + ";"
            + "}"
            + ".scroll-bar .increment-button, .scroll-bar .decrement-button {"
            + "  -fx-background-color: " + SECONDARY_COLOR + ";"
            + "  -fx-padding: 0;"
            + "}"
            + ".scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {"
            + "  -fx-shape: \"\";"
            + "  -fx-padding: 0;"
            + "}";
        
        scene.getStylesheets().add("data:text/css," + scrollbarCss);
    }

    /**
     * Configures the stage with title and scene.
     *
     * @param stage The Stage to configure.
     * @param scene The Scene to set on the stage.
     */
    public void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("MONDAY - Grumpy Task Manager");
    }
}
