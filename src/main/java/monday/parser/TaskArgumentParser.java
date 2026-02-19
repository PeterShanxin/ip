package monday.parser;

import monday.command.AddDeadlineCommand;
import monday.command.AddEventCommand;
import monday.command.AddToDoCommand;
import monday.command.Command;
import monday.command.CommandType;
import monday.command.DeleteCommand;
import monday.command.FindCommand;
import monday.command.MarkCommand;
import monday.command.ViewCommand;
import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;
import monday.exception.ParseException;
import monday.task.TaskPrefix;
import monday.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Parses task-specific arguments from user input.
 */
public class TaskArgumentParser {

    private final CommandParser commandParser;
    private final TaskNumberParser taskNumberParser;
    private final DateParser dateParser;

    /**
     * Creates a new TaskArgumentParser.
     *
     * @param commandParser The parser for command-related operations.
     * @param taskNumberParser The parser for task numbers.
     * @param dateParser The parser for date/time arguments.
     */
    public TaskArgumentParser(CommandParser commandParser,
                             TaskNumberParser taskNumberParser,
                             DateParser dateParser) {
        this.commandParser = commandParser;
        this.taskNumberParser = taskNumberParser;
        this.dateParser = dateParser;
    }

    /**
     * Gets the task number parser.
     *
     * @return The task number parser.
     */
    public TaskNumberParser getTaskNumberParser() {
        return taskNumberParser;
    }

    /**
     * Gets the date parser.
     *
     * @return The date parser.
     */
    public DateParser getDateParser() {
        return dateParser;
    }

    /**
     * Parses a mark command.
     *
     * @param userInput The user input.
     * @return A MarkCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseMarkCommand(String userInput) throws ParseException {
        validateCommandHasArguments(userInput, CommandType.MARK, "mark", "mark 1");
        int taskNumber = taskNumberParser.parseTaskNumber(userInput, "mark");
        return new MarkCommand(taskNumber, true);
    }

    /**
     * Parses an unmark command.
     *
     * @param userInput The user input.
     * @return A MarkCommand with markAsDone=false.
     * @throws ParseException If parsing fails.
     */
    public Command parseUnmarkCommand(String userInput) throws ParseException {
        validateCommandHasArguments(userInput, CommandType.UNMARK, "unmark", "unmark 1");
        int taskNumber = taskNumberParser.parseTaskNumber(userInput, "unmark");
        return new MarkCommand(taskNumber, false);
    }

    /**
     * Parses a delete command.
     *
     * @param userInput The user input.
     * @return A DeleteCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseDeleteCommand(String userInput) throws ParseException {
        validateCommandHasArguments(userInput, CommandType.DELETE, "delete", "delete 1");
        int taskNumber = taskNumberParser.parseTaskNumber(userInput, "delete");
        return new DeleteCommand(taskNumber);
    }

    /**
     * Parses a todo command.
     *
     * @param userInput The user input.
     * @return An AddToDoCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseToDoCommand(String userInput) throws ParseException {
        String description = commandParser.extractDescription(userInput, CommandType.TODO.getCommand()).trim();
        if (description.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_TODO_NO_DESCRIPTION);
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
    public Command parseDeadlineCommand(String userInput) throws ParseException {
        String content = commandParser.extractDescription(userInput, CommandType.DEADLINE.getCommand());

        validateContainsPrefix(content, TaskPrefix.BY, MessageConstants.ERROR_DEADLINE_NO_BY_PREFIX);

        String[] parts = parsePrefixField(content, TaskPrefix.BY);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_DEADLINE_NO_DESCRIPTION);
        }
        if (by.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_DEADLINE_NO_TIME);
        }

        LocalDateTime byDateTime = parseDateTimeField(by);
        return new AddDeadlineCommand(description, byDateTime);
    }

    /**
     * Parses an event command.
     *
     * @param userInput The user input.
     * @return An AddEventCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseEventCommand(String userInput) throws ParseException {
        String content = commandParser.extractDescription(userInput, CommandType.EVENT.getCommand());

        validateContainsPrefix(content, TaskPrefix.FROM, MessageConstants.ERROR_EVENT_NO_PREFIXES);
        validateContainsPrefix(content, TaskPrefix.TO, MessageConstants.ERROR_EVENT_NO_PREFIXES);

        String[] fromParts = parsePrefixField(content, TaskPrefix.FROM);
        String description = fromParts[0].trim();
        String[] toParts = parsePrefixField(fromParts[1], TaskPrefix.TO);
        String from = toParts[0].trim();
        String to = toParts.length > 1 ? toParts[1].trim() : "";

        if (description.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_EVENT_NO_DESCRIPTION);
        }
        if (from.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_EVENT_NO_FROM);
        }
        if (to.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_EVENT_NO_TO);
        }

        LocalDateTime fromDateTime = parseDateTimeField(from);
        LocalDateTime toDateTime = parseDateTimeField(to);
        validateEventDateOrder(fromDateTime, toDateTime);

        return new AddEventCommand(description, fromDateTime, toDateTime);
    }

    /**
     * Parses a view command.
     *
     * @param userInput The user input.
     * @return A ViewCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseViewCommand(String userInput) throws ParseException {
        String dateString = commandParser.extractDescription(userInput, CommandType.VIEW.getCommand()).trim();

        if (dateString.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_VIEW_NO_DATE);
        }

        try {
            LocalDateTime targetDate = dateParser.parseViewDate(dateString);
            return new ViewCommand(targetDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(MessageConstants.ERROR_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Parses a find command.
     *
     * @param userInput The user input.
     * @return A FindCommand.
     * @throws ParseException If parsing fails.
     */
    public Command parseFindCommand(String userInput) throws ParseException {
        String keyword = commandParser.extractDescription(userInput, CommandType.FIND.getCommand()).trim();

        if (keyword.isEmpty()) {
            throw new ParseException(MessageConstants.ERROR_FIND_NO_KEYWORD);
        }

        return new FindCommand(keyword);
    }

    // ========== Common Validation Methods ==========

    /**
     * Validates that content contains a specific prefix.
     *
     * @param content The content to check.
     * @param prefix The prefix to look for.
     * @param errorMessage The error message if prefix is not found.
     * @throws ParseException If the prefix is not found.
     */
    private void validateContainsPrefix(String content, TaskPrefix prefix, String errorMessage)
            throws ParseException {
        if (!content.contains(prefix.toString())) {
            throw new ParseException(errorMessage);
        }
    }

    /**
     * Validates that a command has arguments.
     *
     * @param userInput The user input.
     * @param commandType The type of command.
     * @param commandName The name of the command.
     * @param example The example of correct usage.
     * @throws ParseException If the command has no arguments.
     */
    private void validateCommandHasArguments(String userInput, CommandType commandType,
                                         String commandName, String example) throws ParseException {
        if (commandParser.isCommandOnlyInput(userInput, commandType)) {
            throw new ParseException("Ugh, " + commandName + " which task? Try '" + example + "'.");
        }
    }

    // ========== Common Parsing Methods ==========

    /**
     * Parses a prefix field from content.
     *
     * @param content The content to parse.
     * @param prefix The prefix to split on.
     * @return An array with [description, fieldValue].
     * @throws ParseException If parsing fails.
     */
    private String[] parsePrefixField(String content, TaskPrefix prefix) throws ParseException {
        String[] parts = content.split(prefix.toString(), 2);
        if (parts.length < 2) {
            throw new ParseException("Ugh, I can't understand that input. "
                    + "Make sure to use the '" + prefix.toString() + "' prefix correctly.");
        }
        return parts;
    }

    /**
     * Parses a date/time field string into a LocalDateTime.
     *
     * @param dateTimeString The date/time string to parse.
     * @return The parsed LocalDateTime.
     * @throws ParseException If parsing fails.
     */
    private LocalDateTime parseDateTimeField(String dateTimeString) throws ParseException {
        try {
            return DateTimeParser.parseDateTime(dateTimeString);
        } catch (DateTimeParseException e) {
            throw new ParseException(MessageConstants.ERROR_INVALID_DATETIME_FORMAT);
        }
    }

    /**
     * Validates that event 'to' date is after 'from' date.
     *
     * @param from The start date/time.
     * @param to The end date/time.
     * @throws ParseException If 'to' is not after 'from'.
     */
    private void validateEventDateOrder(LocalDateTime from, LocalDateTime to) throws ParseException {
        if (!to.isAfter(from)) {
            throw new ParseException("Ugh, the end time must be after the start time. "
                    + "Fix your event times.");
        }
    }
}
