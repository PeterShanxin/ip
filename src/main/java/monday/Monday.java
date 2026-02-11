package monday;

import monday.command.Command;
import monday.command.CommandException;
import monday.command.CommandResult;
import monday.exception.MondayStorageException;
import monday.exception.ParseException;
import monday.parser.Parser;
import monday.storage.Storage;
import monday.task.LoadResult;
import monday.task.TaskList;
import monday.ui.MainWindow;
import monday.ui.Ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Now with a GUI because apparently CLI was too "inconvenient."
 */
public class Monday extends Application {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private MainWindow mainWindow;

    private boolean hasCorruption;

    /**
     * Creates a new Monday instance with the required components.
     */
    public Monday() {
        ui = new Ui();
        storage = new Storage("data", "monday.txt");
        parser = new Parser();
    }

    @Override
    public void start(Stage primaryStage) {
        // Load tasks first
        hasCorruption = loadTasks();

        // Setup GUI
        mainWindow = new MainWindow();
        mainWindow.setMonday(this);
        mainWindow.start(primaryStage);

        // Show greeting
        String greeting = ui.getGreetingForGui();
        mainWindow.showMessage(greeting);

        if (hasCorruption) {
            LoadResult loadResult = storage.getLoadResult();
            mainWindow.showMessage("Ugh. I skipped " + loadResult.getCorruptedLineCount()
                + " corrupted lines.\nCheck monday.txt.corrupted for recovery.");
        }
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

            Command command = parser.parseCommand(userInput);
            CommandResult result = command.execute(taskList, ui, storage);

            if (result.shouldSave()) {
                saveTasksIfPossible();
            }

            if (result.shouldExit()) {
                // Save on exit if corruption was detected
                if (hasCorruption) {
                    saveTasksIfPossible();
                }
                // Schedule exit after current event processing
                javafx.application.Platform.exit();
            }

            return ui.getLastResponse();

        } catch (ParseException | CommandException e) {
            return "Warning: " + e.getMessage();
        }
    }

    /**
     * Loads tasks from storage and initializes the task list.
     * Handles any corruption errors during loading.
     *
     * @return true if corruption was detected during load.
     */
    private boolean loadTasks() {
        try {
            LoadResult loadResult = storage.loadTasks();
            taskList = new TaskList(loadResult.getTasks());
            if (loadResult.hasCorruption()) {
                ui.showCorruptionMessage(loadResult.getCorruptedLineCount());
            }
            return loadResult.hasCorruption();
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
            taskList = new TaskList();
            return false;
        }
    }

    /**
     * Saves tasks to storage if possible.
     * Catches any storage exceptions and prints a warning to stderr.
     */
    private void saveTasksIfPossible() {
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
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
