package monday.command;

import monday.storage.Storage;
import monday.task.TaskList;
import monday.ui.Ui;

/**
 * Command to list all tasks.
 * Displays all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command.
     * Displays all tasks or a message if the list is empty.
     *
     * @param taskList The task list to display.
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showTaskList(taskList.getTasks());
        return new CommandResult(false, false);
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
}
