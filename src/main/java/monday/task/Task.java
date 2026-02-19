package monday.task;

/**
 * Represents a task in Monday's task list.
 * Each task has a description and a completion status.
 */
public class Task {
    private static final String DONE_ICON = "[X]";
    private static final String TODO_ICON = "[ ]";
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    private final String description;
    private boolean isDone;

    /**
     * Creates a new task with given description.
     * Tasks are initially not completed.
     *
     * @param description The task description.
     * @throws IllegalArgumentException If description is null, empty, or too long.
     */
    public Task(String description) {
        validateDescription(description);
        this.description = description;
        this.isDone = false;
    }

    /**
     * Validates the task description.
     *
     * @param description The description to validate.
     * @throws IllegalArgumentException If description is null, empty, or too long.
     */
    private void validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Ugh, task description cannot be null.");
        }
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Ugh, task description cannot be empty.");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Ugh, task description is too long. "
                    + "Maximum is " + MAX_DESCRIPTION_LENGTH + " characters.");
        }
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
        assert this.isDone : "Task should be marked as done after markAsDone()";
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
        assert !this.isDone : "Task should be marked as not done after markAsNotDone()";
    }

    /**
     * Returns completion status of this task.
     *
     * @return true if task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns status icon for this task.
     *
     * @return "[X]" if done, "[ ]" if not done.
     */
    public String getStatusIcon() {
        return isDone ? DONE_ICON : TODO_ICON;
    }

    /**
     * Returns description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns type-specific icon for this task.
     * Default implementation returns generic icon for backward compatibility.
     * Subclasses override to provide their type identifier.
     *
     * @return The type icon (e.g., "[T]", "[D]", "[E]", or "[]" for generic).
     */
    public String getTypeIcon() {
        return TaskType.GENERIC.getIcon();
    }

    /**
     * Returns full description including type-specific details.
     * Base implementation returns just description.
     * Subclasses override to add date/time information.
     *
     * @return The full description.
     */
    public String getFullDescription() {
        return description;
    }

    /**
     * Returns string representation of this task.
     *
     * @return "typeIcon statusIcon fullDescription" format.
     */
    @Override
    public String toString() {
        return getTypeIcon() + getStatusIcon() + " " + getFullDescription();
    }
}
