package monday.command;

import monday.task.Task;
import monday.task.TaskList;
import monday.ui.Ui;
import monday.storage.Storage;

/**
 * Abstract command to add a task to the task list.
 * Subclasses implement the specific task creation logic.
 */
public abstract class AddCommand extends Command {

    protected final String description;

    /**
     * Creates a new add command.
     *
     * @param description The task description.
     */
    public AddCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the add command.
     * Validates, creates the task, adds it to the list, and saves.
     *
     * @param taskList The task list to modify.
     * @param ui The UI for displaying messages.
     * @param storage The storage for persisting changes.
     * @return A command result indicating save is needed, no exit.
     * @throws CommandException If the task cannot be added.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws CommandException {
        if (taskList.isAtMaxCapacity()) {
            throw new CommandException("Fine. I can't remember more than 100 things. Forget something first.");
        }

        Task task = createTask();
        taskList.addTask(task);
        ui.showTaskAdded(task, taskList.getTaskCount());
        return new CommandResult(true, false);
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as this is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Creates the specific task instance.
     * Subclasses implement this to create their specific task type.
     *
     * @return The created task.
     * @throws CommandException If the task cannot be created.
     */
    protected abstract Task createTask() throws CommandException;
}
