package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskList;
import monday.ui.Ui;

/**
 * Command to delete a task from the task list.
 * Removes the specified task and updates the task count.
 */
public class DeleteCommand extends Command {

    private final int taskNumber;

    /**
     * Creates a new delete command.
     *
     * @param taskNumber The 1-indexed task number to delete.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command.
     * Deletes the specified task and saves.
     *
     * @param taskList The task list to modify.
     * @param ui The UI for displaying messages.
     * @param storage The storage for persisting changes.
     * @return A command result indicating save is needed, no exit.
     * @throws CommandException If the task number is invalid.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws CommandException {
        if (!taskList.isValidTaskNumber(taskNumber)) {
            throw new CommandException(taskList.getInvalidTaskNumberMessage());
        }

        Task deletedTask = taskList.deleteTask(taskNumber);
        ui.showTaskDeleted(deletedTask, taskList.getTaskCount());
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
}
