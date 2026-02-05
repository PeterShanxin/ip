package monday.task;

import monday.util.DateTimeParser;

import java.time.LocalDateTime;

/**
 * Represents a task that needs to be done before a specific date/time.
 * Deadline tasks have a due date/time attached to them.
 */
public class Deadline extends Task {

    private final LocalDateTime by;

    /**
     * Creates a new deadline task with the given description and due date/time.
     *
     * @param description The task description.
     * @param by The due date/time.
     */
    public Deadline(String description, LocalDateTime by) {
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
        return TaskType.DEADLINE.getIcon();
    }

    /**
     * Returns the full description including the due date/time.
     *
     * @return The description with due date/time information.
     */
    @Override
    public String getFullDescription() {
        return getDescription() + " (by: " + by.format(DateTimeParser.OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns the due date/time of this deadline as a formatted string.
     *
     * @return The due date/time formatted for display.
     */
    public String getBy() {
        return by.format(DateTimeParser.OUTPUT_FORMATTER);
    }

    /**
     * Returns the due date/time of this deadline.
     *
     * @return The due date/time.
     */
    public LocalDateTime getByDateTime() {
        return by;
    }

    /**
     * Returns the due date/time formatted for storage.
     *
     * @return The due date/time formatted for file storage.
     */
    public String getByForStorage() {
        return by.format(DateTimeParser.STORAGE_FORMATTER);
    }

    /**
     * Checks if this deadline occurs on the specified date.
     * Compares year, month, and day components only.
     *
     * @param date The date to compare with.
     * @return true if this deadline is on the specified date, false otherwise.
     */
    public boolean isOnDate(LocalDateTime date) {
        return by.toLocalDate().equals(date.toLocalDate());
    }
}
