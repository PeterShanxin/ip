package monday.constants;

import java.time.format.DateTimeFormatter;

/**
 * Validation constants for the Monday task management application.
 * Contains validation limits, date format patterns, and regex patterns.
 */
public final class ValidationConstants {

    private ValidationConstants() {
        // Utility class - prevent instantiation
    }

    // ========== Validation Limits ==========

    /** Maximum number of tasks allowed */
    public static final int MAX_TASKS = 100;

    /** Minimum valid task number */
    public static final int MIN_TASK_NUMBER = 1;

    /** Minimum array index */
    public static final int MIN_ARRAY_INDEX = 0;

    /** Minimum number of parts for task storage format */
    public static final int MIN_STORAGE_PARTS = 3;

    /** Number of parts for deadline storage format */
    public static final int DEADLINE_STORAGE_PARTS = 4;

    /** Number of parts for event storage format */
    public static final int EVENT_STORAGE_PARTS = 5;

    /** Minimum number of parts for splitting command */
    public static final int MIN_COMMAND_PARTS = 2;

    /** Maximum number of parts when splitting by prefix */
    public static final int MAX_PREFIX_SPLIT_PARTS = 2;

    /** Minimum number of parts when splitting by field */
    public static final int MIN_FIELD_SPLIT_PARTS = 2;

    // ========== Date Format Patterns ==========

    /** Date/time format pattern 1: yyyy-MM-dd HHmm */
    public static final String DATETIME_FORMAT_PATTERN_1 = "yyyy-MM-dd HHmm";

    /** Date/time format pattern 2: d/M/yyyy HHmm */
    public static final String DATETIME_FORMAT_PATTERN_2 = "d/M/yyyy HHmm";

    /** Date/time output format pattern: MMM dd yyyy HHmm */
    public static final String DATETIME_OUTPUT_FORMAT_PATTERN = "MMM dd yyyy HHmm";

    /** Date/time storage format pattern: yyyy-MM-dd HH:mm */
    public static final String DATETIME_STORAGE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";

    /** Date format pattern 1 for view command: yyyy-MM-dd */
    public static final String DATE_FORMAT_PATTERN_1 = "yyyy-MM-dd";

    /** Date format pattern 2 for view command: d/M/yyyy */
    public static final String DATE_FORMAT_PATTERN_2 = "d/M/yyyy";

    /** Date output format pattern: MMM dd yyyy */
    public static final String DATE_OUTPUT_FORMAT_PATTERN = "MMM dd yyyy";

    /** Greeting date format pattern: EEEE, d MMMM yyyy */
    public static final String GREETING_DATE_FORMAT_PATTERN = "EEEE, d MMMM yyyy";

    // ========== Regex Patterns ==========

    /** Regex pattern for splitting by pipe delimiter with optional spaces */
    public static final String PIPE_DELIMITER_REGEX = "\\s*\\|\\s*";

    /** Regex pattern for splitting by whitespace */
    public static final String WHITESPACE_REGEX = "\\s+";

    /** Regex pattern for removing task type icon brackets */
    public static final String TASK_ICON_BRACKET_REGEX = "[\\[\\]]";

    /** Regex pattern for splitting by colon */
    public static final String COLON_SPLIT_REGEX = ":";

    // ========== Date Formatters ==========

    /** Date/time formatter for input format 1: yyyy-MM-dd HHmm */
    public static final DateTimeFormatter INPUT_FORMATTER_1 = DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN_1);

    /** Date/time formatter for input format 2: d/M/yyyy HHmm */
    public static final DateTimeFormatter INPUT_FORMATTER_2 = DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN_2);

    /** Date/time formatter for output: MMM dd yyyy HHmm */
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT_PATTERN);

    /** Date/time formatter for storage: yyyy-MM-dd HH:mm */
    public static final DateTimeFormatter STORAGE_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_STORAGE_FORMAT_PATTERN);

    /** Date formatter for view input format 1: yyyy-MM-dd */
    public static final DateTimeFormatter VIEW_INPUT_FORMATTER_1 = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_1);

    /** Date formatter for view input format 2: d/M/yyyy */
    public static final DateTimeFormatter VIEW_INPUT_FORMATTER_2 = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_2);

    /** Date formatter for view output: MMM dd yyyy */
    public static final DateTimeFormatter VIEW_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT_PATTERN);

    /** Date formatter for greeting: EEEE, d MMMM yyyy */
    public static final DateTimeFormatter GREETING_DATE_FORMATTER = DateTimeFormatter.ofPattern(GREETING_DATE_FORMAT_PATTERN);

    // ========== Task Prefix Values ==========

    /** Prefix for deadline: /by */
    public static final String PREFIX_BY = "/by";

    /** Prefix for event start: /from */
    public static final String PREFIX_FROM = "/from";

    /** Prefix for event end: /to */
    public static final String PREFIX_TO = "/to";

    // ========== Task Type Codes ==========

    /** Task type code for ToDo */
    public static final String TASK_CODE_TODO = "T";

    /** Task type code for Deadline */
    public static final String TASK_CODE_DEADLINE = "D";

    /** Task type code for Event */
    public static final String TASK_CODE_EVENT = "E";

    // ========== Task Status Codes ==========

    /** Task status code for done */
    public static final String TASK_STATUS_DONE = "1";

    /** Task status code for not done */
    public static final String TASK_STATUS_NOT_DONE = "0";

    // ========== Indexing ==========

    /** Offset for converting between 1-indexed and 0-indexed values */
    public static final int INDEX_OFFSET = 1;

    /** Space character for indexing */
    public static final char SPACE_CHAR = ' ';

    // ========== Storage Field Indices ==========

    /** Index for task type in storage parts */
    public static final int STORAGE_INDEX_TYPE = 0;

    /** Index for task status in storage parts */
    public static final int STORAGE_INDEX_STATUS = 1;

    /** Index for task description in storage parts */
    public static final int STORAGE_INDEX_DESCRIPTION = 2;

    /** Index for deadline by field in storage parts */
    public static final int STORAGE_INDEX_BY = 3;

    /** Index for event from field in storage parts */
    public static final int STORAGE_INDEX_FROM = 3;

    /** Index for event to field in storage parts */
    public static final int STORAGE_INDEX_TO = 4;
}
