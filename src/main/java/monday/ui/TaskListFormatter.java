package monday.ui;

import monday.task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Formats task lists for display.
 */
public class TaskListFormatter {

    private static final DateTimeFormatter VIEW_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final MessageFormatter messageFormatter;

    /**
     * Creates a new TaskListFormatter.
     *
     * @param messageFormatter The message formatter to use.
     */
    public TaskListFormatter(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    /**
     * Displays list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            messageFormatter.showResponse("Skeptical. You haven't told me to do anything yet.");
        } else {
            String formattedList = formatTaskList(tasks);
            messageFormatter.showResponse(formattedList);
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
            messageFormatter.showResponse("Skeptical. Nothing scheduled for "
                    + date.format(VIEW_OUTPUT_FORMATTER) + ".");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Ugh. Here's what you have on ")
              .append(date.format(VIEW_OUTPUT_FORMATTER))
              .append(":\n");
            sb.append(formatTaskList(tasks));
            messageFormatter.showResponse(sb.toString());
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
            messageFormatter.showResponse("Fine. No tasks match \"" + keyword + "\". Shocking, I know.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are matching tasks in your list:\n");
            sb.append(formatTaskList(tasks));
            messageFormatter.showResponse(sb.toString());
        }
    }

    /**
     * Formats a list of tasks as a numbered string.
     *
     * @param tasks The list of tasks to format.
     * @return The formatted string representation.
     */
    private String formatTaskList(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(formatTaskEntry(i + 1, tasks.get(i)));
        }
        return sb.toString();
    }

    /**
     * Formats a single task entry with its index.
     *
     * @param index The task index (1-based).
     * @param task The task to format.
     * @return The formatted task entry string.
     */
    private String formatTaskEntry(int index, Task task) {
        return index + ". " + task;
    }

    /**
     * Formats a task list header with the specified text.
     *
     * @param headerText The header text to display.
     * @return The formatted header string.
     */
    private String formatTaskListHeader(String headerText) {
        return headerText + "\n";
    }
}
