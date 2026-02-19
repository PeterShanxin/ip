package monday.ui;

import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;
import monday.task.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Formats task lists for display.
 */
public class TaskListFormatter {

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
            messageFormatter.showResponse(MessageConstants.INFO_EMPTY_TASK_LIST);
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
            messageFormatter.showResponse(MessageConstants.INFO_NO_FILTERED_TASKS_PREFIX
                    + date.format(ValidationConstants.VIEW_OUTPUT_FORMATTER)
                    + MessageConstants.INFO_NO_FILTERED_TASKS_SUFFIX);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageConstants.INFO_FILTERED_TASKS_PREFIX)
              .append(date.format(ValidationConstants.VIEW_OUTPUT_FORMATTER))
              .append(MessageConstants.INFO_FILTERED_TASKS_SUFFIX);
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
            messageFormatter.showResponse(MessageConstants.INFO_NO_MATCHING_TASKS_PREFIX + keyword
                    + MessageConstants.INFO_NO_MATCHING_TASKS_SUFFIX);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageConstants.INFO_MATCHING_TASKS);
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
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> formatTaskEntry(i + 1, tasks.get(i)))
                .collect(Collectors.joining("\n"));
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
