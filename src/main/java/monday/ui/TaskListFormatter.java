package monday.ui;

import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;
import monday.task.Deadline;
import monday.task.Event;
import monday.task.Task;
import monday.task.TaskCounts;
import monday.task.UrgencyLevel;

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
     * Builds a list of all tasks.
     *
     * @param tasks The list of tasks to display.
     * @return The formatted task list.
     */
    public String buildTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return MessageConstants.INFO_EMPTY_TASK_LIST;
        } else {
            return formatTaskList(tasks);
        }
    }

    /**
     * Displays list of all tasks.
     *
     * @param tasks The list of tasks to display.
     * @deprecated Use buildTaskList() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showTaskList(List<Task> tasks) {
        messageFormatter.showResponse(buildTaskList(tasks));
    }

    /**
     * Builds tasks filtered by a specific date.
     *
     * @param tasks The list of filtered tasks to display.
     * @param date The date for which tasks are being displayed.
     * @return The formatted filtered tasks.
     */
    public String buildFilteredTasks(List<Task> tasks, LocalDateTime date) {
        if (tasks.isEmpty()) {
            return MessageConstants.INFO_NO_FILTERED_TASKS_PREFIX
                    + date.format(ValidationConstants.VIEW_OUTPUT_FORMATTER)
                    + MessageConstants.INFO_NO_FILTERED_TASKS_SUFFIX;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageConstants.INFO_FILTERED_TASKS_PREFIX)
              .append(date.format(ValidationConstants.VIEW_OUTPUT_FORMATTER))
              .append(MessageConstants.INFO_FILTERED_TASKS_SUFFIX);
            sb.append(formatTaskList(tasks));
            return sb.toString();
        }
    }

    /**
     * Displays tasks filtered by a specific date.
     *
     * @param tasks The list of filtered tasks to display.
     * @param date The date for which tasks are being displayed.
     * @deprecated Use buildFilteredTasks() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showFilteredTasks(List<Task> tasks, LocalDateTime date) {
        messageFormatter.showResponse(buildFilteredTasks(tasks, date));
    }

    /**
     * Builds tasks that match a keyword search.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     * @return The formatted matching tasks.
     */
    public String buildMatchingTasks(List<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            return MessageConstants.INFO_NO_MATCHING_TASKS_PREFIX + keyword
                    + MessageConstants.INFO_NO_MATCHING_TASKS_SUFFIX;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageConstants.INFO_MATCHING_TASKS);
            sb.append(formatTaskList(tasks));
            return sb.toString();
        }
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     * @deprecated Use buildMatchingTasks() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showMatchingTasks(List<Task> tasks, String keyword) {
        messageFormatter.showResponse(buildMatchingTasks(tasks, keyword));
    }

    /**
     * Builds task summary and upcoming tasks.
     *
     * @param counts The task counts.
     * @param earliestTask The earliest upcoming task (may be null).
     * @return The formatted reminder message.
     */
    public String buildReminders(TaskCounts counts, Task earliestTask) {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append(MessageConstants.REMIND_HEADER);

        // Handle empty task list
        if (counts.getTotal() == 0) {
            sb.append(MessageConstants.REMIND_NO_TASKS);
            return sb.toString();
        }

        // Task counts
        sb.append(MessageConstants.REMIND_COUNTS_PREFIX);
        sb.append(counts.getTotal());
        sb.append(counts.getTotal() == 1
            ? MessageConstants.REMIND_COUNTS_SINGULAR
            : MessageConstants.REMIND_COUNTS_PLURAL);

        // Status counts
        sb.append(MessageConstants.REMIND_COMPLETED_COUNT);
        sb.append(counts.getCompleted());
        sb.append(". ");
        sb.append(MessageConstants.REMIND_PENDING_COUNT);
        sb.append(counts.getPending());
        sb.append(".\n");

        // Type counts
        sb.append(MessageConstants.REMIND_TODO_COUNT);
        sb.append(counts.getTodos());
        sb.append(". ");
        sb.append(MessageConstants.REMIND_DEADLINE_COUNT);
        sb.append(counts.getDeadlines());
        sb.append(". ");
        sb.append(MessageConstants.REMIND_EVENT_COUNT);
        sb.append(counts.getEvents());
        sb.append(".\n");

        // Handle all tasks completed
        if (counts.getPending() == 0) {
            sb.append(MessageConstants.REMIND_ALL_DONE);
            return sb.toString();
        }

        // Handle no upcoming tasks
        if (earliestTask == null) {
            sb.append(MessageConstants.REMIND_NO_UPCOMING);
            return sb.toString();
        }

        // Display upcoming task with urgency indicator
        LocalDateTime taskTime = getTaskTime(earliestTask);
        UrgencyLevel urgency = getUrgencyLevel(earliestTask, taskTime);

        switch (urgency) {
            case OVERDUE:
                sb.append(MessageConstants.REMIND_OVERDUE_PREFIX);
                break;
            default:
                sb.append(MessageConstants.REMIND_UPCOMING_PREFIX);
                break;
        }

        sb.append(earliestTask.toString());

        // Add urgency suffix for today/soon
        if (urgency == UrgencyLevel.TODAY) {
            sb.append(MessageConstants.REMIND_DUE_TODAY);
        } else if (urgency == UrgencyLevel.SOON) {
            sb.append(MessageConstants.REMIND_DUE_SOON);
        }

        return sb.toString();
    }

    /**
     * Displays task summary and upcoming tasks.
     *
     * @param counts The task counts.
     * @param earliestTask The earliest upcoming task (may be null).
     * @deprecated Use buildReminders() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showReminders(TaskCounts counts, Task earliestTask) {
        messageFormatter.showResponse(buildReminders(counts, earliestTask));
    }

    /**
     * Gets the upcoming time for a task.
     * For Deadline tasks, uses the by date/time.
     * For Event tasks, uses the from date/time.
     *
     * @param task The task to get the time for.
     * @return The upcoming time for the task.
     */
    private LocalDateTime getTaskTime(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getByDateTime();
        } else if (task instanceof Event) {
            return ((Event) task).getFromDateTime();
        }
        return LocalDateTime.MAX;
    }

    /**
     * Gets the urgency level for a task based on its time.
     *
     * @param task The task to classify.
     * @param taskTime The task's upcoming time.
     * @return The urgency level of the task.
     */
    private UrgencyLevel getUrgencyLevel(Task task, LocalDateTime taskTime) {
        LocalDateTime now = LocalDateTime.now();
        if (taskTime.isBefore(now)) {
            return UrgencyLevel.OVERDUE;
        } else if (taskTime.toLocalDate().equals(now.toLocalDate())) {
            return UrgencyLevel.TODAY;
        } else if (taskTime.isBefore(now.plusHours(24))) {
            return UrgencyLevel.SOON;
        } else {
            return UrgencyLevel.UPCOMING;
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
