package monday;

/**
 * Represents a task that needs to be done before a specific date/time.
 * Deadline tasks have a due date/time attached to them.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a new deadline task with the given description and due date/time.
     *
     * @param description The task description.
     * @param by The due date/time.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the type-specific icon for this deadline task.
     *
     * @return "[D]" icon representing a deadline task.
     */
    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    /**
     * Returns the full description including the due date/time.
     *
     * @return The description with due date/time information.
     */
    @Override
    public String getFullDescription() {
        return getDescription() + " (by: " + by + ")";
    }

    /**
     * Returns the due date/time of this deadline.
     *
     * @return The due date/time.
     */
    public String getBy() {
        return by;
    }
}
