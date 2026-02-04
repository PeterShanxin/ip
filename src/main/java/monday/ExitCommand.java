package monday;

/**
 * Command to exit the application.
 * Displays a farewell message and signals exit.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command.
     * Displays the farewell message.
     *
     * @param taskList The task list (not used).
     * @param ui The UI for displaying messages.
     * @param storage The storage (not used).
     * @return A command result indicating exit without saving.
     */
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showFarewell();
        return new CommandResult(false, true);
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return true, as this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
