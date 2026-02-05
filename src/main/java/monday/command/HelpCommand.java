package monday.command;

import monday.storage.Storage;
import monday.task.TaskList;
import monday.ui.Ui;

/**
 * Command to display help information.
 * Shows all available commands with usage instructions.
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command.
     * Displays the help message with all available commands.
     *
     * @param taskList The task list (not used).
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating no save or exit needed.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showHelp();
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
