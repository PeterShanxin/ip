package monday.ui;

/**
 * Represents a response from MONDAY, bundling text with error state.
 *
 * @param text The response text to display.
 * @param isError Whether this response represents an error condition.
 */
public record GuiResponse(String text, boolean isError) {}
