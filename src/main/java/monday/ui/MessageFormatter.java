package monday.ui;

import monday.constants.MessageConstants;

/**
 * Formats messages for display.
 */
public class MessageFormatter {

    /**
     * Wraps a message with line separators and blank lines.
     *
     * @param message The message to wrap.
     * @return The wrapped message as a string.
     */
    private String wrapWithLine(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageConstants.LINE_SEPARATOR).append("\n");
        sb.append("\n");  // blank line after opening LINE
        sb.append(message).append("\n");
        sb.append(MessageConstants.LINE_SEPARATOR).append("\n");
        sb.append("\n");  // blank line after closing LINE
        return sb.toString();
    }

    /**
     * Displays a response wrapped with line separators and blank lines.
     *
     * @param message The response message to display (can contain newlines).
     */
    public void showResponse(String message) {
        System.out.println(wrapWithLine(message));
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showResponse(message);
    }

    /**
     * Displays an error message for empty input.
     */
    public void showEmptyInputError() {
        showResponse(MessageConstants.ERROR_EMPTY_INPUT);
    }

    /**
     * Displays an error message for a command without arguments.
     *
     * @param command The command that needs arguments.
     * @param example The example of correct usage.
     */
    public void showCommandOnlyError(String command, String example) {
        showResponse(MessageConstants.ERROR_COMMAND_WITHOUT_ARGS_PREFIX + command
                + MessageConstants.ERROR_COMMAND_WITHOUT_ARGS_SUFFIX + example
                + MessageConstants.ERROR_COMMAND_WITHOUT_ARGS_END);
    }

    /**
     * Displays an error message for invalid task number.
     *
     * @param taskCount The current number of tasks.
     */
    public void showInvalidTaskNumberError(int taskCount) {
        if (taskCount == 0) {
            showResponse(MessageConstants.ERROR_INVALID_TASK_NUMBER_EMPTY);
        } else {
            showResponse(MessageConstants.ERROR_INVALID_TASK_NUMBER_PREFIX + taskCount
                    + MessageConstants.ERROR_INVALID_TASK_NUMBER_SUFFIX);
        }
    }

    /**
     * Displays a corruption message after loading tasks with corrupted data.
     *
     * @param count The number of corrupted lines.
     */
    public void showCorruptionMessage(int count) {
        String unit = count == 1 ? MessageConstants.INFO_CORRUPTION_SUFFIX_SINGULAR
                : MessageConstants.INFO_CORRUPTION_SUFFIX_PLURAL;
        String message = MessageConstants.INFO_CORRUPTION_PREFIX + count + unit + "\n"
                + MessageConstants.INFO_CORRUPTION_RECOVERY;
        showResponse(message);
    }

    /**
     * Displays a grumpy motivational quote.
     * The quote is expected to be wrapped in ANSI color codes.
     *
     * @param quote The motivational quote to display (may contain ANSI color codes).
     */
    public void showCheerMessage(String quote) {
        System.out.println(wrapWithLine(" " + quote));
    }
}
