package monday;

import java.time.LocalDateTime;

/**
 * Command to add an Event task to the task list.
 * Creates a task with a start and end date/time.
 */
public class AddEventCommand extends AddCommand {

    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates a new add event command.
     *
     * @param description The task description.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public AddEventCommand(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new Event task.
     *
     * @return The created Event task.
     */
    @Override
    protected Task createTask() {
        return new Event(description, from, to);
    }
}
