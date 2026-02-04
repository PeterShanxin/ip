package monday;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of tasks for MONDAY.
 * Provides operations to add, delete, mark, unmark, and filter tasks.
 */
public class TaskList {

    private static final int MAX_TASKS = 100;
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
     */
    public Task deleteTask(int taskNumber) {
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks a task as done by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     */
    public void markTaskAsDone(int taskNumber) {
        tasks.get(taskNumber - 1).markAsDone();
    }

    /**
     * Marks a task as not done by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     */
    public void markTaskAsNotDone(int taskNumber) {
        tasks.get(taskNumber - 1).markAsNotDone();
    }

    /**
     * Gets a task by its number (1-indexed).
     *
     * @param taskNumber The 1-indexed task number.
     * @return The task at the specified number.
     */
    public Task getTask(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    /**
     * Gets all tasks in the list.
     *
     * @return A list of all tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Filters tasks by a specific date.
     * Returns Deadline and Event tasks that occur on the given date.
     *
     * @param date The date to filter by.
     * @return A list of tasks occurring on the specified date.
     */
    public List<Task> filterTasksByDate(LocalDateTime date) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.isOnDate(date)) {
                    filteredTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.isOnDate(date)) {
                    filteredTasks.add(task);
                }
            }
        }
        return filteredTasks;
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
        return tasks.size() >= MAX_TASKS;
    }

    /**
     * Checks if a task number is valid (within range and list is not empty).
     *
     * @param taskNumber The 1-indexed task number to validate.
     * @return true if the task number is valid, false otherwise.
     */
    public boolean isValidTaskNumber(int taskNumber) {
        return !tasks.isEmpty() && taskNumber >= 1 && taskNumber <= tasks.size();
    }

    /**
     * Returns an error message for invalid task numbers.
     *
     * @return The error message string.
     */
    public String getInvalidTaskNumberMessage() {
        if (tasks.isEmpty()) {
            return "Skeptical. You haven't told me to do anything yet.";
        } else {
            return "Ugh, that task doesn't exist. Pick between 1 and " + tasks.size() + ".";
        }
    }
}
