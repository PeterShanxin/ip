package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskList;
import monday.ui.Ui;

/**
 * Command to mark or unmark a task as done.
 * Handles both mark and unmark operations based on the markAsDone flag.
 */
public class MarkCommand extends Command {

    private final int taskNumber;
    private final boolean markAsDone;

    /**
     * Creates a new mark command.
     *
     * @param taskNumber The 1-indexed task number to mark/unmark.
     * @param markAsDone true to mark as done, false to mark as not done.
     */
    public MarkCommand(int taskNumber, boolean markAsDone) {
        this.taskNumber = taskNumber;
        this.markAsDone = markAsDone;
    }

    /**
     * Executes the mark/unmark command.
     * Marks or unmarks the specified task and saves.
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

        Task task = taskList.getTask(taskNumber);
        if (markAsDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        ui.showTaskMarked(task, markAsDone);
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
