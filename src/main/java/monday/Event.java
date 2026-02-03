package monday;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that starts at a specific date/time and ends at a specific date/time.
 * Event tasks have both start and end date/time attached to them.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMATTER_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        return getDescription() + " (from: " + from.format(OUTPUT_FORMATTER)
                + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }

    /**
     * Returns the start date/time of this event as a formatted string.
     *
     * @return The start date/time formatted for display.
     */
    public String getFrom() {
        return from.format(OUTPUT_FORMATTER);
    }

    /**
     * Returns the end date/time of this event as a formatted string.
     *
     * @return The end date/time formatted for display.
     */
    public String getTo() {
        return to.format(OUTPUT_FORMATTER);
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
        return from.format(STORAGE_FORMATTER);
    }

    /**
     * Returns the end date/time formatted for storage.
     *
     * @return The end date/time formatted for file storage.
     */
    public String getToForStorage() {
        return to.format(STORAGE_FORMATTER);
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
