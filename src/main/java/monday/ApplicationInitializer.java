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
 * Handles initialization of application components.
 * Manages the setup of UI, storage, parser, and task list.
 */
public class ApplicationInitializer {

    private Ui ui;
    private Storage storage;
    private Parser parser;
    private TaskList taskList;
    private boolean hasCorruption;

    /**
     * Creates a new ApplicationInitializer.
     */
    public ApplicationInitializer() {
        initializeComponents();
    }

    /**
     * Initializes all application components.
     */
    private void initializeComponents() {
        ui = new Ui();
        storage = new Storage("data", "monday.txt");
        parser = new Parser();
    }

    /**
     * Loads task data from storage.
     *
     * @return true if corruption was detected during load.
     */
    public boolean loadTaskData() {
        try {
            LoadResult loadResult = storage.loadTasks();
            taskList = new TaskList(loadResult.getTasks());
            if (loadResult.hasCorruption()) {
                ui.showCorruptionMessage(loadResult.getCorruptedLineCount());
            }
            hasCorruption = loadResult.hasCorruption();
            return hasCorruption;
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
            taskList = new TaskList();
            hasCorruption = false;
            return hasCorruption;
        }
    }

    /**
     * Gets the UI component.
     *
     * @return The Ui instance.
     */
    public Ui getUi() {
        return ui;
    }

    /**
     * Gets the storage component.
     *
     * @return The Storage instance.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Gets the parser component.
     *
     * @return The Parser instance.
     */
    public Parser getParser() {
        return parser;
    }

    /**
     * Gets the task list component.
     *
     * @return The TaskList instance.
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Checks if corruption was detected during load.
     *
     * @return true if corruption was detected.
     */
    public boolean hasCorruption() {
        return hasCorruption;
    }
}
