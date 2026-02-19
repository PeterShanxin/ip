package monday.ui;

import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Generates greeting messages for the UI.
 */
public class GreetingGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = ValidationConstants.GREETING_DATE_FORMATTER;

    /**
     * Gets a grumpy greeting based on current day of week.
     *
     * @return A grumpy greeting message for current day.
     */
    public String getGrumpyGreeting() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();

        switch (day) {
        case MONDAY:
            return buildGreeting(MessageConstants.GREETING_MONDAY, currentDate);
        case TUESDAY:
            return buildGreeting(MessageConstants.GREETING_TUESDAY, currentDate);
        case WEDNESDAY:
            return buildGreeting(MessageConstants.GREETING_WEDNESDAY, currentDate);
        case THURSDAY:
            return buildGreeting(MessageConstants.GREETING_THURSDAY, currentDate);
        case FRIDAY:
            return buildGreeting(MessageConstants.GREETING_FRIDAY, currentDate);
        case SATURDAY:
            return buildGreeting(MessageConstants.GREETING_SATURDAY, currentDate);
        case SUNDAY:
            return buildGreeting(MessageConstants.GREETING_SUNDAY, currentDate);
        default:
            // Unreachable: DayOfWeek enum covers all 7 days
            throw new AssertionError("Unknown day: " + day);
        }
    }

    /**
     * Gets greeting message for GUI (without printing).
     *
     * @return The formatted greeting message.
     */
    public String getGreetingForGui() {
        return getGrumpyGreeting() + "\n" + MessageConstants.GREETING_PROMPT;
    }

    /**
     * Builds a complete greeting message from base greeting, current date,
     * day-specific message, and help line.
     *
     * @param dayMessage The day-specific message to insert after date.
     * @param currentDate The current date to display in greeting.
     * @return The complete formatted greeting message.
     */
    private String buildGreeting(String dayMessage, LocalDate currentDate) {
        String baseGreeting = MessageConstants.GREETING_BASE;
        String dateLine = MessageConstants.GREETING_DATE_PREFIX + currentDate.format(DATE_FORMATTER);
        String helpLine = MessageConstants.GREETING_HELP_LINE;
        return baseGreeting + "\n\n" + dateLine + "\n\n" + dayMessage + "\n\n" + helpLine;
    }
}
