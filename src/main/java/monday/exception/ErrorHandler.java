package monday.exception;

import monday.command.CommandException;

/**
 * Centralized error handling for the Monday application.
 * Provides consistent exception handling and error message formatting across the codebase.
 */
public class ErrorHandler {

    private static final String WARNING_PREFIX = "Warning: ";
    private static final String STORAGE_ERROR_PREFIX = "Ugh. ";

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private ErrorHandler() {
        throw new AssertionError("ErrorHandler is a utility class and cannot be instantiated.");
    }

    /**
     * Handles a ParseException and returns a formatted error message.
     *
     * @param e The ParseException to handle.
     * @return A formatted error message for display to the user.
     */
    public static String handleParseException(ParseException e) {
        return formatErrorMessage(e);
    }

    /**
     * Handles a CommandException and returns a formatted error message.
     *
     * @param e The CommandException to handle.
     * @return A formatted error message for display to the user.
     */
    public static String handleCommandException(CommandException e) {
        return formatErrorMessage(e);
    }

    /**
     * Handles a MondayStorageException and returns a formatted error message.
     *
     * @param e The MondayStorageException to handle.
     * @return A formatted error message for display to the user.
     */
    public static String handleStorageException(MondayStorageException e) {
        return formatErrorMessage(e);
    }

    /**
     * Handles an unexpected exception and returns a formatted error message.
     * This should be used for exceptions that are not expected during normal operation.
     *
     * @param e The unexpected exception to handle.
     * @return A formatted error message for display to the user.
     */
    public static String handleUnexpectedException(Exception e) {
        return WARNING_PREFIX + "Something unexpected happened: " + e.getMessage();
    }

    /**
     * Formats an exception message for display to the user.
     * Uses a consistent "Warning: " prefix for all exceptions.
     *
     * @param e The exception to format.
     * @return A formatted error message.
     */
    public static String formatErrorMessage(Exception e) {
        return WARNING_PREFIX + e.getMessage();
    }

    /**
     * Creates a storage error message with the "Ugh." prefix.
     * Used by Storage class to create consistent storage error messages.
     *
     * @param message The error message.
     * @return A formatted storage error message.
     */
    public static String createStorageErrorMessage(String message) {
        return STORAGE_ERROR_PREFIX + message;
    }

    /**
     * Creates a storage error message with the "Ugh." prefix and includes the original exception.
     * Used by Storage class to create consistent storage error messages with context.
     *
     * @param message The error message.
     * @param e The original exception that caused the error.
     * @return A formatted storage error message with context.
     */
    public static String createStorageErrorMessage(String message, Exception e) {
        return STORAGE_ERROR_PREFIX + message + " " + e.getMessage();
    }
}
