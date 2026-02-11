package monday.command;

/**
 * Represents a command that can be issued to Monday.
 * Commands are case-insensitive and may have aliases.
 */
public enum CommandType {
    /** Exit command - terminates the application */
    BYE("bye", "exit"),

    /** List command - displays all tasks */
    LIST("list"),

    /** Mark command - marks task as done */
    MARK("mark"),

    /** Unmark command - marks task as not done */
    UNMARK("unmark"),

    /** Todo command - creates a todo task */
    TODO("todo"),

    /** Deadline command - creates a deadline task */
    DEADLINE("deadline"),

    /** Event command - creates an event task */
    EVENT("event"),

    /** Delete command - removes a task */
    DELETE("delete"),

    /** View command - displays tasks for a specific date */
    VIEW("view"),

    /** Find command - searches tasks by keyword */
    FIND("find"),

    /** Help command - displays usage information */
    HELP("help"),

    /** Cheer command - displays a grumpy motivational quote */
    CHEER("cheer");

    private final String primaryCommand;
    private final String[] aliases;

    /**
     * Creates a CommandType with the primary command word.
     *
     * @param primaryCommand The primary command word.
     */
    CommandType(String primaryCommand) {
        this.primaryCommand = primaryCommand;
        this.aliases = new String[0];
    }

    /**
     * Creates a CommandType with the primary command word and aliases.
     *
     * @param primaryCommand The primary command word.
     * @param aliases Alternative command words that are accepted.
     */
    CommandType(String primaryCommand, String... aliases) {
        this.primaryCommand = primaryCommand;
        this.aliases = aliases;
    }

    /**
     * Returns the primary command word for this command type.
     *
     * @return The primary command word.
     */
    public String getCommand() {
        return primaryCommand;
    }

    /**
     * Checks if the given input matches this command type.
     *
     * @param input The user input to check.
     * @return true if the input matches this command, false otherwise.
     */
    public boolean matches(String input) {
        if (primaryCommand.equalsIgnoreCase(input)) {
            return true;
        }
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the CommandType that matches the given command word.
     *
     * @param commandWord The command word to find.
     * @return The matching CommandType, or null if no match is found.
     */
    public static CommandType fromString(String commandWord) {
        for (CommandType type : values()) {
            if (type.matches(commandWord)) {
                return type;
            }
        }
        return null;
    }
}
