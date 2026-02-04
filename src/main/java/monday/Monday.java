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
import monday.ui.Ui;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Named after everyone's least favorite day of the week, Monday has a
 * sarcastic personality but gets the job done.
 */
public class Monday {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;

    /**
     * Creates a new Monday instance with the required components.
     */
    public Monday() {
        ui = new Ui();
        storage = new Storage("data", "monday.txt");
        parser = new Parser();
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
     * Runs the main application loop.
     * Greets the user, processes commands, and exits when requested.
     */
    public void run() {
        // Grumpy greeting
        ui.showGreeting();

        // Load tasks from storage and track corruption
        boolean hasCorruption = loadTasks();

        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();

                if (userInput.isEmpty()) {
                    ui.showEmptyInputError();
                    continue;
                }

                Command command = parser.parseCommand(userInput);
                CommandResult result = command.execute(taskList, ui, storage);

                if (result.shouldSave()) {
                    saveTasksIfPossible();
                }

                isExit = result.shouldExit();
            } catch (ParseException | CommandException e) {
                ui.showError(e.getMessage());
            }
        }

        // Save on exit if corruption was detected during load
        // This ensures corrupted lines are removed from monday.txt
        // even if user didn't make any changes
        if (hasCorruption) {
            saveTasksIfPossible();
        }

        ui.close();
    }

    /**
     * Entry point for the Monday chatbot application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Monday().run();
    }
}
