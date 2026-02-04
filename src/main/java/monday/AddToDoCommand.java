package monday;

/**
 * Command to add a ToDo task to the task list.
 * Creates a simple task without any date/time information.
 */
public class AddToDoCommand extends AddCommand {

    /**
     * Creates a new add todo command.
     *
     * @param description The task description.
     */
    public AddToDoCommand(String description) {
        super(description);
    }

    /**
     * Creates a new ToDo task.
     *
     * @return The created ToDo task.
     */
    @Override
    protected Task createTask() {
        return new ToDo(description);
    }
}
