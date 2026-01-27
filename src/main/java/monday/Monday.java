package monday;

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
        System.out.println(LINE);
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
                System.out.println(successMessage);
                System.out.println("  " + task);
            } else {
                printInvalidTaskError(tasks.size());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Ugh, that's not a valid number. Try '" + command + " 1' instead.");
        }
        System.out.println(LINE);
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
     * Prints an error message for invalid task numbers.
     *
     * @param taskCount The current number of tasks in the list.
     */
    private static void printInvalidTaskError(int taskCount) {
        if (taskCount == 0) {
            System.out.println("Skeptical. You haven't told me to do anything yet.");
        } else {
            System.out.println("Ugh, that task doesn't exist. Pick between 1 and " + taskCount + ".");
        }
    }

    /**
     * Entry point for the Monday chatbot application.
     * Greets the user, processes commands, and exits when requested.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Grumpy greeting
        System.out.println(LINE);
        System.out.println("Ugh. I'm MONDAY. Yes, that Monday.");
        System.out.println("What do you want?");
        System.out.println(LINE);

        // Command loop
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        boolean isExit = false;

        while (!isExit) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println("Finally, you're leaving. Don't come back too soon.");
                System.out.println(LINE);
                isExit = true;
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println(LINE);
                if (tasks.isEmpty()) {
                    System.out.println("Skeptical. You haven't told me to do anything yet.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
                System.out.println(LINE);
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                handleMark(tasks, userInput);
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                handleUnmark(tasks, userInput);
            } else if (userInput.isEmpty()) {
                System.out.println(LINE);
                System.out.println("Ugh, you didn't actually say anything. Try again.");
                System.out.println(LINE);
            } else {
                if (tasks.size() >= MAX_TASKS) {
                    System.out.println(LINE);
                    System.out.println("Fine. I can't remember more than 100 things. "
                            + "Forget something first.");
                    System.out.println(LINE);
                } else {
                    tasks.add(new Task(userInput));
                    System.out.println(LINE);
                    System.out.println("added: " + userInput);
                    System.out.println(LINE);
                }
            }
        }

        scanner.close();
    }
}
