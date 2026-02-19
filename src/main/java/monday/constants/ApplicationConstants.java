package monday.constants;

/**
 * Application-wide constants for the Monday task management application.
 * Contains file paths, limits, and configuration values.
 */
public final class ApplicationConstants {

    private ApplicationConstants() {
        // Utility class - prevent instantiation
    }

    // ========== File Paths ==========

    /** Default data directory name */
    public static final String DATA_DIR_NAME = "data";

    /** Default storage file name */
    public static final String STORAGE_FILE_NAME = "monday.txt";

    /** Corrupted file extension */
    public static final String CORRUPTED_FILE_EXTENSION = ".corrupted";

    /** Cheer quotes file path */
    public static final String CHEER_FILE_PATH = "data/cheer.txt";

    // ========== Application Limits ==========

    /** Maximum number of tasks allowed */
    public static final int MAX_TASKS = 100;

    // ========== Storage Format ==========

    /** Pipe delimiter for storage format */
    public static final String PIPE_DELIMITER = "|";

    /** Field separator in storage format (pipe with optional spaces) */
    public static final String STORAGE_FIELD_SEPARATOR = "\\s*\\|\\s*";

    /** Prefix for deadline field in storage */
    public static final String STORAGE_BY_PREFIX = "by:";

    /** Prefix for from field in storage */
    public static final String STORAGE_FROM_PREFIX = "from:";

    /** Prefix for to field in storage */
    public static final String STORAGE_TO_PREFIX = "to:";

    // ========== Task Status ==========

    /** Status indicator for completed task */
    public static final String TASK_STATUS_DONE = "1";

    /** Status indicator for incomplete task */
    public static final String TASK_STATUS_NOT_DONE = "0";

    // ========== Task Type Icons ==========

    /** Task type icon for ToDo */
    public static final String TASK_TYPE_TODO = "[T]";

    /** Task type icon for Deadline */
    public static final String TASK_TYPE_DEADLINE = "[D]";

    /** Task type icon for Event */
    public static final String TASK_TYPE_EVENT = "[E]";

    // ========== Task Type Codes ==========

    /** Task type code for ToDo in storage */
    public static final String TASK_CODE_TODO = "T";

    /** Task type code for Deadline in storage */
    public static final String TASK_CODE_DEADLINE = "D";

    /** Task type code for Event in storage */
    public static final String TASK_CODE_EVENT = "E";

    // ========== ANSI Color Codes ==========

    /** ANSI code for yellow text */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /** ANSI code to reset text color */
    public static final String ANSI_RESET = "\u001B[0m";

    // ========== Indexing ==========

    /** Offset for converting between 1-indexed and 0-indexed values */
    public static final int INDEX_OFFSET = 1;
}
