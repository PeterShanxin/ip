package monday.task;

/**
 * Represents the type of a task.
 * Each task type has a unique icon identifier for display purposes.
 */
public enum TaskType {
    /** Generic task with no specific type */
    GENERIC("[]"),

    /** Todo task - a simple task without date/time */
    TODO("[T]"),

    /** Deadline task - task with due date/time */
    DEADLINE("[D]"),

    /** Event task - task with start and end date/time */
    EVENT("[E]");

    private final String icon;

    /**
     * Creates a TaskType with the specified icon.
     *
     * @param icon The icon representing this task type.
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon for this task type.
     *
     * @return The task type icon (e.g., "[T]", "[D]", "[E]", or "[]").
     */
    public String getIcon() {
        return icon;
    }
}
