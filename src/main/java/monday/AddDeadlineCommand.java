package monday;

import java.time.LocalDateTime;

/**
 * Command to add a Deadline task to the task list.
 * Creates a task with a due date/time.
 */
public class AddDeadlineCommand extends AddCommand {

    private final LocalDateTime by;

    /**
     * Creates a new add deadline command.
     *
     * @param description The task description.
     * @param by The due date/time.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a new Deadline task.
     *
     * @return The created Deadline task.
     */
    @Override
    protected Task createTask() {
        return new Deadline(description, by);
    }
}
