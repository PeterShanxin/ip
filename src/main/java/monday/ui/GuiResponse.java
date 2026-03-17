package monday.ui;

/**
 * Represents a response from MONDAY, bundling text with error state.
 *
 * @param text The response text to display.
 * @param isError Whether this response represents an error condition.
 * @param shouldExit Whether the GUI should exit after displaying this response.
 */
public record GuiResponse(String text, boolean isError, boolean shouldExit) {}
