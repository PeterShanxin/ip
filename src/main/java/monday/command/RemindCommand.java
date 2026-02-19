package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskCounts;
import monday.task.TaskList;
import monday.ui.Ui;

/**
 * Command to show task summary and upcoming tasks.
 * Displays task counts and the earliest upcoming uncompleted task.
 */
public class RemindCommand extends Command {

    /**
     * Executes the remind command.
     * Displays task summary and earliest upcoming task.
     *
     * @param taskList The task list to analyze.
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        TaskCounts counts = taskList.getTaskCounts();
        Task earliestTask = taskList.getEarliestUpcomingTask();

        ui.showReminders(counts, earliestTask);
        return new CommandResult(false, false);
    }
}
