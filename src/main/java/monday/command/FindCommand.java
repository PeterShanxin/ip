package monday.command;

import monday.task.TaskList;
import monday.ui.Ui;
import monday.storage.Storage;

import java.util.List;

/**
 * Command to find tasks containing a specific keyword in their description.
 * Performs case-insensitive substring matching on task descriptions.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Creates a FindCommand with the specified search keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command.
     * Searches for tasks containing the keyword and displays matching tasks.
     *
     * @param taskList The task list to search.
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        List<monday.task.Task> matchingTasks = taskList.getFilteredTasks(keyword);
        ui.showMatchingTasks(matchingTasks, keyword);
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
