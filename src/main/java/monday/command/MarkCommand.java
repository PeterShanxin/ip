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
        taskList.validateTaskNumber(taskNumber);

        Task task = taskList.getTask(taskNumber);
        assert task != null : "Task should not be null";
        if (markAsDone) {
            task.markAsDone();
            // Postcondition: Task should be marked as done
        } else {
            task.markAsNotDone();
            assert !task.isDone() : "Task should be marked as not done";
        }

        ui.showTaskMarked(task, markAsDone);
        return new CommandResult(true, false);
    }
}
