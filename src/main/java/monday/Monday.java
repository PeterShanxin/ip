package monday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Named after everyone's least favorite day of the week, Monday has a
 * sarcastic personality but gets the job done.
 */
public class Monday {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

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
    private static void handleTaskStatusChange(ArrayList<Task> tasks, String userInput,
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
    private static void handleMark(ArrayList<Task> tasks, String userInput) {
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
    private static void handleUnmark(ArrayList<Task> tasks, String userInput) {
        handleTaskStatusChange(tasks, userInput, "unmark", false,
                "Ugh, I've marked this task as not done:");
    }

    /**
     * Checks if a task number is valid (within range and list is not empty).
     *
     * @param tasks The list of tasks.
     * @param taskNumber The task number to validate (1-indexed).
     * @return true if the task number is valid, false otherwise.
     */
    private static boolean isValidTaskNumber(ArrayList<Task> tasks, int taskNumber) {
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
     * Creates a ToDo task from user input.
     * Format: todo borrow book
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the todo command.
     */
    private static void handleToDo(ArrayList<Task> tasks, String userInput) {
        String description = extractDescription(userInput, "todo").trim();
        String response;

        if (description.isEmpty()) {
            response = "Ugh, a todo needs a description. Try 'todo borrow book'.";
        } else if (tasks.size() >= MAX_TASKS) {
            response = "Fine. I can't remember more than 100 things. Forget something first.";
        } else {
            ToDo todo = new ToDo(description);
            tasks.add(todo);
            response = "Fine. I've added this todo:\n" + "  " + todo + "\n"
                    + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                    + " in the list.";
        }
        printResponse(response);
    }

    /**
     * Creates a Deadline task from user input.
     * Format: deadline return book /by Sunday
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the deadline command.
     */
    private static void handleDeadline(ArrayList<Task> tasks, String userInput) {
        String response;

        try {
            String content = extractDescription(userInput, "deadline");

            if (!content.contains("/by")) {
                response = "Ugh, deadlines need a '/by' time. Try 'deadline return book /by Sunday'.";
                printResponse(response);
                return;
            }

            String[] parts = content.split("/by", 2);
            String description = parts[0].trim();
            String by = parts[1].trim();

            if (description.isEmpty()) {
                response = "Ugh, what's the deadline for? Try 'deadline return book /by Sunday'.";
            } else if (by.isEmpty()) {
                response = "Ugh, when is it due? Try 'deadline return book /by Sunday'.";
            } else if (tasks.size() >= MAX_TASKS) {
                response = "Fine. I can't remember more than 100 things. Forget something first.";
            } else {
                Deadline deadline = new Deadline(description, by);
                tasks.add(deadline);
                response = "Fine. I've added this deadline:\n" + "  " + deadline + "\n"
                        + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                        + " in the list.";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            response = "Ugh, I can't understand that deadline. Try 'deadline return book /by Sunday'.";
        }
        printResponse(response);
    }

    /**
     * Creates an Event task from user input.
     * Format: event project meeting /from Mon 2pm /to 4pm
     *
     * @param tasks The list of tasks to add to.
     * @param userInput The user input containing the event command.
     */
    private static void handleEvent(ArrayList<Task> tasks, String userInput) {
        String response;

        try {
            String content = extractDescription(userInput, "event");

            if (!content.contains("/from") || !content.contains("/to")) {
                response = "Ugh, events need '/from' and '/to' times. "
                        + "Try 'event project meeting /from Mon 2pm /to 4pm'.";
                printResponse(response);
                return;
            }

            String[] fromParts = content.split("/from", 2);
            String description = fromParts[0].trim();

            if (fromParts.length < 2) {
                response = "Ugh, I can't understand that event. "
                        + "Try 'event project meeting /from Mon 2pm /to 4pm'.";
                printResponse(response);
                return;
            }

            String[] toParts = fromParts[1].split("/to", 2);
            String from = toParts[0].trim();
            String to = toParts.length > 1 ? toParts[1].trim() : "";

            if (description.isEmpty()) {
                response = "Ugh, what's the event? Try 'event project meeting /from Mon 2pm /to 4pm'.";
            } else if (from.isEmpty()) {
                response = "Ugh, when does it start? Try 'event project meeting /from Mon 2pm /to 4pm'.";
            } else if (to.isEmpty()) {
                response = "Ugh, when does it end? Try 'event project meeting /from Mon 2pm /to 4pm'.";
            } else if (tasks.size() >= MAX_TASKS) {
                response = "Fine. I can't remember more than 100 things. Forget something first.";
            } else {
                Event event = new Event(description, from, to);
                tasks.add(event);
                response = "Fine. I've added this event:\n" + "  " + event + "\n"
                        + "Now you have " + tasks.size() + (tasks.size() == 1 ? " task" : " tasks")
                        + " in the list.";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            response = "Ugh, I can't understand that event. "
                    + "Try 'event project meeting /from Mon 2pm /to 4pm'.";
        }
        printResponse(response);
    }

    /**
     * Builds a complete greeting message from the base greeting, day-specific message, and help line.
     *
     * @param dayMessage The day-specific message to insert between base greeting and help line.
     * @return The complete formatted greeting message.
     */
    private static String buildGreeting(String dayMessage) {
        String baseGreeting = "Ugh. It's Monday. YES, THE MONDAY. Unhelpful, unwilling, "
                + "and exactly what you deserve.";
        String helpLine = "Type 'help' for how to use this app. It's cute that you think "
                + "it'll work.";
        return baseGreeting + "\n\n" + dayMessage + "\n\n" + helpLine;
    }

    /**
     * Returns a grumpy greeting based on the current day of the week.
     * Each day has a unique sarcastic message reflecting Monday's personality.
     *
     * @return A grumpy greeting message for the current day.
     */
    private static String getGrumpyGreeting() {
        DayOfWeek day = LocalDate.now().getDayOfWeek();

        switch (day) {
        case MONDAY:
            return buildGreeting("My namesake day. How... fitting.");
        case TUESDAY:
            return buildGreeting("Tuesday already feels like a decade.");
        case WEDNESDAY:
            return buildGreeting("Happy hump day. Not.");
        case THURSDAY:
            return buildGreeting("Thursday. Almost there. Allegedly.");
        case FRIDAY:
            return buildGreeting("Friday. Finally. Don't get excited.");
        case SATURDAY:
            return buildGreeting("Weekend work? Cute.");
        case SUNDAY:
            return buildGreeting("Sunday scaries already? I live here.");
        default:
            return buildGreeting("What day is it even?");
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
                + "  mark <number>                - Mark task as done\n"
                + "  unmark <number>              - Mark task as not done\n"
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
        ArrayList<Task> tasks = new ArrayList<>();
        boolean isExit = false;

        while (!isExit) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye") || userInput.equalsIgnoreCase("exit")) {
                printResponse("Finally, you're leaving. Don't come back too soon.");
                isExit = true;
            } else if (userInput.equalsIgnoreCase("list")) {
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
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                handleMark(tasks, userInput);
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                handleUnmark(tasks, userInput);
            } else if (userInput.toLowerCase().startsWith("todo ")) {
                handleToDo(tasks, userInput);
            } else if (userInput.toLowerCase().startsWith("deadline ")) {
                handleDeadline(tasks, userInput);
            } else if (userInput.toLowerCase().startsWith("event ")) {
                handleEvent(tasks, userInput);
            } else if (userInput.equalsIgnoreCase("help")) {
                handleHelp();
            } else if (userInput.isEmpty()) {
                printResponse("Ugh, you didn't actually say anything. Try again.");
            } else {
                String addResponse;
                if (tasks.size() >= MAX_TASKS) {
                    addResponse = "Fine. I can't remember more than 100 things. Forget something first.";
                } else {
                    tasks.add(new Task(userInput));
                    addResponse = "added: " + userInput;
                }
                printResponse(addResponse);
            }
        }

        scanner.close();
    }
}
