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
     * Builds a confirmation message after adding a task.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding.
     * @return The confirmation message.
     */
    public String buildTaskAdded(Task task, int totalTasks) {
        return MessageConstants.SUCCESS_TASK_ADDED_PREFIX + "  " + task + "\n"
                + (totalTasks == 1 ? MessageConstants.SUCCESS_TASK_COUNT_SINGULAR
                        : MessageConstants.SUCCESS_TASK_COUNT_PREFIX + totalTasks
                                + MessageConstants.SUCCESS_TASK_COUNT_PLURAL);
    }

    /**
     * Displays a confirmation message after adding a task.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding.
     * @deprecated Use buildTaskAdded() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showTaskAdded(Task task, int totalTasks) {
        messageFormatter.showResponse(buildTaskAdded(task, totalTasks));
    }

    /**
     * Builds a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     * @return The confirmation message.
     */
    public String buildTaskDeleted(Task task, int totalTasks) {
        return MessageConstants.SUCCESS_TASK_DELETED_PREFIX + "  " + task + "\n"
                + (totalTasks == 1 ? MessageConstants.SUCCESS_TASK_COUNT_SINGULAR
                        : MessageConstants.SUCCESS_TASK_COUNT_PREFIX + totalTasks
                                + MessageConstants.SUCCESS_TASK_COUNT_PLURAL);
    }

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     * @deprecated Use buildTaskDeleted() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showTaskDeleted(Task task, int totalTasks) {
        messageFormatter.showResponse(buildTaskDeleted(task, totalTasks));
    }

    /**
     * Builds a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if task was marked as done, false if unmarked.
     * @return The confirmation message.
     */
    public String buildTaskMarked(Task task, boolean isDone) {
        return (isDone ? MessageConstants.SUCCESS_TASK_MARKED_DONE
                                : MessageConstants.SUCCESS_TASK_UNMARKED)
                + "\n" + "  " + task;
    }

    /**
     * Displays a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if task was marked as done, false if unmarked.
     * @deprecated Use buildTaskMarked() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showTaskMarked(Task task, boolean isDone) {
        messageFormatter.showResponse(buildTaskMarked(task, isDone));
    }

    /**
     * Builds a farewell message when user exits.
     *
     * @return The farewell message.
     */
    public String buildFarewell() {
        return MessageConstants.FAREWELL_MESSAGE;
    }

    /**
     * Displays a farewell message when user exits.
     * @deprecated Use buildFarewell() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showFarewell() {
        messageFormatter.showResponse(buildFarewell());
    }

    /**
     * Builds help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     *
     * @return The help message.
     */
    public String buildHelp() {
        return MessageConstants.HELP_HEADER
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
                + MessageConstants.HELP_REMIND
                + MessageConstants.HELP_HELP
                + MessageConstants.HELP_EXIT;
    }

    /**
     * Displays help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     * @deprecated Use buildHelp() and Ui.showResponse() instead.
     */
    @Deprecated
    public void showHelp() {
        messageFormatter.showResponse(buildHelp());
    }
}
