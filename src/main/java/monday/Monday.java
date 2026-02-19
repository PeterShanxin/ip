package monday;

import monday.exception.ErrorHandler;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Now with a GUI because apparently CLI was too "inconvenient."
 */
public class Monday extends Application {

    private CommandProcessor commandProcessor;

    /**
     * Creates a new Monday instance with the required components.
     */
    public Monday() {
        // Initialization is now handled by ApplicationInitializer
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize application components
        ApplicationInitializer initializer = new ApplicationInitializer();
        
        // Load tasks
        boolean hasCorruption = initializer.loadTaskData();

        // Create command processor
        commandProcessor = new CommandProcessor(
            initializer.getParser(),
            initializer.getUi(),
            initializer.getStorage(),
            initializer.getTaskList(),
            hasCorruption
        );

        // Setup GUI
        GuiOrchestrator guiOrchestrator = new GuiOrchestrator(
            this,
            initializer.getUi(),
            initializer.getStorage(),
            hasCorruption
        );
        guiOrchestrator.setupGui(primaryStage);
    }

    /**
     * Gets a response for the given user input.
     * Called by GUI when user submits a command.
     *
     * @param userInput The user's input string.
     * @return The response to display.
     */
    public String getResponse(String userInput) {
        try {
            if (userInput.isEmpty()) {
                return "Ugh, you didn't actually say anything. Try again.";
            }

            return commandProcessor.processCommand(userInput);

        } catch (Exception e) {
            return ErrorHandler.handleUnexpectedException(e);
        }
    }

    /**
     * Entry point for the Monday chatbot application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
