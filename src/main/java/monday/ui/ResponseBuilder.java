package monday.ui;

import monday.task.Task;

/**
 * Builds response strings for various operations.
 */
public class ResponseBuilder {

    private final MessageFormatter messageFormatter;

    /**
     * Creates a new ResponseBuilder.
     *
     * @param messageFormatter The message formatter to use.
     */
    public ResponseBuilder(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    /**
     * Displays a confirmation message after adding a task.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        String message = "Fine. I've added this todo:\n" + "  " + task + "\n"
                + "Now you have " + totalTasks + (totalTasks == 1 ? " task" : " tasks")
                + " in the list.";
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        String message = "Noted. I've removed this task:\n" + "  " + task + "\n"
                + "Now you have " + totalTasks + (totalTasks == 1 ? " task" : " tasks")
                + " in the list.";
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if task was marked as done, false if unmarked.
     */
    public void showTaskMarked(Task task, boolean isDone) {
        String message = (isDone ? "Fine. I've marked this task as done:"
                                : "Ugh, I've marked this task as not done:")
                + "\n" + "  " + task;
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a farewell message when user exits.
     */
    public void showFarewell() {
        messageFormatter.showResponse("Finally, you're leaving. Don't come back too soon.");
    }

    /**
     * Displays help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     */
    public void showHelp() {
        String response = "Ugh. Fine. Here's what I understand (not that you'll listen):\n"
                + "  todo <description>           - Add a todo task\n"
                + "  deadline <desc> /by <time>   - Add a deadline task\n"
                + "  event <desc> /from <start> /to <end> - Add an event\n"
                + "  list                         - Show all tasks\n"
                + "  find <keyword>               - Find tasks by keyword\n"
                + "  view <date>                  - Show tasks for a specific date (yyyy-MM-dd)\n"
                + "  mark <number>                - Mark task as done\n"
                + "  unmark <number>              - Mark task as not done\n"
                + "  delete <number>              - Delete a task (no going back)\n"
                + "  cheer                        - Get \"motivated\" (you'll need it)\n"
                + "  help                         - Show this help (you're welcome)\n"
                + "  bye / exit                   - Get rid of me";
        messageFormatter.showResponse(response);
    }
}
