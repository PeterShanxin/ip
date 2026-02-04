package monday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Named after everyone's least favorite day of the week, Monday has a
 * sarcastic personality but gets the job done.
 */
public class Monday {
    private static final int MAX_TASKS = 100;
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy");

    private static Ui ui;
    private static Storage storage;

    /**
     * Handles changing a task's completion status (mark/unmark).
     * Parses the task number from input and updates the task's status.
     *
     * @param tasks The list of tasks to modify.
     * @param userInput The user input containing the command.
     * @param command The command word used ("mark" or "unmark").
     * @param markAsDone The new completion status (true for mark, false for unmark).
     * @param successMessage The message to display on success.
     */
    private static void handleTaskStatusChange(List<Task> tasks, String userInput,
            String command, boolean markAsDone, String successMessage) {
        try {
            String[] parts = userInput.trim().split("\\s+", 2);
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (isValidTaskNumber(tasks, taskNumber)) {
                Task task = tasks.get(taskNumber - 1);
                if (markAsDone) {
                    task.markAsDone();
                } else {
                    task.markAsNotDone();
                }
                saveTasksIfPossible(tasks);
                ui.showTaskMarked(task, markAsDone);
            } else {
                ui.showInvalidTaskNumberError(tasks.size());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            ui.showError("Ugh, that's not a valid number. Try '" + command + " 1' instead.");
        }
    }

    /**
     * Marks a task as done based on user input.
     * Parses the task number from input and updates the task's completion status.
     *
     * @param tasks The list of tasks to modify.
     * @param userInput The user input containing the mark command.
     */
    private static void handleMark(List<Task> tasks, String userInput) {
        handleTaskStatusChange(tasks, userInput, "mark", true,
                "Fine. I've marked this task as done:");
    }

    /**
     * Marks a task as not done based on user input.
     * Parses the task number from input and updates the task's completion status.
     *
     * @param tasks The list of tasks to modify.
     * @param userInput The user input containing the unmark command.
     */
    private static void handleUnmark(List<Task> tasks, String userInput) {
        handleTaskStatusChange(tasks, userInput, "unmark", false,
                "Ugh, I've marked this task as not done:");
    }

    /**
     * Handles task removal (delete operation).
     * Parses the task number from input and removes the task from the list.
     *
     * @param tasks The list of tasks to modify.
     * @param userInput The user input containing the command.
     * @param command The command word used ("delete").
     * @param successMessage The message to display on successful deletion.
     */
    private static void handleTaskRemoval(List<Task> tasks, String userInput,
            String command, String successMessage) {
        try {
            String[] parts = userInput.trim().split("\\s+", 2);
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (isValidTaskNumber(tasks, taskNumber)) {
                Task deletedTask = tasks.remove(taskNumber - 1);
                saveTasksIfPossible(tasks);
                ui.showTaskDeleted(deletedTask, tasks.size());
            } else {
                ui.showInvalidTaskNumberError(tasks.size());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            ui.showError("Ugh, that's not a valid number. Try '" + command + " 1' instead.");
        }
    }

    /**
     * Deletes a task from the list based on user input.
     * Parses the task number from input and removes the task.
     *
     * @param tasks The list of tasks to modify.
     * @param userInput The user input containing the delete command.
     */
    private static void handleDelete(List<Task> tasks, String userInput) {
        handleTaskRemoval(tasks, userInput, "delete",
                "Noted. I've removed this task:");
    }

    /**
     * Checks if a task number is valid (within range and list is not empty).
     *
     * @param tasks The list of tasks.
     * @param taskNumber The task number to validate (1-indexed).
     * @return true if the task number is valid, false otherwise.
     */
    private static boolean isValidTaskNumber(List<Task> tasks, int taskNumber) {
        return !tasks.isEmpty() && taskNumber >= 1 && taskNumber <= tasks.size();
    }

    /**
     * Saves tasks to disk if possible.
     * Catches any storage exceptions and prints a warning to stderr.
     *
     * @param tasks The list of tasks to save.
     */
    private static void saveTasksIfPossible(List<Task> tasks) {
        try {
            storage.saveTasks(tasks);
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
        }
    }

    /**
     * Extracts the description part from a command input.
     * Removes the command keyword and returns the rest.
     *
     * @param userInput The full user input.
     * @param command The command keyword to remove (e.g., "todo", "deadline").
     * @return The description part of the input.
     */
    private static String extractDescription(String userInput, String command) {
        return userInput.substring(command.length()).trim();
    }

    /**
     * Extracts the first word (command word) from user input.
     * The command word is the first sequence of non-whitespace characters.
     *
     * @param userInput The full user input.
     * @return The command word in lowercase, or empty string if input is empty.
     */
    private static String extractCommandWord(String userInput) {
        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        int spaceIndex = trimmed.indexOf(' ');
        return spaceIndex == -1 ? trimmed.toLowerCase()
                                : trimmed.substring(0, spaceIndex).toLowerCase();
    }

    /**
     * Checks if the user input contains only the command word with no arguments.
     *
     * @param userInput The full user input.
     * @param commandType The type of command to check for.
     * @return true if input is just the command word, false otherwise.
     */
    private static boolean isCommandOnlyInput(String userInput, CommandType commandType) {
        return userInput.trim().equalsIgnoreCase(commandType.getCommand());
    }

    /**
     * Generates an error message for unknown commands.
     * Provides a grumpy response suggesting the help command.
     *
     * @param commandWord The unknown command word.
     * @return The error message.
     */
    private static String getUnknownCommandErrorMessage(String commandWord) {
        return "Ugh, I don't understand '" + commandWord + "'. "
             + "Type 'help' if you're confused. It's probably hopeless though.";
    }

    /**
     * Creates a ToDo task from user input.
     * Format: todo borrow book
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the todo command.
     */
    private static void handleToDo(List<Task> tasks, String userInput) {
        String description = extractDescription(userInput, CommandType.TODO.getCommand()).trim();

        if (description.isEmpty()) {
            ui.showError("Ugh, a todo needs a description. Try 'todo borrow book'.");
        } else if (tasks.size() >= MAX_TASKS) {
            ui.showError("Fine. I can't remember more than 100 things. Forget something first.");
        } else {
            ToDo todo = new ToDo(description);
            tasks.add(todo);
            saveTasksIfPossible(tasks);
            ui.showTaskAdded(todo, tasks.size());
        }
    }

    /**
     * Creates a Deadline task from user input.
     * Format: deadline return book /by 2019-12-02 1800
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the deadline command.
     */
    private static void handleDeadline(List<Task> tasks, String userInput) {
        try {
            String content = extractDescription(userInput, CommandType.DEADLINE.getCommand());

            if (!content.contains(TaskPrefix.BY.toString())) {
                ui.showError("Ugh, deadlines need a '/by' time. "
                        + "Try 'deadline return book /by 2019-12-02 1800'.");
                return;
            }

            String[] parts = content.split(TaskPrefix.BY.toString(), 2);
            String description = parts[0].trim();
            String by = parts[1].trim();

            if (description.isEmpty()) {
                ui.showError("Ugh, what's the deadline for? Try 'deadline return book /by 2019-12-02 1800'.");
            } else if (by.isEmpty()) {
                ui.showError("Ugh, when is it due? Try 'deadline return book /by 2019-12-02 1800'.");
            } else if (tasks.size() >= MAX_TASKS) {
                ui.showError("Fine. I can't remember more than 100 things. Forget something first.");
            } else {
                LocalDateTime byDateTime = DateTimeParser.parseDateTime(by);
                Deadline deadline = new Deadline(description, byDateTime);
                tasks.add(deadline);
                saveTasksIfPossible(tasks);
                ui.showTaskAdded(deadline, tasks.size());
            }
        } catch (DateTimeParseException e) {
            ui.showError("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showError("Ugh, I can't understand that deadline. "
                    + "Try 'deadline return book /by 2019-12-02 1800'.");
        }
    }

    /**
     * Creates an Event task from user input.
     * Format: event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the event command.
     */
    private static void handleEvent(List<Task> tasks, String userInput) {
        try {
            String content = extractDescription(userInput, CommandType.EVENT.getCommand());

            if (!content.contains(TaskPrefix.FROM.toString()) || !content.contains(TaskPrefix.TO.toString())) {
                ui.showError("Ugh, events need '/from' and '/to' times. "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
                return;
            }

            String[] fromParts = content.split(TaskPrefix.FROM.toString(), 2);
            String description = fromParts[0].trim();

            if (fromParts.length < 2) {
                ui.showError("Ugh, I can't understand that event. "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
                return;
            }

            String[] toParts = fromParts[1].split(TaskPrefix.TO.toString(), 2);
            String from = toParts[0].trim();
            String to = toParts.length > 1 ? toParts[1].trim() : "";

            if (description.isEmpty()) {
                ui.showError("Ugh, what's the event? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
            } else if (from.isEmpty()) {
                ui.showError("Ugh, when does it start? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
            } else if (to.isEmpty()) {
                ui.showError("Ugh, when does it end? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
            } else if (tasks.size() >= MAX_TASKS) {
                ui.showError("Fine. I can't remember more than 100 things. Forget something first.");
            } else {
                LocalDateTime fromDateTime = DateTimeParser.parseDateTime(from);
                LocalDateTime toDateTime = DateTimeParser.parseDateTime(to);
                Event event = new Event(description, fromDateTime, toDateTime);
                tasks.add(event);
                saveTasksIfPossible(tasks);
                ui.showTaskAdded(event, tasks.size());
            }
        } catch (DateTimeParseException e) {
            ui.showError("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showError("Ugh, I can't understand that event. "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.");
        }
    }

    /**
     * Displays tasks scheduled for a specific date.
     * Parses the date from user input and filters tasks that occur on that date.
     *
     * @param tasks The list of tasks to filter.
     * @param userInput The user input containing the view command.
     */
    private static void handleView(List<Task> tasks, String userInput) {
        String dateString = extractDescription(userInput, CommandType.VIEW.getCommand()).trim();

        if (dateString.isEmpty()) {
            ui.showError("Ugh, what date do you want to view? Try 'view 2019-12-25'.");
            return;
        }

        try {
            LocalDateTime targetDate = parseViewDate(dateString);
            List<Task> filteredTasks = new ArrayList<>();

            for (Task task : tasks) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    if (deadline.isOnDate(targetDate)) {
                        filteredTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (event.isOnDate(targetDate)) {
                        filteredTasks.add(task);
                    }
                }
            }

            ui.showFilteredTasks(filteredTasks, targetDate);
        } catch (DateTimeParseException e) {
            ui.showError("Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd' or 'd/M/yyyy' format.");
        }
    }

    /**
     * Parses a date string for the view command.
     * Tries multiple formats: yyyy-MM-dd, then d/M/yyyy.
     *
     * @param dateString The date string to parse.
     * @return The parsed LocalDateTime (time set to midnight).
     * @throws DateTimeParseException If the string cannot be parsed with any format.
     */
    private static LocalDateTime parseViewDate(String dateString) throws DateTimeParseException {
        try {
            LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_1);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            try {
                LocalDate date = LocalDate.parse(dateString, VIEW_INPUT_FORMATTER_2);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new DateTimeParseException(
                        "Ugh, I can't understand that date. Try 'yyyy-MM-dd' or 'd/M/yyyy' format.",
                        dateString, 0);
            }
        }
    }

    /**
     * Displays help information for all available commands.
     * Maintains Monday's grumpy personality while being reluctantly helpful.
     */
    private static void handleHelp() {
        ui.showHelp();
    }

    /**
     * Entry point for the Monday chatbot application.
     * Greets the user, processes commands, and exits when requested.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ui = new Ui();
        storage = new Storage("data", "monday.txt");

        // Grumpy greeting
        ui.showGreeting();

        // Command loop
        List<Task> tasks;
        boolean hasCorruption = false;
        try {
            LoadResult loadResult = storage.loadTasks();
            tasks = loadResult.getTasks();
            hasCorruption = loadResult.hasCorruption();
            if (hasCorruption) {
                ui.showCorruptionMessage(loadResult.getCorruptedLineCount());
            }
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
            tasks = new ArrayList<>();
        }
        boolean isExit = false;

        while (!isExit) {
            String userInput = ui.readCommand();

            if (userInput.isEmpty()) {
                ui.showEmptyInputError();
                continue;
            }

            String commandWord = extractCommandWord(userInput);

            CommandType commandType = CommandType.fromString(commandWord);

            if (commandType == null) {
                ui.showError(getUnknownCommandErrorMessage(commandWord));
                continue;
            }

            switch (commandType) {
            case BYE:
                ui.showFarewell();
                isExit = true;
                break;
            case LIST:
                ui.showTaskList(tasks);
                break;
            case MARK:
                if (isCommandOnlyInput(userInput, CommandType.MARK)) {
                    ui.showError("Ugh, mark which task? Try 'mark 1'.");
                } else {
                    handleMark(tasks, userInput);
                }
                break;
            case UNMARK:
                if (isCommandOnlyInput(userInput, CommandType.UNMARK)) {
                    ui.showError("Ugh, unmark which task? Try 'unmark 1'.");
                } else {
                    handleUnmark(tasks, userInput);
                }
                break;
            case TODO:
                if (isCommandOnlyInput(userInput, CommandType.TODO)) {
                    ui.showError("Ugh, a todo needs a description. Try 'todo borrow book'.");
                } else {
                    handleToDo(tasks, userInput);
                }
                break;
            case DEADLINE:
                if (isCommandOnlyInput(userInput, CommandType.DEADLINE)) {
                    ui.showError("Ugh, deadlines need a '/by' time. "
                            + "Try 'deadline return book /by 2019-12-02 1800'.");
                } else {
                    handleDeadline(tasks, userInput);
                }
                break;
            case EVENT:
                if (isCommandOnlyInput(userInput, CommandType.EVENT)) {
                    ui.showError("Ugh, events need '/from' and '/to' times. "
                               + "Try 'event project meeting /from Mon 2pm /to 4pm'.");
                } else {
                    handleEvent(tasks, userInput);
                }
                break;
            case DELETE:
                if (isCommandOnlyInput(userInput, CommandType.DELETE)) {
                    ui.showError("Ugh, delete which task? Try 'delete 1'.");
                } else {
                    handleDelete(tasks, userInput);
                }
                break;
            case VIEW:
                if (isCommandOnlyInput(userInput, CommandType.VIEW)) {
                    ui.showError("Ugh, what date do you want to view? Try 'view 2019-12-25'.");
                } else {
                    handleView(tasks, userInput);
                }
                break;
            case HELP:
                handleHelp();
                break;
            default:
                ui.showError(getUnknownCommandErrorMessage(commandWord));
                break;
            }
        }

        // Save on exit if corruption was detected during load
        // This ensures corrupted lines are removed from monday.txt
        // even if user didn't make any changes
        if (hasCorruption) {
            saveTasksIfPossible(tasks);
        }

        ui.close();
    }
}
