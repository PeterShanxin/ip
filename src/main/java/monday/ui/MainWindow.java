package monday.ui;

import monday.Monday;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main GUI window for MONDAY.
 * Displays a dialog interface with input field and output area.
 */
public class MainWindow extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Monday monday;

    /**
     * Sets the Monday instance for command execution.
     *
     * @param monday The Monday instance to use for command processing.
     */
    public void setMonday(Monday monday) {
        this.monday = monday;
    }

    @Override
    public void start(Stage stage) {
        // Step 1: Setup container
        dialogContainer = new VBox();
        dialogContainer.setPadding(new Insets(10));
        dialogContainer.setSpacing(10);
        dialogContainer.prefHeight(Region.USE_COMPUTED_SIZE);

        scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);

        userInput = new TextField();
        userInput.setPromptText("Tell me what to do...");
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        // Layout constraints
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 60.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        AnchorPane.setTopAnchor(userInput, 560.0);
        AnchorPane.setLeftAnchor(userInput, 0.0);
        AnchorPane.setRightAnchor(userInput, 80.0);

        AnchorPane.setTopAnchor(sendButton, 560.0);
        AnchorPane.setRightAnchor(sendButton, 0.0);

        // Step 2: Configure scene
        scene = new Scene(mainLayout, 400, 600);
        stage.setScene(scene);
        stage.setTitle("MONDAY - Grumpy Task Manager");

        // Step 3: Handle input
        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        stage.show();
    }

    /**
     * Handles user input from the text field.
     * Parses command, executes, and displays result.
     */
    private void handleUserInput() {
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
