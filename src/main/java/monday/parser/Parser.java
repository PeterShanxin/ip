package monday.parser;

import monday.command.Command;
import monday.exception.ParseException;

/**
 * Parses user input into Command objects.
 * This class acts as a facade that delegates to specialized parsers.
 */
public class Parser {

    private final CommandParser commandParser;
    private final TaskArgumentParser taskArgumentParser;

    /**
     * Creates a new Parser with default specialized parsers.
     */
    public Parser() {
        TaskNumberParser taskNumberParser = new TaskNumberParser();
        DateParser dateParser = new DateParser();
        // Create commandParser with null first, will be replaced
        CommandParser tempCommandParser = new CommandParser(null);
        this.taskArgumentParser = new TaskArgumentParser(tempCommandParser, taskNumberParser, dateParser);
        // Create the actual commandParser with the taskArgumentParser
        this.commandParser = new CommandParser(taskArgumentParser);
    }

    /**
     * Parses user input into a Command object.
     *
     * @param userInput The raw user input.
     * @return The parsed Command object.
     * @throws ParseException If the input cannot be parsed.
     */
    public Command parseCommand(String userInput) throws ParseException {
        return commandParser.parseCommand(userInput);
    }

    /**
     * Extracts the first word (command word) from user input.
     * The command word is the first sequence of non-whitespace characters.
     *
     * @param userInput The full user input.
     * @return The command word in lowercase, or empty string if input is empty.
     */
    public String extractCommandWord(String userInput) {
        return commandParser.extractCommandWord(userInput);
    }

    /**
     * Checks if the user input contains only the command word with no arguments.
     *
     * @param userInput The full user input.
     * @param commandType The type of command to check for.
     * @return true if input is just the command word, false otherwise.
     */
    public boolean isCommandOnlyInput(String userInput, monday.command.CommandType commandType) {
        return commandParser.isCommandOnlyInput(userInput, commandType);
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
        return commandParser.extractDescription(userInput, command);
    }

    /**
     * Parses a task number from user input.
     *
     * @param userInput The user input.
     * @param commandName The command name for error messages.
     * @return The parsed task number.
     * @throws ParseException If parsing fails.
     */
    public int parseTaskNumber(String userInput, String commandName) throws ParseException {
        return taskArgumentParser.getTaskNumberParser().parseTaskNumber(userInput, commandName);
    }

    /**
     * Parses a date string for the view command.
     * Tries multiple formats: yyyy-MM-dd, then d/M/yyyy.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDateTime (time set to midnight).
     * @throws java.time.format.DateTimeParseException If the string cannot be parsed with any format.
     */
    public java.time.LocalDateTime parseViewDate(String dateString) throws java.time.format.DateTimeParseException {
        return taskArgumentParser.getDateParser().parseViewDate(dateString);
    }
}
