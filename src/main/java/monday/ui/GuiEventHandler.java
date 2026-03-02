package monday.ui;

import monday.Monday;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Handles GUI event interactions for the MainWindow.
 * Manages user input processing and message display.
 */
public class GuiEventHandler {

    private final Monday monday;
    private final TextField userInput;
    private final VBox dialogContainer;
    private final ScrollPane scrollPane;

    /**
     * Creates a new GuiEventHandler with the required components.
     *
     * @param monday The Monday instance for command processing.
     * @param userInput The text field for user input.
     * @param dialogContainer The container for displaying dialogs.
     * @param scrollPane The scroll pane for scrolling dialogs.
     */
    public GuiEventHandler(Monday monday, TextField userInput, 
                           VBox dialogContainer, ScrollPane scrollPane) {
        this.monday = monday;
        this.userInput = userInput;
        this.dialogContainer = dialogContainer;
        this.scrollPane = scrollPane;
    }

    /**
     * Handles user input from the text field.
     * Parses command, executes, and displays result.
     */
    public void handleUserInput() {
        String input = userInput.getText();
        DialogBox userDialog = new DialogBox(input, true, false);
        dialogContainer.getChildren().add(userDialog);

        userInput.clear();

        GuiResponse response = monday.getResponse(input);
        DialogBox mondayDialog = new DialogBox(response.text(), false, response.isError());
        dialogContainer.getChildren().add(mondayDialog);

        // Scroll to bottom using Timeline to ensure all layout passes complete
        scrollToBottom();
    }
    
    /**
     * Scrolls the ScrollPane to the bottom after a delay to ensure layout is complete.
     * Uses Platform.runLater + Timeline to handle JavaFX layout timing issues.
     */
    private void scrollToBottom() {
        // First runLater to ensure content is added
        Platform.runLater(() -> {
            // Set initial scroll position
            scrollPane.setVvalue(1.0);
            
            // Use Timeline with small delay to ensure all layout passes complete
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(50),
                event -> {
                    scrollPane.setVvalue(1.0);
                }
            ));
            timeline.play();
        });
    }

    /**
     * Shows a bot message in the dialog container.
     * Used for greeting and initial messages.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        DialogBox dialog = new DialogBox(message, false, false);
        dialogContainer.getChildren().add(dialog);
        
        // Scroll to bottom using the same Timeline-based approach
        scrollToBottom();
    }
}
