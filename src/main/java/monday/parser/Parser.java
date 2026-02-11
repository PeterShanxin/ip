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
import monday.exception.ParseException;
import monday.task.TaskPrefix;
import monday.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into Command objects.
 * Extracts command type and arguments to create appropriate commands.
 */
public class Parser {

    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy");

    /**
     * Parses user input into a Command object.
     *
     * @param userInput The raw user input.
     * @return The parsed Command object.
     * @throws ParseException If the input cannot be parsed.
     */
    public Command parseCommand(String userInput) throws ParseException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new ParseException("Ugh, you didn't actually say anything. Try again.");
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
            return parseMarkCommand(userInput);
        case UNMARK:
            return parseUnmarkCommand(userInput);
        case DELETE:
            return parseDeleteCommand(userInput);
        case FIND:
            return parseFindCommand(userInput);
        case CHEER:
            return new CheerCommand();
        case TODO:
            return parseToDoCommand(userInput);
        case DEADLINE:
            return parseDeadlineCommand(userInput);
        case EVENT:
            return parseEventCommand(userInput);
        case VIEW:
            return parseViewCommand(userInput);
        default:
            throw new ParseException(getUnknownCommandErrorMessage(commandWord));
        }
    }

    /**
     * Parses a mark command.
     *
     * @param userInput The user input.
     * @return A MarkCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseMarkCommand(String userInput) throws ParseException {
        if (isCommandOnlyInput(userInput, CommandType.MARK)) {
            throw new ParseException("Ugh, mark which task? Try 'mark 1'.");
        }
        int taskNumber = parseTaskNumber(userInput, "mark");
        return new MarkCommand(taskNumber, true);
    }

    /**
     * Parses an unmark command.
     *
     * @param userInput The user input.
     * @return A MarkCommand with markAsDone=false.
     * @throws ParseException If parsing fails.
     */
    private Command parseUnmarkCommand(String userInput) throws ParseException {
        if (isCommandOnlyInput(userInput, CommandType.UNMARK)) {
            throw new ParseException("Ugh, unmark which task? Try 'unmark 1'.");
        }
        int taskNumber = parseTaskNumber(userInput, "unmark");
        return new MarkCommand(taskNumber, false);
    }

    /**
     * Parses a delete command.
     *
     * @param userInput The user input.
     * @return A DeleteCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseDeleteCommand(String userInput) throws ParseException {
        if (isCommandOnlyInput(userInput, CommandType.DELETE)) {
            throw new ParseException("Ugh, delete which task? Try 'delete 1'.");
        }
        int taskNumber = parseTaskNumber(userInput, "delete");
        return new DeleteCommand(taskNumber);
    }

    /**
     * Parses a todo command.
     *
     * @param userInput The user input.
     * @return An AddToDoCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseToDoCommand(String userInput) throws ParseException {
        String description = extractDescription(userInput, CommandType.TODO.getCommand()).trim();
        if (description.isEmpty()) {
            throw new ParseException("Ugh, a todo needs a description. Try 'todo borrow book'.");
        }
        return new AddToDoCommand(description);
    }

    /**
     * Parses a deadline command.
     *
     * @param userInput The user input.
     * @return An AddDeadlineCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseDeadlineCommand(String userInput) throws ParseException {
        String content = extractDescription(userInput, CommandType.DEADLINE.getCommand());

        if (!content.contains(TaskPrefix.BY.toString())) {
            throw new ParseException("Ugh, deadlines need a '/by' time. "
                    + "Try 'deadline return book /by 2019-12-02 1800'.");
        }

        String[] parts = content.split(TaskPrefix.BY.toString(), 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new ParseException("Ugh, what's the deadline for? Try 'deadline return book /by 2019-12-02 1800'.");
        }
        if (by.isEmpty()) {
            throw new ParseException("Ugh, when is it due? Try 'deadline return book /by 2019-12-02 1800'.");
        }

        try {
            LocalDateTime byDateTime = DateTimeParser.parseDateTime(by);
            return new AddDeadlineCommand(description, byDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException("Ugh, I can't understand that deadline. "
                    + "Try 'deadline return book /by 2019-12-02 1800'.");
        }
    }

    /**
     * Parses an event command.
     *
     * @param userInput The user input.
     * @return An AddEventCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseEventCommand(String userInput) throws ParseException {
        String content = extractDescription(userInput, CommandType.EVENT.getCommand());

        if (!content.contains(TaskPrefix.FROM.toString()) || !content.contains(TaskPrefix.TO.toString())) {
            throw new ParseException("Ugh, events need '/from' and '/to' times. "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }

        String[] fromParts = content.split(TaskPrefix.FROM.toString(), 2);
        String description = fromParts[0].trim();

        if (fromParts.length < 2) {
            throw new ParseException("Ugh, I can't understand that event. "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }

        String[] toParts = fromParts[1].split(TaskPrefix.TO.toString(), 2);
        String from = toParts[0].trim();
        String to = toParts.length > 1 ? toParts[1].trim() : "";

        if (description.isEmpty()) {
            throw new ParseException("Ugh, what's the event? "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }
        if (from.isEmpty()) {
            throw new ParseException("Ugh, when does it start? "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }
        if (to.isEmpty()) {
            throw new ParseException("Ugh, when does it end? "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }

        try {
            LocalDateTime fromDateTime = DateTimeParser.parseDateTime(from);
            LocalDateTime toDateTime = DateTimeParser.parseDateTime(to);
            return new AddEventCommand(description, fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException("Ugh, I can't understand that event. "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }
    }

    /**
     * Parses a view command.
     *
     * @param userInput The user input.
     * @return A ViewCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseViewCommand(String userInput) throws ParseException {
        String dateString = extractDescription(userInput, CommandType.VIEW.getCommand()).trim();

        if (dateString.isEmpty()) {
            throw new ParseException("Ugh, what date do you want to view? Try 'view 2019-12-25'.");
        }

        try {
            LocalDateTime targetDate = parseViewDate(dateString);
            return new ViewCommand(targetDate);
        } catch (DateTimeParseException e) {
            throw new ParseException("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd' or 'd/M/yyyy' format.");
        }
    }

    /**
     * Parses a find command.
     *
     * @param userInput The user input.
     * @return A FindCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseFindCommand(String userInput) throws ParseException {
        String keyword = extractDescription(userInput, CommandType.FIND.getCommand()).trim();

        if (keyword.isEmpty()) {
            throw new ParseException("Ugh, find what? Try 'find book'.");
        }

        return new FindCommand(keyword);
    }

    /**
     * Parses a cheer command.
     * Cheer command takes no arguments.
     *
     * @param userInput The user input.
     * @return A CheerCommand.
     * @throws ParseException If parsing fails.
     */
    private Command parseCheerCommand(String userInput) throws ParseException {
        if (isCommandOnlyInput(userInput, CommandType.CHEER)) {
            throw new ParseException("Ugh, cheer command takes no arguments. Just type 'cheer'.");
        }

        return new CheerCommand();
    }

    /**
     * Parses a task number from user input.
     *
     * @param userInput The user input.
     * @param commandName The command name for error messages.
     * @return The parsed task number.
     * @throws ParseException If parsing fails.
     */
    private int parseTaskNumber(String userInput, String commandName) throws ParseException {
        try {
            String[] parts = userInput.trim().split("\\s+", 2);
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException("Ugh, that's not a valid number. Try '" + commandName + " 1' instead.");
        }
    }

    /**
     * Parses a date string for the view command.
     * Tries multiple formats: yyyy-MM-dd, then d/M/yyyy.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDateTime (time set to midnight).
     * @throws DateTimeParseException If the string cannot be parsed with any format.
     */
    private LocalDateTime parseViewDate(String dateString) throws DateTimeParseException {
        try {
            LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_1);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_2);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException(
                        "Ugh, I can't understand that date. Try 'yyyy-MM-dd' or 'd/M/yyyy' format.",
                        dateString, 0);
            }
        }
    }

    /**
     * Extracts the description part from a command input.
     * Removes the command keyword and returns the rest.
     *
     * @param userInput The full user input.
     * @param command The command keyword to remove (e.g., "todo", "deadline").
     * @return The description part of the input.
     */
    private String extractDescription(String userInput, String command) {
        return userInput.substring(command.length()).trim();
    }

    /**
     * Extracts the first word (command word) from user input.
     * The command word is the first sequence of non-whitespace characters.
     *
     * @param userInput The full user input.
     * @return The command word in lowercase, or empty string if input is empty.
     */
    private String extractCommandWord(String userInput) {
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
    private boolean isCommandOnlyInput(String userInput, CommandType commandType) {
        return userInput.trim().equalsIgnoreCase(commandType.getCommand());
    }

    /**
     * Generates an error message for unknown commands.
     * Provides a grumpy response suggesting the help command.
     *
     * @param commandWord The unknown command word.
     * @return The error message.
     */
    private String getUnknownCommandErrorMessage(String commandWord) {
        return "Ugh, I don't understand '" + commandWord + "'. "
             + "Type 'help' if you're confused. It's probably hopeless though.";
    }
}
