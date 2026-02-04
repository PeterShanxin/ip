package monday.command;

/**
 * Represents an exception that occurs during command execution.
 * Thrown when a command cannot be executed successfully.
 */
public class CommandException extends Exception {

    /**
     * Creates a new command exception with the specified message.
     *
     * @param message The error message describing what went wrong.
     */
    public CommandException(String message) {
        super(message);
    }
}
