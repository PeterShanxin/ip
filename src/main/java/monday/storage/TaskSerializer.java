package monday.storage;

import monday.constants.ApplicationConstants;
import monday.constants.ValidationConstants;
import monday.task.Deadline;
import monday.task.Event;
import monday.task.Task;

/**
 * Handles task encoding for storage.
 */
public class TaskSerializer {

    /**
     * Encodes a task into a string format for storage.
     *
     * @param task The task to encode.
     * @return The encoded string representation.
     */
    public String encodeTask(Task task) {
        String type = task.getTypeIcon().replaceAll(ValidationConstants.TASK_ICON_BRACKET_REGEX, "");
        String done = task.isDone() ? ApplicationConstants.TASK_STATUS_DONE : ApplicationConstants.TASK_STATUS_NOT_DONE;
        String desc = task.getDescription();

        if (task instanceof Deadline) {
            String by = ((Deadline) task).getByForStorage();
            return String.format("%s | %s | %s | by: %s", type, done, desc, by);
        } else if (task instanceof Event) {
            String from = ((Event) task).getFromForStorage();
            String to = ((Event) task).getToForStorage();
            return String.format("%s | %s | %s | from: %s | to: %s", type, done, desc, from, to);
        } else {
            return String.format("%s | %s | %s", type, done, desc);
        }
    }
}
