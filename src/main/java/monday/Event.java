package monday;

import java.time.LocalDateTime;

/**
 * Represents a task that starts at a specific date/time and ends at a specific date/time.
 * Event tasks have both start and end date/time attached to them.
 */
public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates a new event task with the given description and time range.
     *
     * @param description The task description.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
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
        return TaskType.EVENT.getIcon();
    }

    /**
     * Returns the full description including the time range.
     *
     * @return The description with start and end time information.
     */
    @Override
    public String getFullDescription() {
        return getDescription() + " (from: " + from.format(DateTimeParser.OUTPUT_FORMATTER)
                + " to: " + to.format(DateTimeParser.OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns the start date/time of this event as a formatted string.
     *
     * @return The start date/time formatted for display.
     */
    public String getFrom() {
        return from.format(DateTimeParser.OUTPUT_FORMATTER);
    }

    /**
     * Returns the end date/time of this event as a formatted string.
     *
     * @return The end date/time formatted for display.
     */
    public String getTo() {
        return to.format(DateTimeParser.OUTPUT_FORMATTER);
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return The start date/time.
     */
    public LocalDateTime getFromDateTime() {
        return from;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return The end date/time.
     */
    public LocalDateTime getToDateTime() {
        return to;
    }

    /**
     * Returns the start date/time formatted for storage.
     *
     * @return The start date/time formatted for file storage.
     */
    public String getFromForStorage() {
        return from.format(DateTimeParser.STORAGE_FORMATTER);
    }

    /**
     * Returns the end date/time formatted for storage.
     *
     * @return The end date/time formatted for file storage.
     */
    public String getToForStorage() {
        return to.format(DateTimeParser.STORAGE_FORMATTER);
    }

    /**
     * Checks if this event occurs on the specified date.
     * An event occurs on a date if its start date matches the given date.
     *
     * @param date The date to compare with.
     * @return true if this event is on the specified date, false otherwise.
     */
    public boolean isOnDate(LocalDateTime date) {
        return from.toLocalDate().equals(date.toLocalDate());
    }
}
