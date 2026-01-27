package monday;

/**
 * Represents a task in Monday's task list.
 * Each task has a description and a completion status.
 */
public class Task {
    private static final String DONE_ICON = "[X]";
    private static final String TODO_ICON = "[ ]";

    private final String description;
    private boolean isDone;

    /**
     * Creates a new task with the given description.
     * Tasks are initially not completed.
     *
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the completion status of this task.
     *
     * @return true if task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "[X]" if done, "[ ]" if not done.
     */
    public String getStatusIcon() {
        return isDone ? DONE_ICON : TODO_ICON;
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation of this task.
     *
     * @return "statusIcon description" format.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
