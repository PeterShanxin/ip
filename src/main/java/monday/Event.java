package monday;

/**
 * Represents a task that starts at a specific date/time and ends at a specific date/time.
 * Event tasks have both start and end date/time attached to them.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates a new event task with the given description and time range.
     *
     * @param description The task description.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the type-specific icon for this event task.
     *
     * @return "[E]" icon representing an event task.
     */
    @Override
    public String getTypeIcon() {
        return "[E]";
    }

    /**
     * Returns the full description including the time range.
     *
     * @return The description with start and end time information.
     */
    @Override
    public String getFullDescription() {
        return getDescription() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return The start date/time.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return The end date/time.
     */
    public String getTo() {
        return to;
    }
}
