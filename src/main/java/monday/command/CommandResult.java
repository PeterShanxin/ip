package monday.command;

/**
 * Represents the result of executing a command.
 * Contains flags indicating whether to save tasks and/or exit the application.
 */
public class CommandResult {

    private final boolean shouldSave;
    private final boolean shouldExit;

    /**
     * Creates a new command result.
     *
     * @param shouldSave true if tasks should be saved after this command.
     * @param shouldExit true if the application should exit after this command.
     */
    public CommandResult(boolean shouldSave, boolean shouldExit) {
        this.shouldSave = shouldSave;
        this.shouldExit = shouldExit;
    }

    /**
     * Checks if tasks should be saved after this command.
     *
     * @return true if tasks should be saved.
     */
    public boolean shouldSave() {
        return shouldSave;
    }

    /**
     * Checks if the application should exit after this command.
     *
     * @return true if the application should exit.
     */
    public boolean shouldExit() {
        return shouldExit;
    }
}
