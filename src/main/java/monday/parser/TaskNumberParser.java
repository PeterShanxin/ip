package monday.parser;

import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;
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
            String[] parts = userInput.trim().split(ValidationConstants.WHITESPACE_REGEX, 2);
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException(MessageConstants.ERROR_INVALID_NUMBER_PREFIX + commandName
                    + MessageConstants.ERROR_INVALID_NUMBER_SUFFIX);
        }
    }
}
