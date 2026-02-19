package monday.ui;

import monday.Monday;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
        DialogBox userDialog = new DialogBox(input, true);
        dialogContainer.getChildren().add(userDialog);

        userInput.clear();

        // Execute command through Monday
        String response = monday.getResponse(input);
        DialogBox mondayDialog = new DialogBox(response, false);
        dialogContainer.getChildren().add(mondayDialog);

        // Auto-scroll to bottom
        scrollPane.setVvalue(1.0);
    }

    /**
     * Shows a message in the dialog container.
     * Used for greeting and initial messages.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        DialogBox dialog = new DialogBox(message, false);
        dialogContainer.getChildren().add(dialog);
        scrollPane.setVvalue(1.0);
    }
}
