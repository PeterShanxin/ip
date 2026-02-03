package monday;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that needs to be done before a specific date/time.
 * Deadline tasks have a due date/time attached to them.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMATTER_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        return getDescription() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns the due date/time of this deadline as a formatted string.
     *
     * @return The due date/time formatted for display.
     */
    public String getBy() {
        return by.format(OUTPUT_FORMATTER);
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
        return by.format(STORAGE_FORMATTER);
    }

    /**
     * Parses a date/time string into a LocalDateTime.
     * Tries multiple formats: yyyy-MM-dd HHmm, then d/M/yyyy HHmm.
     *
     * @param dateTimeString The date/time string to parse.
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the string cannot be parsed with any format.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER_1);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER_2);
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException(
                        "Ugh, I can't understand that date. Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm'.",
                        dateTimeString, 0);
            }
        }
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
