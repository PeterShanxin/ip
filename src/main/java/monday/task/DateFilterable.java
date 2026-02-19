package monday.task;

import java.time.LocalDateTime;

/**
 * Interface for tasks that can be filtered by date.
 * Tasks implementing this interface support date-based filtering operations.
 */
public interface DateFilterable {

    /**
     * Checks if this task occurs on the specified date.
     * Compares year, month, and day components only.
     *
     * @param date The date to compare with.
     * @return true if this task is on the specified date, false otherwise.
     */
    boolean isOnDate(LocalDateTime date);
}
