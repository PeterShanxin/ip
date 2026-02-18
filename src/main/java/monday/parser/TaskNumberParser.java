package monday.parser;

import monday.exception.ParseException;

/**
 * Parses and validates task numbers from user input.
 */
public class TaskNumberParser {

    /**
     * Parses a task number from user input.
     *
     * @param userInput The user input.
     * @param commandName The command name for error messages.
     * @return The parsed task number.
     * @throws ParseException If parsing fails.
     */
    public int parseTaskNumber(String userInput, String commandName) throws ParseException {
        try {
            String[] parts = userInput.trim().split("\\s+", 2);
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException("Ugh, that's not a valid number. Try '" + commandName + " 1' instead.");
        }
    }
}
