package monday.ui;

import monday.constants.MessageConstants;
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
        String message = MessageConstants.SUCCESS_TASK_ADDED_PREFIX + "  " + task + "\n"
                + (totalTasks == 1 ? MessageConstants.SUCCESS_TASK_COUNT_SINGULAR
                        : MessageConstants.SUCCESS_TASK_COUNT_PREFIX + totalTasks
                                + MessageConstants.SUCCESS_TASK_COUNT_PLURAL);
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        String message = MessageConstants.SUCCESS_TASK_DELETED_PREFIX + "  " + task + "\n"
                + (totalTasks == 1 ? MessageConstants.SUCCESS_TASK_COUNT_SINGULAR
                        : MessageConstants.SUCCESS_TASK_COUNT_PREFIX + totalTasks
                                + MessageConstants.SUCCESS_TASK_COUNT_PLURAL);
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if task was marked as done, false if unmarked.
     */
    public void showTaskMarked(Task task, boolean isDone) {
        String message = (isDone ? MessageConstants.SUCCESS_TASK_MARKED_DONE
                                : MessageConstants.SUCCESS_TASK_UNMARKED)
                + "\n" + "  " + task;
        messageFormatter.showResponse(message);
    }

    /**
     * Displays a farewell message when user exits.
     */
    public void showFarewell() {
        messageFormatter.showResponse(MessageConstants.FAREWELL_MESSAGE);
    }

    /**
     * Displays help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     */
    public void showHelp() {
        String response = MessageConstants.HELP_HEADER
                + MessageConstants.HELP_TODO
                + MessageConstants.HELP_DEADLINE
                + MessageConstants.HELP_EVENT
                + MessageConstants.HELP_LIST
                + MessageConstants.HELP_FIND
                + MessageConstants.HELP_VIEW
                + MessageConstants.HELP_MARK
                + MessageConstants.HELP_UNMARK
                + MessageConstants.HELP_DELETE
                + MessageConstants.HELP_CHEER
                + MessageConstants.HELP_HELP
                + MessageConstants.HELP_EXIT;
        messageFormatter.showResponse(response);
    }
}
