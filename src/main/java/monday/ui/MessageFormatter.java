package monday.ui;

/**
 * Formats messages for display.
 */
public class MessageFormatter {

    private static final String LINE = "____________________________________________________________"
            + "______";

    /**
     * Wraps a message with line separators and blank lines.
     *
     * @param message The message to wrap.
     * @return The wrapped message as a string.
     */
    private String wrapWithLine(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\n");
        sb.append("\n");  // blank line after opening LINE
        sb.append(message).append("\n");
        sb.append(LINE).append("\n");
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
        showResponse("Ugh, you didn't actually say anything. Try again.");
    }

    /**
     * Displays an error message for a command without arguments.
     *
     * @param command The command that needs arguments.
     * @param example The example of correct usage.
     */
    public void showCommandOnlyError(String command, String example) {
        showResponse("Ugh, " + command + " needs more info. Try '" + example + "'.");
    }

    /**
     * Displays an error message for invalid task number.
     *
     * @param taskCount The current number of tasks.
     */
    public void showInvalidTaskNumberError(int taskCount) {
        if (taskCount == 0) {
            showResponse("Skeptical. You haven't told me to do anything yet.");
        } else {
            showResponse("Ugh, that task doesn't exist. Pick between 1 and " + taskCount + ".");
        }
    }

    /**
     * Displays a corruption message after loading tasks with corrupted data.
     *
     * @param count The number of corrupted lines.
     */
    public void showCorruptionMessage(int count) {
        String unit = count == 1 ? " corrupted line." : " corrupted lines.";
        String message = "Ugh. I skipped " + count + unit + "\nCheck monday.txt.corrupted for recovery.";
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
