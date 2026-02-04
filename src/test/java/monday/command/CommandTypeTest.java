package monday.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for CommandType.
 * Tests command matching with aliases and case insensitivity.
 */
public class CommandTypeTest {

    @Test
    public void testMatches_primaryCommand_caseSensitive() {
        assertTrue(CommandType.BYE.matches("bye"));
        assertTrue(CommandType.LIST.matches("list"));
        assertTrue(CommandType.MARK.matches("mark"));
    }

    @Test
    public void testMatches_primaryCommand_caseInsensitive() {
        assertTrue(CommandType.BYE.matches("BYE"));
        assertTrue(CommandType.BYE.matches("Bye"));
        assertTrue(CommandType.LIST.matches("LIST"));
        assertTrue(CommandType.LIST.matches("List"));
    }

    @Test
    public void testMatches_alias_caseInsensitive() {
        assertTrue(CommandType.BYE.matches("exit"));
        assertTrue(CommandType.BYE.matches("EXIT"));
        assertTrue(CommandType.BYE.matches("Exit"));
    }

    @Test
    public void testMatches_noMatch() {
        assertFalse(CommandType.BYE.matches("invalid"));
        assertFalse(CommandType.LIST.matches("listx"));
        assertFalse(CommandType.TODO.matches("todo123"));
    }

    @Test
    public void testMatches_nullInput() {
        assertFalse(CommandType.BYE.matches(null));
        assertFalse(CommandType.LIST.matches(""));
    }

    @Test
    public void testFromString_validCommand() {
        assertEquals(CommandType.BYE, CommandType.fromString("bye"));
        assertEquals(CommandType.LIST, CommandType.fromString("list"));
        assertEquals(CommandType.MARK, CommandType.fromString("mark"));
        assertEquals(CommandType.UNMARK, CommandType.fromString("unmark"));
        assertEquals(CommandType.TODO, CommandType.fromString("todo"));
        assertEquals(CommandType.DEADLINE, CommandType.fromString("deadline"));
        assertEquals(CommandType.EVENT, CommandType.fromString("event"));
        assertEquals(CommandType.DELETE, CommandType.fromString("delete"));
        assertEquals(CommandType.VIEW, CommandType.fromString("view"));
        assertEquals(CommandType.HELP, CommandType.fromString("help"));
    }

    @Test
    public void testFromString_validAlias() {
        assertEquals(CommandType.BYE, CommandType.fromString("exit"));
    }

    @Test
    public void testFromString_invalidCommand() {
        assertNull(CommandType.fromString("invalid"));
        assertNull(CommandType.fromString("xyz"));
        assertNull(CommandType.fromString("123"));
    }

    @Test
    public void testFromString_caseInsensitive() {
        assertEquals(CommandType.BYE, CommandType.fromString("BYE"));
        assertEquals(CommandType.BYE, CommandType.fromString("ByE"));
        assertEquals(CommandType.LIST, CommandType.fromString("LiSt"));
        assertEquals(CommandType.HELP, CommandType.fromString("HELP"));
        assertEquals(CommandType.TODO, CommandType.fromString("ToDo"));
    }

    @Test
    public void testGetCommand() {
        assertEquals("bye", CommandType.BYE.getCommand());
        assertEquals("list", CommandType.LIST.getCommand());
        assertEquals("mark", CommandType.MARK.getCommand());
        assertEquals("unmark", CommandType.UNMARK.getCommand());
        assertEquals("todo", CommandType.TODO.getCommand());
        assertEquals("deadline", CommandType.DEADLINE.getCommand());
        assertEquals("event", CommandType.EVENT.getCommand());
        assertEquals("delete", CommandType.DELETE.getCommand());
        assertEquals("view", CommandType.VIEW.getCommand());
        assertEquals("help", CommandType.HELP.getCommand());
    }

    @Test
    public void testAllCommandTypes_havePrimaryCommand() {
        for (CommandType type : CommandType.values()) {
            assertNotNull(type.getCommand());
            assertFalse(type.getCommand().isEmpty());
        }
    }

    @Test
    public void testAllCommandTypes_uniquePrimaryCommands() {
        String[] commands = new String[CommandType.values().length];
        int i = 0;
        for (CommandType type : CommandType.values()) {
            commands[i++] = type.getCommand();
        }
        // All primary commands should be unique
        for (int j = 0; j < commands.length; j++) {
            for (int k = j + 1; k < commands.length; k++) {
                assertFalse(commands[j].equals(commands[k]),
                        "Duplicate command found: " + commands[j]);
            }
        }
    }

    @Test
    public void testMatches_allCommands() {
        // Test that all enum values can be matched
        assertTrue(CommandType.BYE.matches("bye"));
        assertTrue(CommandType.LIST.matches("list"));
        assertTrue(CommandType.MARK.matches("mark"));
        assertTrue(CommandType.UNMARK.matches("unmark"));
        assertTrue(CommandType.TODO.matches("todo"));
        assertTrue(CommandType.DEADLINE.matches("deadline"));
        assertTrue(CommandType.EVENT.matches("event"));
        assertTrue(CommandType.DELETE.matches("delete"));
        assertTrue(CommandType.VIEW.matches("view"));
        assertTrue(CommandType.HELP.matches("help"));
    }

    @Test
    public void testFromString_allCommands() {
        // Test that all enum values can be found
        assertEquals(CommandType.BYE, CommandType.fromString("bye"));
        assertEquals(CommandType.LIST, CommandType.fromString("list"));
        assertEquals(CommandType.MARK, CommandType.fromString("mark"));
        assertEquals(CommandType.UNMARK, CommandType.fromString("unmark"));
        assertEquals(CommandType.TODO, CommandType.fromString("todo"));
        assertEquals(CommandType.DEADLINE, CommandType.fromString("deadline"));
        assertEquals(CommandType.EVENT, CommandType.fromString("event"));
        assertEquals(CommandType.DELETE, CommandType.fromString("delete"));
        assertEquals(CommandType.VIEW, CommandType.fromString("view"));
        assertEquals(CommandType.HELP, CommandType.fromString("help"));
    }
}
