package monday;

import monday.command.Command;
import monday.command.CommandException;
import monday.command.CommandResult;
import monday.exception.ErrorHandler;
import monday.exception.MondayStorageException;
import monday.exception.ParseException;
import monday.parser.Parser;
import monday.storage.Storage;
import monday.task.TaskList;
import monday.ui.GuiResponse;
import monday.ui.Ui;

/**
 * Processes user commands and handles command execution.
 * Manages the command parsing, execution, and response generation.
 */
public class CommandProcessor {

    private final Parser parser;
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private final boolean hasCorruption;

    /**
     * Creates a new CommandProcessor with the required components.
     *
     * @param parser The Parser for parsing commands.
     * @param ui The Ui for displaying responses.
     * @param storage The Storage for saving tasks.
     * @param taskList The TaskList for task management.
     * @param hasCorruption Whether corruption was detected during load.
     */
    public CommandProcessor(Parser parser, Ui ui, Storage storage, 
                           TaskList taskList, boolean hasCorruption) {
        this.parser = parser;
        this.ui = ui;
        this.storage = storage;
        this.taskList = taskList;
        this.hasCorruption = hasCorruption;
    }

    /**
     * Processes a user input and returns the response.
     *
     * @param userInput The user's input string.
     * @return The GuiResponse with text and error state.
     */
    public GuiResponse processCommand(String userInput) {
        try {
            Command command = parser.parseCommand(userInput);
            CommandResult result = command.execute(taskList, ui, storage);

            if (result.shouldSave()) {
                saveTasksIfPossible();
            }

            if (result.shouldExit()) {
                prepareExit();
            }

            return new GuiResponse(ui.getLastResponse(), false, result.shouldExit());

        } catch (ParseException e) {
            return new GuiResponse(ErrorHandler.handleParseException(e), true, false);
        } catch (CommandException e) {
            return new GuiResponse(ErrorHandler.handleCommandException(e), true, false);
        }
    }

    /**
     * Handles the exit command.
     * Saves tasks if corruption was detected before the GUI exits.
     */
    private void prepareExit() {
        // Save on exit if corruption was detected
        if (hasCorruption) {
            saveTasksIfPossible();
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
            System.err.println(ErrorHandler.handleStorageException(e));
        }
    }
}
