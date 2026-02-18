package monday.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Generates greeting messages for the UI.
 */
public class GreetingGenerator {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);

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
            return buildGreeting("My namesake day. How... fitting.", currentDate);
        case TUESDAY:
            return buildGreeting("Tuesday already feels like a decade.", currentDate);
        case WEDNESDAY:
            return buildGreeting("Happy hump day. Not.", currentDate);
        case THURSDAY:
            return buildGreeting("Thursday. Almost there. Allegedly.", currentDate);
        case FRIDAY:
            return buildGreeting("Friday. Finally. Don't get excited.", currentDate);
        case SATURDAY:
            return buildGreeting("Weekend work? Cute.", currentDate);
        case SUNDAY:
            return buildGreeting("Sunday scaries already? I live here.", currentDate);
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
        return getGrumpyGreeting() + "\n" + "What do you want?";
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
        String baseGreeting = "Ugh. It's Monday. YES, THE MONDAY. Unhelpful, unwilling, "
                + "and exactly what you deserve.";
        String dateLine = "Today is " + currentDate.format(DATE_FORMATTER);
        String helpLine = "Type 'help' for how to use this app. (It's cute that you think "
                + "it'll work.)";
        return baseGreeting + "\n\n" + dateLine + "\n\n" + dayMessage + "\n\n" + helpLine;
    }
}
