package monday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Named after everyone's least favorite day of the week, Monday has a
 * sarcastic personality but gets the job done.
 */
public class Monday {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter VIEW_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter VIEW_INPUT_FORMATTER_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy");

    /**
     * Prints a response wrapped with line separators and blank lines around content.
     *
     * @param message The response message to display (can contain newlines).
     */
    private static void printResponse(String message) {
        System.out.println(LINE);
        System.out.println();  // blank line after opening LINE
        System.out.println(message);
        System.out.println(LINE);
        System.out.println();  // blank line after closing LINE
    }

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
        String response;
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
                response = successMessage + "\n" + "  " + task;
            } else {
                response = getInvalidTaskErrorMessage(tasks.size());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response = "Ugh, that's not a valid number. Try '" + command + " 1' instead.";
        }
        printResponse(response);
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
        String response;
        try {
            String[] parts = userInput.trim().split("\\s+", 2);
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (isValidTaskNumber(tasks, taskNumber)) {
                Task deletedTask = tasks.remove(taskNumber - 1);
                saveTasksIfPossible(tasks);
                response = successMessage + "\n" + "  " + deletedTask + "\n"
                        + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                        + " in the list.";
            } else {
                response = getInvalidTaskErrorMessage(tasks.size());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response = "Ugh, that's not a valid number. Try '" + command + " 1' instead.";
        }
        printResponse(response);
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
     * Returns an error message for invalid task numbers.
     *
     * @param taskCount The current number of tasks in the list.
     * @return The error message string.
     */
    private static String getInvalidTaskErrorMessage(int taskCount) {
        if (taskCount == 0) {
            return "Skeptical. You haven't told me to do anything yet.";
        } else {
            return "Ugh, that task doesn't exist. Pick between 1 and " + taskCount + ".";
        }
    }

    /**
     * Saves tasks to disk if possible.
     * Catches any storage exceptions and prints a warning to stderr.
     *
     * @param tasks The list of tasks to save.
     */
    private static void saveTasksIfPossible(List<Task> tasks) {
        try {
            Storage.saveTasks(tasks);
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
        String response;

        if (description.isEmpty()) {
            response = "Ugh, a todo needs a description. Try 'todo borrow book'.";
        } else if (tasks.size() >= MAX_TASKS) {
            response = "Fine. I can't remember more than 100 things. Forget something first.";
        } else {
            ToDo todo = new ToDo(description);
            tasks.add(todo);
            saveTasksIfPossible(tasks);
            response = "Fine. I've added this todo:\n" + "  " + todo + "\n"
                    + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                    + " in the list.";
        }
        printResponse(response);
    }

    /**
     * Creates a Deadline task from user input.
     * Format: deadline return book /by 2019-12-02 1800
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the deadline command.
     */
    private static void handleDeadline(List<Task> tasks, String userInput) {
        String response;

        try {
            String content = extractDescription(userInput, CommandType.DEADLINE.getCommand());

            if (!content.contains(TaskPrefix.BY.toString())) {
                response = "Ugh, deadlines need a '/by' time. "
                        + "Try 'deadline return book /by 2019-12-02 1800'.";
                printResponse(response);
                return;
            }

            String[] parts = content.split(TaskPrefix.BY.toString(), 2);
            String description = parts[0].trim();
            String by = parts[1].trim();

            if (description.isEmpty()) {
                response = "Ugh, what's the deadline for? Try 'deadline return book /by 2019-12-02 1800'.";
            } else if (by.isEmpty()) {
                response = "Ugh, when is it due? Try 'deadline return book /by 2019-12-02 1800'.";
            } else if (tasks.size() >= MAX_TASKS) {
                response = "Fine. I can't remember more than 100 things. Forget something first.";
            } else {
                LocalDateTime byDateTime = Deadline.parseDateTime(by);
                Deadline deadline = new Deadline(description, byDateTime);
                tasks.add(deadline);
                saveTasksIfPossible(tasks);
                response = "Fine. I've added this deadline:\n" + "  " + deadline + "\n"
                        + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                        + " in the list.";
            }
        } catch (DateTimeParseException e) {
            response = "Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.";
        } catch (ArrayIndexOutOfBoundsException e) {
            response = "Ugh, I can't understand that deadline. "
                    + "Try 'deadline return book /by 2019-12-02 1800'.";
        }
        printResponse(response);
    }

    /**
     * Creates an Event task from user input.
     * Format: event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the event command.
     */
    private static void handleEvent(List<Task> tasks, String userInput) {
        String response;

        try {
            String content = extractDescription(userInput, CommandType.EVENT.getCommand());

            if (!content.contains(TaskPrefix.FROM.toString()) || !content.contains(TaskPrefix.TO.toString())) {
                response = "Ugh, events need '/from' and '/to' times. "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
                printResponse(response);
                return;
            }

            String[] fromParts = content.split(TaskPrefix.FROM.toString(), 2);
            String description = fromParts[0].trim();

            if (fromParts.length < 2) {
                response = "Ugh, I can't understand that event. "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
                printResponse(response);
                return;
            }

            String[] toParts = fromParts[1].split(TaskPrefix.TO.toString(), 2);
            String from = toParts[0].trim();
            String to = toParts.length > 1 ? toParts[1].trim() : "";

            if (description.isEmpty()) {
                response = "Ugh, what's the event? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
            } else if (from.isEmpty()) {
                response = "Ugh, when does it start? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
            } else if (to.isEmpty()) {
                response = "Ugh, when does it end? "
                        + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
            } else if (tasks.size() >= MAX_TASKS) {
                response = "Fine. I can't remember more than 100 things. Forget something first.";
            } else {
                LocalDateTime fromDateTime = Event.parseDateTime(from);
                LocalDateTime toDateTime = Event.parseDateTime(to);
                Event event = new Event(description, fromDateTime, toDateTime);
                tasks.add(event);
                saveTasksIfPossible(tasks);
                response = "Fine. I've added this event:\n" + "  " + event + "\n"
                        + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                        + " in the list.";
            }
        } catch (DateTimeParseException e) {
            response = "Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd HHmm' or 'd/M/yyyy HHmm' format.";
        } catch (ArrayIndexOutOfBoundsException e) {
            response = "Ugh, I can't understand that event. "
                    + "Try 'event project meeting /from 2019-12-25 1400 /to 2019-12-25 1800'.";
        }
        printResponse(response);
    }

    /**
     * Builds a complete greeting message from the base greeting, current date, day-specific message, and help line.
     *
     * @param dayMessage The day-specific message to insert after the date.
     * @param currentDate The current date to display in the greeting.
     * @return The complete formatted greeting message.
     */
    private static String buildGreeting(String dayMessage, LocalDate currentDate) {
        String baseGreeting = "Ugh. It's Monday. YES, THE MONDAY. Unhelpful, unwilling, "
                + "and exactly what you deserve.";
        String dateLine = "Today is " + currentDate.format(DATE_FORMATTER);
        String helpLine = "Type 'help' for how to use this app. (It's cute that you think "
                + "it'll work.)";
        return baseGreeting + "\n\n" + dateLine + "\n\n" + dayMessage + "\n\n" + helpLine;
    }

    /**
     * Returns a grumpy greeting based on the current day of the week.
     * Each day has a unique sarcastic message reflecting Monday's personality.
     *
     * @return A grumpy greeting message for the current day.
     */
    private static String getGrumpyGreeting() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();

        switch (day) {
        case MONDAY:
            return buildGreeting("My namesake day. How... fitting.", currentDate);
        case TUESDAY:
            return buildGreeting("Tuesday already feels like a decade.", currentDate);
        case WEDNESDAY:
            return buildGreeting("Happy hump day. Not.", currentDate);
        case THURSDAY:
            return buildGreeting("Thursday. Almost there. Allegedly.", currentDate);
        case FRIDAY:
            return buildGreeting("Friday. Finally. Don't get excited.", currentDate);
        case SATURDAY:
            return buildGreeting("Weekend work? Cute.", currentDate);
        case SUNDAY:
            return buildGreeting("Sunday scaries already? I live here.", currentDate);
        default:
            // Unreachable: DayOfWeek enum covers all 7 days
            throw new AssertionError("Unknown day: " + day);
        }
    }

    /**
     * Formats a corruption message based on the count of corrupted lines.
     * Uses singular or plural form of "corrupted line" appropriately.
     *
     * @param count The number of corrupted lines.
     * @return The formatted corruption message.
     */
    private static String formatCorruptionMessage(int count) {
        String unit = count == 1 ? " corrupted line." : " corrupted lines.";
        return "Ugh. I skipped " + count + unit + "\nCheck monday.txt.corrupted for recovery.";
    }

    /**
     * Displays tasks scheduled for a specific date.
     * Parses the date from user input and filters tasks that occur on that date.
     *
     * @param tasks The list of tasks to filter.
     * @param userInput The user input containing the view command.
     */
    private static void handleView(List<Task> tasks, String userInput) {
        String response;
        String dateString = extractDescription(userInput, CommandType.VIEW.getCommand()).trim();

        if (dateString.isEmpty()) {
            response = "Ugh, what date do you want to view? Try 'view 2019-12-25'.";
            printResponse(response);
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

            if (filteredTasks.isEmpty()) {
                response = "Skeptical. Nothing scheduled for "
                        + targetDate.format(VIEW_OUTPUT_FORMATTER) + ".";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Ugh. Here's what you have on ")
                  .append(targetDate.format(VIEW_OUTPUT_FORMATTER))
                  .append(":\n");
                for (int i = 0; i < filteredTasks.size(); i++) {
                    if (i > 0) {
                        sb.append("\n");
                    }
                    sb.append((i + 1)).append(". ").append(filteredTasks.get(i));
                }
                response = sb.toString();
            }
        } catch (DateTimeParseException e) {
            response = "Ugh, I can't understand that date. "
                    + "Try 'yyyy-MM-dd' or 'd/M/yyyy' format.";
        }
        printResponse(response);
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
        String response = "Ugh. Fine. Here's what I understand (not that you'll listen):\n"
                + "  todo <description>           - Add a todo task\n"
                + "  deadline <desc> /by <time>   - Add a deadline task\n"
                + "  event <desc> /from <start> /to <end> - Add an event\n"
                + "  list                         - Show all tasks\n"
                + "  view <date>                  - Show tasks for a specific date (yyyy-MM-dd)\n"
                + "  mark <number>                - Mark task as done\n"
                + "  unmark <number>              - Mark task as not done\n"
                + "  delete <number>              - Delete a task (no going back)\n"
                + "  help                         - Show this help (you're welcome)\n"
                + "  bye / exit                   - Get rid of me";
        printResponse(response);
    }

    /**
     * Entry point for the Monday chatbot application.
     * Greets the user, processes commands, and exits when requested.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Grumpy greeting
        String greeting = getGrumpyGreeting() + "\n" + "What do you want?";
        printResponse(greeting);

        // Command loop
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks;
        boolean hasCorruption = false;
        try {
            LoadResult loadResult = Storage.loadTasks();
            tasks = loadResult.getTasks();
            hasCorruption = loadResult.hasCorruption();
            if (hasCorruption) {
                printResponse(formatCorruptionMessage(loadResult.getCorruptedLineCount()));
            }
        } catch (MondayStorageException e) {
            System.err.println("Warning: " + e.getMessage());
            tasks = new ArrayList<>();
        }
        boolean isExit = false;

        while (!isExit) {
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                printResponse("Ugh, you didn't actually say anything. Try again.");
                continue;
            }

            String commandWord = extractCommandWord(userInput);

            CommandType commandType = CommandType.fromString(commandWord);

            if (commandType == null) {
                printResponse(getUnknownCommandErrorMessage(commandWord));
                continue;
            }

            switch (commandType) {
            case BYE:
                printResponse("Finally, you're leaving. Don't come back too soon.");
                isExit = true;
                break;
            case LIST:
                String listResponse;
                if (tasks.isEmpty()) {
                    listResponse = "Skeptical. You haven't told me to do anything yet.";
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tasks.size(); i++) {
                        if (i > 0) {
                            sb.append("\n");
                        }
                        sb.append((i + 1)).append(". ").append(tasks.get(i));
                    }
                    listResponse = sb.toString();
                }
                printResponse(listResponse);
                break;
            case MARK:
                if (isCommandOnlyInput(userInput, CommandType.MARK)) {
                    printResponse("Ugh, mark which task? Try 'mark 1'.");
                } else {
                    handleMark(tasks, userInput);
                }
                break;
            case UNMARK:
                if (isCommandOnlyInput(userInput, CommandType.UNMARK)) {
                    printResponse("Ugh, unmark which task? Try 'unmark 1'.");
                } else {
                    handleUnmark(tasks, userInput);
                }
                break;
            case TODO:
                if (isCommandOnlyInput(userInput, CommandType.TODO)) {
                    printResponse("Ugh, a todo needs a description. Try 'todo borrow book'.");
                } else {
                    handleToDo(tasks, userInput);
                }
                break;
            case DEADLINE:
                if (isCommandOnlyInput(userInput, CommandType.DEADLINE)) {
                    printResponse("Ugh, deadlines need a '/by' time. Try 'deadline return book /by Sunday'.");
                } else {
                    handleDeadline(tasks, userInput);
                }
                break;
            case EVENT:
                if (isCommandOnlyInput(userInput, CommandType.EVENT)) {
                    printResponse("Ugh, events need '/from' and '/to' times. "
                               + "Try 'event project meeting /from Mon 2pm /to 4pm'.");
                } else {
                    handleEvent(tasks, userInput);
                }
                break;
            case DELETE:
                if (isCommandOnlyInput(userInput, CommandType.DELETE)) {
                    printResponse("Ugh, delete which task? Try 'delete 1'.");
                } else {
                    handleDelete(tasks, userInput);
                }
                break;
            case VIEW:
                if (isCommandOnlyInput(userInput, CommandType.VIEW)) {
                    printResponse("Ugh, what date do you want to view? Try 'view 2019-12-25'.");
                } else {
                    handleView(tasks, userInput);
                }
                break;
            case HELP:
                handleHelp();
                break;
            default:
                printResponse(getUnknownCommandErrorMessage(commandWord));
                break;
            }
        }

        // Save on exit if corruption was detected during load
        // This ensures corrupted lines are removed from monday.txt
        // even if user didn't make any changes
        if (hasCorruption) {
            saveTasksIfPossible(tasks);
        }

        scanner.close();
    }
}
