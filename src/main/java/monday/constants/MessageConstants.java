package monday.constants;

/**
 * Message constants for the Monday task management application.
 * Contains all user-facing messages organized by category.
 */
public final class MessageConstants {

    private MessageConstants() {
        // Utility class - prevent instantiation
    }

    // ========== Error Messages ==========

    /** Error message for empty input */
    public static final String ERROR_EMPTY_INPUT = "Ugh, you didn't actually say anything. Try again.";

    /** Error message prefix for command without arguments */
    public static final String ERROR_COMMAND_WITHOUT_ARGS_PREFIX = "Ugh, ";

    /** Error message suffix for command without arguments */
    public static final String ERROR_COMMAND_WITHOUT_ARGS_SUFFIX = " needs more info. Try '";

    /** Error message suffix for command without arguments (closing) */
    public static final String ERROR_COMMAND_WITHOUT_ARGS_END = "'.";

    /** Error message for invalid task number when list is empty */
    public static final String ERROR_INVALID_TASK_NUMBER_EMPTY = "Skeptical. You haven't told me to do anything yet.";

    /** Error message prefix for invalid task number */
    public static final String ERROR_INVALID_TASK_NUMBER_PREFIX = "Ugh, that task doesn't exist. Pick between 1 and ";

    /** Error message suffix for invalid task number */
    public static final String ERROR_INVALID_TASK_NUMBER_SUFFIX = ".";

    /** Error message for unknown command prefix */
    public static final String ERROR_UNKNOWN_COMMAND_PREFIX = "Ugh, I don't understand '";

    /** Error message for unknown command suffix */
    public static final String ERROR_UNKNOWN_COMMAND_SUFFIX = "'. Type 'help' if you're confused. It's probably hopeless though.";

    /** Error message for todo without description */
    public static final String ERROR_TODO_NO_DESCRIPTION = "Ugh, a todo needs a description. Try 'todo borrow book'.";

    /** Error message for deadline without /by prefix */
    public static final String ERROR_DEADLINE_NO_BY_PREFIX = "Ugh, deadlines need a '/by' time. Try 'deadline return book /by 2019-12-02 1800'.";

    /** Error message for deadline without description */
    public static final String ERROR_DEADLINE_NO_DESCRIPTION = "Ugh, what's the deadline for? Try 'deadline return book /by 2019-12-02 1800'.";

    /** Error message for deadline without time */
    public static final String ERROR_DEADLINE_NO_TIME = "Ugh, when is it due? Try 'deadline return book /by 2019-12-02 1800'.";

    /** Error message for event without /from and /to prefixes */
    public static final String ERROR_EVENT_NO_PREFIXES = "Ugh, events need '/from' and '/to' times. Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";

    /** Error message for event without description */
    public static final String ERROR_EVENT_NO_DESCRIPTION = "Ugh, what's the event? Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";

    /** Error message for event without start time */
    public static final String ERROR_EVENT_NO_FROM = "Ugh, when does it start? Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";

    /** Error message for event without end time */
    public static final String ERROR_EVENT_NO_TO = "Ugh, when does it end? Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";

    /** Error message for invalid event date order */
    public static final String ERROR_EVENT_DATE_ORDER = "Ugh, end time must be after start time. Fix your event times.";

    /** Error message for view without date */
    public static final String ERROR_VIEW_NO_DATE = "Ugh, what date do you want to view? Try 'view 2019-12-25'.";

    /** Error message for invalid date format */
    public static final String ERROR_INVALID_DATE_FORMAT = "Ugh, I can't understand that date. Try 'yyyy-MM-dd' or 'd/M/yyyy' format.";

    /** Error message for invalid date/time format */
    public static final String ERROR_INVALID_DATETIME_FORMAT = "Ugh, I can't understand that date. Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm'.";

    /** Error message for find without keyword */
    public static final String ERROR_FIND_NO_KEYWORD = "Ugh, find what? Try 'find book'.";

    /** Error message for cheer with arguments */
    public static final String ERROR_CHEER_WITH_ARGS = "Ugh, cheer command takes no arguments. Just type 'cheer'.";

    /** Error message for invalid task number */
    public static final String ERROR_INVALID_NUMBER_PREFIX = "Ugh, that's not a valid number. Try '";

    /** Error message suffix for invalid task number */
    public static final String ERROR_INVALID_NUMBER_SUFFIX = " 1' instead.";

    /** Error message for command without task number prefix */
    public static final String ERROR_COMMAND_NO_TASK_NUMBER_PREFIX = "Ugh, ";

    /** Error message suffix for command without task number */
    public static final String ERROR_COMMAND_NO_TASK_NUMBER_SUFFIX = " which task? Try '";

    /** Error message for invalid prefix usage */
    public static final String ERROR_INVALID_PREFIX_USAGE = "Ugh, I can't understand that input. Make sure to use '";

    /** Error message suffix for invalid prefix usage */
    public static final String ERROR_INVALID_PREFIX_USAGE_SUFFIX = "' prefix correctly.";

    // ========== Success Messages ==========

    /** Success message prefix for adding task */
    public static final String SUCCESS_TASK_ADDED_PREFIX = "Fine. I've added this todo:\n";

    /** Success message prefix for deleting task */
    public static final String SUCCESS_TASK_DELETED_PREFIX = "Noted. I've removed this task:\n";

    /** Success message for marking task as done */
    public static final String SUCCESS_TASK_MARKED_DONE = "Fine. I've marked this task as done:";

    /** Success message for marking task as not done */
    public static final String SUCCESS_TASK_UNMARKED = "Ugh, I've marked this task as not done:";

    /** Success message suffix for task count (singular) */
    public static final String SUCCESS_TASK_COUNT_SINGULAR = "Now you have 1 task in the list.";

    /** Success message prefix for task count */
    public static final String SUCCESS_TASK_COUNT_PREFIX = "Now you have ";

    /** Success message suffix for task count (plural) */
    public static final String SUCCESS_TASK_COUNT_PLURAL = " tasks in the list.";

    /** Farewell message */
    public static final String FAREWELL_MESSAGE = "Finally, you're leaving. Don't come back too soon.";

    // ========== Info Messages ==========

    /** Message for empty task list */
    public static final String INFO_EMPTY_TASK_LIST = "Skeptical. You haven't told me to do anything yet.";

    /** Message prefix for filtered tasks (no results) */
    public static final String INFO_NO_FILTERED_TASKS_PREFIX = "Skeptical. Nothing scheduled for ";

    /** Message suffix for filtered tasks (no results) */
    public static final String INFO_NO_FILTERED_TASKS_SUFFIX = ".";

    /** Message prefix for filtered tasks (with results) */
    public static final String INFO_FILTERED_TASKS_PREFIX = "Ugh. Here's what you have on ";

    /** Message suffix for filtered tasks (with results) */
    public static final String INFO_FILTERED_TASKS_SUFFIX = ":\n";

    /** Message for no matching tasks */
    public static final String INFO_NO_MATCHING_TASKS_PREFIX = "Fine. No tasks match \"";

    /** Message suffix for no matching tasks */
    public static final String INFO_NO_MATCHING_TASKS_SUFFIX = "\". Shocking, I know.";

    /** Message prefix for matching tasks */
    public static final String INFO_MATCHING_TASKS = "Here are matching tasks in your list:\n";

    /** Corruption message prefix */
    public static final String INFO_CORRUPTION_PREFIX = "Ugh. I skipped ";

    /** Corruption message suffix (singular) */
    public static final String INFO_CORRUPTION_SUFFIX_SINGULAR = " corrupted line.";

    /** Corruption message suffix (plural) */
    public static final String INFO_CORRUPTION_SUFFIX_PLURAL = " corrupted lines.";

    /** Corruption recovery message */
    public static final String INFO_CORRUPTION_RECOVERY = "Check monday.txt.corrupted for recovery.";

    // ========== Greeting Messages ==========

    /** Base greeting message */
    public static final String GREETING_BASE = "Ugh. It's Monday. YES, THE MONDAY. Unhelpful, unwilling, and exactly what you deserve.";

    /** Greeting for Monday */
    public static final String GREETING_MONDAY = "My namesake day. How... fitting.";

    /** Greeting for Tuesday */
    public static final String GREETING_TUESDAY = "Tuesday already feels like a decade.";

    /** Greeting for Wednesday */
    public static final String GREETING_WEDNESDAY = "Happy hump day. Not.";

    /** Greeting for Thursday */
    public static final String GREETING_THURSDAY = "Thursday. Almost there. Allegedly.";

    /** Greeting for Friday */
    public static final String GREETING_FRIDAY = "Friday. Finally. Don't get excited.";

    /** Greeting for Saturday */
    public static final String GREETING_SATURDAY = "Weekend work? Cute.";

    /** Greeting for Sunday */
    public static final String GREETING_SUNDAY = "Sunday scaries already? I live here.";

    /** Date prefix in greeting */
    public static final String GREETING_DATE_PREFIX = "Today is ";

    /** Help line in greeting */
    public static final String GREETING_HELP_LINE = "Type 'help' for how to use this app. (It's cute that you think it'll work.)";

    /** Default prompt after greeting */
    public static final String GREETING_PROMPT = "What do you want?";

    // ========== Help Messages ==========

    /** Help message header */
    public static final String HELP_HEADER = "Ugh. Fine. Here's what I understand (not that you'll listen):\n";

    /** Help message for todo command */
    public static final String HELP_TODO = "  todo <description>           - Add a todo task\n";

    /** Help message for deadline command */
    public static final String HELP_DEADLINE = "  deadline <desc> /by <time>   - Add a deadline task\n";

    /** Help message for event command */
    public static final String HELP_EVENT = "  event <desc> /from <start> /to <end> - Add an event\n";

    /** Help message for list command */
    public static final String HELP_LIST = "  list                         - Show all tasks\n";

    /** Help message for find command */
    public static final String HELP_FIND = "  find <keyword>               - Find tasks by keyword\n";

    /** Help message for view command */
    public static final String HELP_VIEW = "  view <date>                  - Show tasks for a specific date (yyyy-MM-dd)\n";

    /** Help message for mark command */
    public static final String HELP_MARK = "  mark <number>                - Mark task as done\n";

    /** Help message for unmark command */
    public static final String HELP_UNMARK = "  unmark <number>              - Mark task as not done\n";

    /** Help message for delete command */
    public static final String HELP_DELETE = "  delete <number>              - Delete a task (no going back)\n";

    /** Help message for cheer command */
    public static final String HELP_CHEER = "  cheer                        - Get \"motivated\" (you'll need it)\n";

    /** Help message for help command */
    public static final String HELP_HELP = "  help                         - Show this help (you're welcome)\n";

    /** Help message for bye/exit command */
    public static final String HELP_EXIT = "  bye / exit                   - Get rid of me";

    // ========== Corruption Messages ==========

    /** Corrupted line message prefix */
    public static final String CORRUPTED_LINE_MESSAGE_PREFIX = "Ugh. Skipping corrupted line ";

    /** Backup warning message */
    public static final String BACKUP_WARNING = "Warning: Couldn't backup corrupted line.";

    // ========== Default Quote ==========

    /** Default cheer quote when file cannot be read */
    public static final String DEFAULT_CHEER_QUOTE = "Congratulations on doing the bare minimum. That's still more than most people manage.";

    // ========== UI Formatting ==========

    /** Line separator for UI output */
    public static final String LINE_SEPARATOR = "____________________________________________________________"
            + "______";

    // ========== Storage Error Messages ==========

    /** Storage error prefix */
    public static final String STORAGE_ERROR_PREFIX = "Ugh. ";

    /** Storage error for data file access */
    public static final String STORAGE_ERROR_DATA_FILE_ACCESS = "I can't access your data file.";

    /** Storage error for saving tasks */
    public static final String STORAGE_ERROR_SAVE_TASKS = "I couldn't save your tasks.";

    // ========== Warning Messages ==========

    /** Warning prefix */
    public static final String WARNING_PREFIX = "Warning: ";

    /** Warning for unexpected exception */
    public static final String WARNING_UNEXPECTED_PREFIX = "Something unexpected happened: ";
}
