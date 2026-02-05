package monday.ui;

import monday.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Handles all user interface interactions for MONDAY.
 * Manages display output, user input, and message formatting.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________"
            + "______";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter VIEW_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final Scanner scanner;

    /**
     * Creates a new Ui instance and initializes the input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a response wrapped with line separators and blank lines.
     *
     * @param message The response message to display (can contain newlines).
     */
    public void showResponse(String message) {
        System.out.println(LINE);
        System.out.println();  // blank line after opening LINE
        System.out.println(message);
        System.out.println(LINE);
        System.out.println();  // blank line after closing LINE
    }

    /**
     * Displays a grumpy greeting based on the current day of the week.
     * Each day has a unique sarcastic message reflecting Monday's personality.
     */
    public void showGreeting() {
        String greeting = getGrumpyGreeting() + "\n" + "What do you want?";
        showResponse(greeting);
    }

    /**
     * Displays the farewell message when the user exits.
     */
    public void showFarewell() {
        showResponse("Finally, you're leaving. Don't come back too soon.");
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            showResponse("Skeptical. You haven't told me to do anything yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append((i + 1)).append(". ").append(tasks.get(i));
            }
            showResponse(sb.toString());
        }
    }

    /**
     * Displays tasks filtered by a specific date.
     *
     * @param tasks The list of filtered tasks to display.
     * @param date The date for which tasks are being displayed.
     */
    public void showFilteredTasks(List<Task> tasks, LocalDateTime date) {
        if (tasks.isEmpty()) {
            showResponse("Skeptical. Nothing scheduled for "
                    + date.format(VIEW_OUTPUT_FORMATTER) + ".");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Ugh. Here's what you have on ")
              .append(date.format(VIEW_OUTPUT_FORMATTER))
              .append(":\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append((i + 1)).append(". ").append(tasks.get(i));
            }
            showResponse(sb.toString());
        }
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     */
    public void showMatchingTasks(List<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            showResponse("Fine. No tasks match \"" + keyword + "\". Shocking, I know.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append((i + 1)).append(". ").append(tasks.get(i));
            }
            showResponse(sb.toString());
        }
    }

    /**
     * Displays a confirmation message after adding a task.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        String message = "Fine. I've added this todo:\n" + "  " + task + "\n"
                + "Now you have " + totalTasks + (totalTasks == 1 ? " task" : " tasks")
                + " in the list.";
        showResponse(message);
    }

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        String message = "Noted. I've removed this task:\n" + "  " + task + "\n"
                + "Now you have " + totalTasks + (totalTasks == 1 ? " task" : " tasks")
                + " in the list.";
        showResponse(message);
    }

    /**
     * Displays a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if the task was marked as done, false if unmarked.
     */
    public void showTaskMarked(Task task, boolean isDone) {
        String message = (isDone ? "Fine. I've marked this task as done:"
                                : "Ugh, I've marked this task as not done:")
                + "\n" + "  " + task;
        showResponse(message);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showResponse(message);
    }

    /**
     * Displays an error message for empty input.
     */
    public void showEmptyInputError() {
        showResponse("Ugh, you didn't actually say anything. Try again.");
    }

    /**
     * Displays an error message for a command without arguments.
     *
     * @param command The command that needs arguments.
     * @param example The example of correct usage.
     */
    public void showCommandOnlyError(String command, String example) {
        showResponse("Ugh, " + command + " needs more info. Try '" + example + "'.");
    }

    /**
     * Displays an error message for invalid task number.
     *
     * @param taskCount The current number of tasks.
     */
    public void showInvalidTaskNumberError(int taskCount) {
        if (taskCount == 0) {
            showResponse("Skeptical. You haven't told me to do anything yet.");
        } else {
            showResponse("Ugh, that task doesn't exist. Pick between 1 and " + taskCount + ".");
        }
    }

    /**
     * Displays a corruption message after loading tasks with corrupted data.
     *
     * @param count The number of corrupted lines.
     */
    public void showCorruptionMessage(int count) {
        String unit = count == 1 ? " corrupted line." : " corrupted lines.";
        String message = "Ugh. I skipped " + count + unit + "\nCheck monday.txt.corrupted for recovery.";
        showResponse(message);
    }

    /**
     * Displays help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     */
    public void showHelp() {
        String response = "Ugh. Fine. Here's what I understand (not that you'll listen):\n"
                + "  todo <description>           - Add a todo task\n"
                + "  deadline <desc> /by <time>   - Add a deadline task\n"
                + "  event <desc> /from <start> /to <end> - Add an event\n"
                + "  list                         - Show all tasks\n"
                + "  find <keyword>               - Find tasks by keyword\n"
                + "  view <date>                  - Show tasks for a specific date (yyyy-MM-dd)\n"
                + "  mark <number>                - Mark task as done\n"
                + "  unmark <number>              - Mark task as not done\n"
                + "  delete <number>              - Delete a task (no going back)\n"
                + "  help                         - Show this help (you're welcome)\n"
                + "  bye / exit                   - Get rid of me";
        showResponse(response);
    }

    /**
     * Reads a command from user input.
     *
     * @return The trimmed user input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner used for reading input.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Returns a grumpy greeting based on the current day of the week.
     *
     * @return A grumpy greeting message for the current day.
     */
    private String getGrumpyGreeting() {
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
     * Builds a complete greeting message from the base greeting, current date,
     * day-specific message, and help line.
     *
     * @param dayMessage The day-specific message to insert after the date.
     * @param currentDate The current date to display in the greeting.
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
