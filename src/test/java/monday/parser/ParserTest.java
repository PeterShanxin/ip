package monday.parser;

import monday.command.AddDeadlineCommand;
import monday.command.AddEventCommand;
import monday.command.AddToDoCommand;
import monday.command.Command;
import monday.command.DeleteCommand;
import monday.command.ExitCommand;
import monday.command.HelpCommand;
import monday.command.ListCommand;
import monday.command.MarkCommand;
import monday.command.ViewCommand;
import monday.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Parser.
 * Tests command parsing with various input formats and error conditions.
 */
public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    public void testParseCommand_emptyInput() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand("");
        });
    }

    @Test
    public void testParseCommand_whitespaceOnly() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand("   ");
        });
    }

    @Test
    public void testParseCommand_nullInput() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand(null);
        });
    }

    @Test
    public void testParseCommand_bye() throws ParseException {
        Command command = parser.parseCommand("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void testParseCommand_bye_withAlias() throws ParseException {
        Command command = parser.parseCommand("exit");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void testParseCommand_bye_caseInsensitive() throws ParseException {
        Command command = parser.parseCommand("BYE");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void testParseCommand_list() throws ParseException {
        Command command = parser.parseCommand("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testParseCommand_list_caseInsensitive() throws ParseException {
        Command command = parser.parseCommand("LIST");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testParseCommand_help() throws ParseException {
        Command command = parser.parseCommand("help");
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void testParseCommand_mark_validNumber() throws ParseException {
        Command command = parser.parseCommand("mark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseCommand_mark_noNumber() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("mark");
        });
        assertTrue(exception.getMessage().contains("mark which task"));
    }

    @Test
    public void testParseCommand_mark_invalidNumber() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand("mark abc");
        });
    }

    @Test
    public void testParseCommand_unmark_validNumber() throws ParseException {
        Command command = parser.parseCommand("unmark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseCommand_unmark_noNumber() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("unmark");
        });
        assertTrue(exception.getMessage().contains("unmark which task"));
    }

    @Test
    public void testParseCommand_unmark_invalidNumber() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand("unmark abc");
        });
    }

    @Test
    public void testParseCommand_delete_validNumber() throws ParseException {
        Command command = parser.parseCommand("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testParseCommand_delete_noNumber() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("delete");
        });
        assertTrue(exception.getMessage().contains("delete which task"));
    }

    @Test
    public void testParseCommand_delete_invalidNumber() {
        assertThrows(ParseException.class, () -> {
            parser.parseCommand("delete abc");
        });
    }

    @Test
    public void testParseCommand_todo_validDescription() throws ParseException {
        Command command = parser.parseCommand("todo borrow book");
        assertTrue(command instanceof AddToDoCommand);
    }

    @Test
    public void testParseCommand_todo_emptyDescription() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("todo");
        });
        assertTrue(exception.getMessage().contains("needs a description"));
    }

    @Test
    public void testParseCommand_todo_whitespaceOnly() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("todo    ");
        });
        assertTrue(exception.getMessage().contains("needs a description"));
    }

    @Test
    public void testParseCommand_deadline_validInput() throws ParseException {
        Command command = parser.parseCommand("deadline return book /by 2019-12-02 1800");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    public void testParseCommand_deadline_validInput_format2() throws ParseException {
        Command command = parser.parseCommand("deadline return book /by 2/12/2019 1800");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    public void testParseCommand_deadline_missingBy() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("deadline return book");
        });
        assertTrue(exception.getMessage().contains("/by"));
    }

    @Test
    public void testParseCommand_deadline_emptyDescription() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("deadline /by 2019-12-02 1800");
        });
        assertTrue(exception.getMessage().contains("what's the deadline for"));
    }

    @Test
    public void testParseCommand_deadline_emptyBy() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("deadline return book /by");
        });
        assertTrue(exception.getMessage().contains("when is it due"));
    }

    @Test
    public void testParseCommand_deadline_invalidDate() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("deadline return book /by invalid");
        });
        assertTrue(exception.getMessage().contains("can't understand that date"));
    }

    @Test
    public void testParseCommand_event_validInput() throws ParseException {
        Command command = parser.parseCommand("event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void testParseCommand_event_validInput_format2() throws ParseException {
        Command command = parser.parseCommand("event project meeting /from 25/12/2019 1400 /to 25/12/2019 1800");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void testParseCommand_event_missingFrom() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event project meeting /to 2019-12-25 1800");
        });
        assertTrue(exception.getMessage().contains("/from"));
    }

    @Test
    public void testParseCommand_event_missingTo() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event project meeting /from 2019-12-25 1400");
        });
        assertTrue(exception.getMessage().contains("/to"));
    }

    @Test
    public void testParseCommand_event_emptyDescription() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event /from 2019-12-25 1400 /to 2019-12-25 1800");
        });
        assertTrue(exception.getMessage().contains("what's the event"));
    }

    @Test
    public void testParseCommand_event_emptyFrom() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event meeting /from /to 2019-12-25 1800");
        });
        assertTrue(exception.getMessage().contains("when does it start"));
    }

    @Test
    public void testParseCommand_event_emptyTo() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event meeting /from 2019-12-25 1400 /to");
        });
        assertTrue(exception.getMessage().contains("when does it end"));
    }

    @Test
    public void testParseCommand_event_invalidDate() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("event meeting /from invalid /to 2019-12-25 1800");
        });
        assertTrue(exception.getMessage().contains("can't understand that date"));
    }

    @Test
    public void testParseCommand_view_validDate() throws ParseException {
        Command command = parser.parseCommand("view 2019-12-25");
        assertTrue(command instanceof ViewCommand);
    }

    @Test
    public void testParseCommand_view_validDate_format2() throws ParseException {
        Command command = parser.parseCommand("view 25/12/2019");
        assertTrue(command instanceof ViewCommand);
    }

    @Test
    public void testParseCommand_view_emptyDate() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("view");
        });
        assertTrue(exception.getMessage().contains("what date"));
    }

    @Test
    public void testParseCommand_view_invalidDate() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("view invalid");
        });
        assertTrue(exception.getMessage().contains("can't understand that date"));
    }

    @Test
    public void testParseCommand_view_invalidMonth() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("view 2019-13-25");
        });
        assertTrue(exception.getMessage().contains("can't understand that date"));
    }

    @Test
    public void testParseCommand_unknownCommand() {
        ParseException exception = assertThrows(ParseException.class, () -> {
            parser.parseCommand("invalid command here");
        });
        assertTrue(exception.getMessage().contains("don't understand"));
    }

    @Test
    public void testParseCommand_caseInsensitivity() throws ParseException {
        Command command1 = parser.parseCommand("TODO read book");
        Command command2 = parser.parseCommand("Todo read book");
        Command command3 = parser.parseCommand("todo read book");
        assertTrue(command1 instanceof AddToDoCommand);
        assertTrue(command2 instanceof AddToDoCommand);
        assertTrue(command3 instanceof AddToDoCommand);
    }

    @Test
    public void testParseCommand_extraWhitespace() throws ParseException {
        Command command = parser.parseCommand("  todo   read book  ");
        assertTrue(command instanceof AddToDoCommand);
    }
}
