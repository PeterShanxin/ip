package monday;

/**
 * Represents the parameter prefix for task date/time information.
 * These prefixes are used to separate task description from temporal details.
 */
public enum TaskPrefix {
    /** Prefix for deadline due date/time */
    BY("/by"),

    /** Prefix for event start date/time */
    FROM("/from"),

    /** Prefix for event end date/time */
    TO("/to");

    private final String prefix;

    /**
     * Creates a TaskPrefix with the specified prefix string.
     *
     * @param prefix The prefix string (e.g., "/by", "/from", "/to").
     */
    TaskPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the prefix string for this task prefix.
     *
     * @return The prefix string.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the prefix string.
     *
     * @return The prefix string.
     */
    @Override
    public String toString() {
        return prefix;
    }
}
