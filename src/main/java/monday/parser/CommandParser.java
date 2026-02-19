package monday.parser;

import monday.command.AddDeadlineCommand;
import monday.command.AddEventCommand;
import monday.command.AddToDoCommand;
import monday.command.CheerCommand;
import monday.command.Command;
import monday.command.CommandType;
import monday.command.DeleteCommand;
import monday.command.ExitCommand;
import monday.command.FindCommand;
import monday.command.HelpCommand;
import monday.command.ListCommand;
import monday.command.MarkCommand;
import monday.command.ViewCommand;
import monday.constants.MessageConstants;
import monday.exception.ParseException;

/**
 * Parses user input into Command objects.
 * Handles command type extraction and command creation.
 */
public class CommandParser {

    private final TaskArgumentParser taskArgumentParser;

    /**
     * Creates a new CommandParser.
     *
     * @param taskArgumentParser The parser for task-specific arguments.
     */
    public CommandParser(TaskArgumentParser taskArgumentParser) {
        this.taskArgumentParser = taskArgumentParser;
    }

    /**
     * Parses user input into a Command object.
     *
     * @param userInput The raw user input.
     * @return The parsed Command object.
     * @throws ParseException If the input cannot be parsed.
     */
    public Command parseCommand(String userInput) throws ParseException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_EMPTY_INPUT);
        }

        String commandWord = extractCommandWord(userInput);
        CommandType commandType = CommandType.fromString(commandWord);

        if (commandType == null) {
            throw new ParseException(getUnknownCommandErrorMessage(commandWord));
        }

        switch (commandType) {
        case BYE:
            return new ExitCommand();
        case LIST:
            return new ListCommand();
        case HELP:
            return new HelpCommand();
        case MARK:
            return taskArgumentParser.parseMarkCommand(userInput);
        case UNMARK:
            return taskArgumentParser.parseUnmarkCommand(userInput);
        case DELETE:
            return taskArgumentParser.parseDeleteCommand(userInput);
        case FIND:
            return taskArgumentParser.parseFindCommand(userInput);
        case CHEER:
            return new CheerCommand();
        case TODO:
            return taskArgumentParser.parseToDoCommand(userInput);
        case DEADLINE:
            return taskArgumentParser.parseDeadlineCommand(userInput);
        case EVENT:
            return taskArgumentParser.parseEventCommand(userInput);
        case VIEW:
            return taskArgumentParser.parseViewCommand(userInput);
        default:
            throw new ParseException(getUnknownCommandErrorMessage(commandWord));
        }
    }

    /**
     * Extracts the first word (command word) from user input.
     * The command word is the first sequence of non-whitespace characters.
     *
     * @param userInput The full user input.
     * @return The command word in lowercase, or empty string if input is empty.
     */
    public String extractCommandWord(String userInput) {
        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        int spaceIndex = trimmed.indexOf(' ');
        return spaceIndex == -1 ? trimmed.toLowerCase()
                                : trimmed.substring(0, spaceIndex).toLowerCase();
    }

    /**
     * Checks if the user input contains only the command word with no arguments.
     *
     * @param userInput The full user input.
     * @param commandType The type of command to check for.
     * @return true if input is just the command word, false otherwise.
     */
    public boolean isCommandOnlyInput(String userInput, CommandType commandType) {
        return userInput.trim().equalsIgnoreCase(commandType.getCommand());
    }

    /**
     * Extracts the description part from a command input.
     * Removes the command keyword and returns the rest.
     *
     * @param userInput The full user input.
     * @param command The command keyword to remove (e.g., "todo", "deadline").
     * @return The description part of the input.
     */
    public String extractDescription(String userInput, String command) {
        return userInput.substring(command.length()).trim();
    }

    /**
     * Generates an error message for unknown commands.
     * Provides a grumpy response suggesting the help command.
     *
     * @param commandWord The unknown command word.
     * @return The error message.
     */
    public String getUnknownCommandErrorMessage(String commandWord) {
        return MessageConstants.ERROR_UNKNOWN_COMMAND_PREFIX + commandWord
                + MessageConstants.ERROR_UNKNOWN_COMMAND_SUFFIX;
    }
}
