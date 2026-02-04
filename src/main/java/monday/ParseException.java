package monday;

/**
 * Represents an exception that occurs during command parsing.
 * Thrown when user input cannot be parsed into a valid command.
 */
public class ParseException extends Exception {

    /**
     * Creates a new parse exception with the specified message.
     *
     * @param message The error message describing what went wrong.
     */
    public ParseException(String message) {
        super(message);
    }
}
