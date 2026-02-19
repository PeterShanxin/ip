package monday.ui;

import monday.Monday;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private GuiEventHandler eventHandler;

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
        GuiBuilder builder = new GuiBuilder();
        GuiStyler styler = new GuiStyler();

        // Step 1: Build GUI components
        dialogContainer = builder.buildDialogContainer();
        scrollPane = builder.buildScrollPane(dialogContainer);
        userInput = builder.buildInputField();
        sendButton = builder.buildSendButton();
        AnchorPane mainLayout = builder.buildMainLayout(scrollPane, userInput, sendButton);

        // Step 2: Apply styles
        styler.applyDialogContainerStyle(dialogContainer);
        styler.applyScrollPaneStyle(scrollPane);
        styler.applyInputFieldStyle(userInput);
        styler.applyButtonStyle(sendButton);
        styler.applyMainLayoutStyle(mainLayout);

        // Step 3: Configure scene
        scene = new Scene(mainLayout, 400, 600);
        styler.applySceneStyle(scene);
        styler.applyScrollbarStyle(scene);
        styler.configureStage(stage, scene);

        // Step 4: Setup event handling
        eventHandler = new GuiEventHandler(monday, userInput, dialogContainer, scrollPane);
        sendButton.setOnMouseClicked(event -> eventHandler.handleUserInput());
        userInput.setOnAction(event -> eventHandler.handleUserInput());

        stage.show();
    }

    /**
     * Shows a message in the dialog container.
     * Used for greeting and initial messages.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        eventHandler.showMessage(message);
    }
}
