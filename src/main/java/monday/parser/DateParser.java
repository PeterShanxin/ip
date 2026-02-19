package monday.parser;

import monday.constants.ValidationConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses date/time arguments for commands.
 */
public class DateParser {

    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_1 = ValidationConstants.VIEW_INPUT_FORMATTER_1;
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_2 = ValidationConstants.VIEW_INPUT_FORMATTER_2;

    /**
     * Parses a date string for the view command.
     * Tries multiple formats: yyyy-MM-dd, then d/M/yyyy.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDateTime (time set to midnight).
     * @throws DateTimeParseException If the string cannot be parsed with any format.
     */
    public LocalDateTime parseViewDate(String dateString) throws DateTimeParseException {
        try {
            LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_1);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_2);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException(
                        "Ugh, I can't understand that date. Try 'yyyy-MM-dd' or 'd/M/yyyy' format.",
                        dateString, 0);
            }
        }
    }
}
