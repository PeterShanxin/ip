package monday;

/**
 * Represents a command that can be executed by MONDAY.
 * All concrete commands must extend this abstract class.
 */
public abstract class Command {

    /**
     * Executes this command with the given dependencies.
     *
     * @param taskList The task list to operate on.
     * @param ui The UI for displaying messages.
     * @param storage The storage for persisting changes.
     * @return The result of executing this command.
     * @throws CommandException If execution fails.
     */
    public abstract CommandResult execute(TaskList taskList, Ui ui, Storage storage)
            throws CommandException;

    /**
     * Checks if this command should exit the application.
     *
     * @return true if this command signals exit.
     */
    public abstract boolean isExit();
}
