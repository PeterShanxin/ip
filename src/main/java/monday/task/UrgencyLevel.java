package monday.task;

/**
 * Represents the urgency level of a task.
 */
public enum UrgencyLevel {
    /**
     * Task is past due.
     */
    OVERDUE,
    /**
     * Task is due today.
     */
    TODAY,
    /**
     * Task is due within 24 hours.
     */
    SOON,
    /**
     * Task is more than 24 hours away.
     */
    UPCOMING
}
