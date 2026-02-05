package monday.command;

import monday.storage.Storage;
import monday.task.Task;
import monday.task.TaskList;
import monday.ui.Ui;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Command to view tasks scheduled for a specific date.
 * Filters and displays tasks that occur on the given date.
 */
public class ViewCommand extends Command {

    private final LocalDateTime date;

    /**
     * Creates a new view command.
     *
     * @param date The date to view tasks for.
     */
    public ViewCommand(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Executes the view command.
     * Filters tasks by the specified date and displays them.
     *
     * @param taskList The task list to filter.
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        List<Task> filteredTasks = taskList.filterTasksByDate(date);
        ui.showFilteredTasks(filteredTasks, date);
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
