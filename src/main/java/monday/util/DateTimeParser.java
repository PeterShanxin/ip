package monday.util;

import monday.constants.ValidationConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting date/time strings.
 * Provides common date/time parsing functionality used across the application.
 */
public class DateTimeParser {

    /** Input format: year-month-day hour-minute (e.g., 2019-12-02 1800) */
    public static final DateTimeFormatter INPUT_FORMATTER_1 = ValidationConstants.INPUT_FORMATTER_1;

    /** Input format: day/month/year hour-minute (e.g., 2/12/2019 1800) */
    public static final DateTimeFormatter INPUT_FORMATTER_2 = ValidationConstants.INPUT_FORMATTER_2;

    /** Output format: month day year hour-minute (e.g., Dec 02 2019 1800) */
    public static final DateTimeFormatter OUTPUT_FORMATTER = ValidationConstants.OUTPUT_FORMATTER;

    /** Storage format: year-month-day hour:minute (e.g., 2019-12-02 18:00) */
    public static final DateTimeFormatter STORAGE_FORMATTER = ValidationConstants.STORAGE_FORMATTER;

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
                        dateTimeString,
                        0);
            }
        }
    }

    private DateTimeParser() {
        // Utility class - prevent instantiation
    }
}
