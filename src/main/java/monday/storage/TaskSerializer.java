package monday.storage;

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
        String type = task.getTypeIcon().replaceAll("[\\[\\]]", "");
        String done = task.isDone() ? "1" : "0";
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
