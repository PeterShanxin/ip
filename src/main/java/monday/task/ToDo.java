package monday.task;

/**
 * Represents a todo task without any date/time attached to it.
 * Todo tasks are simple tasks that need to be done.
 */
public class ToDo extends Task {

    /**
     * Creates a new todo task with the given description.
     *
     * @param description The task description.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the type-specific icon for this todo task.
     *
     * @return "[T]" icon representing a todo task.
     */
    @Override
    public String getTypeIcon() {
        return TaskType.TODO.getIcon();
    }
}
