package monday.task;

import monday.command.CommandException;
import monday.constants.MessageConstants;
import monday.constants.ValidationConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the list of tasks for MONDAY.
 * Provides operations to add, delete, mark, unmark, and filter tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates a new TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     * @return The deleted task.
     * @throws CommandException If the task number is invalid.
     */
    public Task deleteTask(int taskNumber) throws CommandException {
        validateTaskNumber(taskNumber);
        return tasks.remove(convertToZeroIndexed(taskNumber));
    }

    /**
     * Marks a task as done by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     * @throws CommandException If the task number is invalid.
     */
    public void markTaskAsDone(int taskNumber) throws CommandException {
        validateTaskNumber(taskNumber);
        tasks.get(convertToZeroIndexed(taskNumber)).markAsDone();
    }

    /**
     * Marks a task as not done by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     * @throws CommandException If the task number is invalid.
     */
    public void markTaskAsNotDone(int taskNumber) throws CommandException {
        validateTaskNumber(taskNumber);
        tasks.get(convertToZeroIndexed(taskNumber)).markAsNotDone();
    }

    /**
     * Gets a task by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     * @return The task at the specified number.
     * @throws CommandException If the task number is invalid.
     */
    public Task getTask(int taskNumber) throws CommandException {
        validateTaskNumber(taskNumber);
        return tasks.get(convertToZeroIndexed(taskNumber));
    }

    /**
     * Gets all tasks in the list.
     *
     * @return A list of all tasks.
     */
    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>(tasks);
        return taskList;
    }

    /**
     * Filters tasks by a specific date.
     * Returns tasks that implement DateFilterable and occur on the given date.
     *
     * @param date The date to filter by.
     * @return A list of tasks occurring on the specified date.
     */
    public List<Task> filterTasksByDate(LocalDateTime date) {
        return tasks.stream()
                .filter(task -> task instanceof DateFilterable)
                .map(task -> (DateFilterable) task)
                .filter(dateFilterable -> dateFilterable.isOnDate(date))
                .map(dateFilterable -> (Task) dateFilterable)
                .collect(Collectors.toList());
    }

    /**
     * Filters tasks by a keyword in their description.
     * Performs case-insensitive substring matching on task descriptions.
     *
     * @param keyword The keyword to search for.
     * @return A list of tasks whose descriptions contain the keyword.
     */
    public List<Task> getFilteredTasks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Gets task counts by type and status.
     *
     * @return A TaskCounts object containing counts by type and status.
     */
    public TaskCounts getTaskCounts() {
        int total = tasks.size();
        int completed = (int) tasks.stream().filter(Task::isDone).count();
        int pending = total - completed;
        int todos = (int) tasks.stream().filter(t -> t instanceof ToDo).count();
        int deadlines = (int) tasks.stream().filter(t -> t instanceof Deadline).count();
        int events = (int) tasks.stream().filter(t -> t instanceof Event).count();
        return new TaskCounts(total, completed, pending, todos, deadlines, events);
    }

    /**
     * Gets the earliest upcoming uncompleted task (Deadline or Event).
     * Returns null if no upcoming tasks exist.
     *
     * @return The earliest upcoming task, or null if none.
     */
    public Task getEarliestUpcomingTask() {
        return tasks.stream()
                .filter(task -> !task.isDone())
                .filter(task -> task instanceof Deadline || task instanceof Event)
                .min((t1, t2) -> {
                    LocalDateTime time1 = getUpcomingTime(t1);
                    LocalDateTime time2 = getUpcomingTime(t2);
                    return time1.compareTo(time2);
                })
                .orElse(null);
    }

    /**
     * Gets the upcoming time for a task.
     * For Deadline tasks, uses the by date/time.
     * For Event tasks, uses the from date/time.
     *
     * @param task The task to get the time for.
     * @return The upcoming time for the task.
     */
    private LocalDateTime getUpcomingTime(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getByDateTime();
        } else if (task instanceof Event) {
            return ((Event) task).getFromDateTime();
        }
        return LocalDateTime.MAX;
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return The task count.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Checks if the task list is at maximum capacity.
     *
     * @return true if at max capacity, false otherwise.
     */
    public boolean isAtMaxCapacity() {
        return tasks.size() >= ValidationConstants.MAX_TASKS;
    }

    /**
     * Checks if a task number is valid (within range and list is not empty).
     *
     * @param taskNumber The 1-indexed task number to validate.
     * @return true if the task number is valid, false otherwise.
     */
    public boolean isValidTaskNumber(int taskNumber) {
        return !tasks.isEmpty() && taskNumber >= ValidationConstants.MIN_TASK_NUMBER && taskNumber <= tasks.size();
    }

    /**
     * Converts a 1-indexed task number to a 0-indexed array index.
     *
     * @param taskNumber The 1-indexed task number.
     * @return The 0-indexed array index.
     */
    private int convertToZeroIndexed(int taskNumber) {
        return taskNumber - ValidationConstants.INDEX_OFFSET;
    }

    /**
     * Validates a task number and throws an exception if invalid.
     * This method centralizes task number validation logic.
     *
     * @param taskNumber The 1-indexed task number to validate.
     * @throws CommandException If the task number is invalid.
     */
    public void validateTaskNumber(int taskNumber) throws CommandException {
        if (!isValidTaskNumber(taskNumber)) {
            throw new CommandException(getInvalidTaskNumberMessage());
        }
    }

    /**
     * Returns an error message for invalid task numbers.
     *
     * @return The error message string.
     */
    public String getInvalidTaskNumberMessage() {
        if (tasks.isEmpty()) {
            return MessageConstants.ERROR_INVALID_TASK_NUMBER_EMPTY;
        } else {
            return MessageConstants.ERROR_INVALID_TASK_NUMBER_PREFIX + tasks.size()
                    + MessageConstants.ERROR_INVALID_TASK_NUMBER_SUFFIX;
        }
    }
}
