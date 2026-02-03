package monday;

/**
 * Represents an exception that occurs during storage operations.
 * Thrown when file I/O operations fail for loading or saving tasks.
 */
public class MondayStorageException extends Exception {

    /**
     * Creates a new storage exception with the specified message.
     *
     * @param message The error message describing what went wrong.
     */
    public MondayStorageException(String message) {
        super(message);
    }
}
