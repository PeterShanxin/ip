package monday.task;

/**
 * Represents counts of tasks by type and status.
 */
public class TaskCounts {
    private final int total;
    private final int completed;
    private final int pending;
    private final int todos;
    private final int deadlines;
    private final int events;

    /**
     * Creates a TaskCounts object with the specified counts.
     *
     * @param total Total number of tasks.
     * @param completed Number of completed tasks.
     * @param pending Number of pending tasks.
     * @param todos Number of ToDo tasks.
     * @param deadlines Number of Deadline tasks.
     * @param events Number of Event tasks.
     */
    public TaskCounts(int total, int completed, int pending, int todos, int deadlines, int events) {
        this.total = total;
        this.completed = completed;
        this.pending = pending;
        this.todos = todos;
        this.deadlines = deadlines;
        this.events = events;
    }

    /**
     * Returns the total number of tasks.
     *
     * @return Total task count.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Returns the number of completed tasks.
     *
     * @return Completed task count.
     */
    public int getCompleted() {
        return completed;
    }

    /**
     * Returns the number of pending tasks.
     *
     * @return Pending task count.
     */
    public int getPending() {
        return pending;
    }

    /**
     * Returns the number of ToDo tasks.
     *
     * @return ToDo task count.
     */
    public int getTodos() {
        return todos;
    }

    /**
     * Returns the number of Deadline tasks.
     *
     * @return Deadline task count.
     */
    public int getDeadlines() {
        return deadlines;
    }

    /**
     * Returns the number of Event tasks.
     *
     * @return Event task count.
     */
    public int getEvents() {
        return events;
    }
}
