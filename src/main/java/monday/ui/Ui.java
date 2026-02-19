package monday.ui;

import monday.task.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles all user interface interactions for MONDAY.
 * Manages display output, user input, and message formatting.
 * This class acts as a facade that delegates to specialized formatters.
 */
public class Ui {

    private final MessageFormatter messageFormatter;
    private final TaskListFormatter taskListFormatter;
    private final GreetingGenerator greetingGenerator;
    private final ResponseBuilder responseBuilder;
    private final Scanner scanner;
    private String lastResponse;

    /**
     * Creates a new Ui instance and initializes formatters and input scanner.
     */
    public Ui() {
        this.messageFormatter = new MessageFormatter();
        this.taskListFormatter = new TaskListFormatter(messageFormatter);
        this.greetingGenerator = new GreetingGenerator();
        this.responseBuilder = new ResponseBuilder(messageFormatter);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a response wrapped with line separators and blank lines.
     * Also stores response for GUI retrieval.
     *
     * @param message The response message to display (can contain newlines).
     */
    public void showResponse(String message) {
        lastResponse = message;
        messageFormatter.showResponse(message);
    }

    /**
     * Gets last response message for GUI display.
     *
     * @return The last response message.
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     * Gets greeting message for GUI (without printing).
     *
     * @return The formatted greeting message.
     */
    public String getGreetingForGui() {
        return greetingGenerator.getGreetingForGui();
    }

    /**
     * Displays a grumpy greeting based on current day of the week.
     * Each day has a unique sarcastic message reflecting Monday's personality.
     */
    public void showGreeting() {
        String greeting = greetingGenerator.getGrumpyGreeting() + "\n"
                + monday.constants.MessageConstants.GREETING_PROMPT;
        showResponse(greeting);
    }

    /**
     * Displays a farewell message when user exits.
     */
    public void showFarewell() {
        responseBuilder.showFarewell();
    }

    /**
     * Displays list of all tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        taskListFormatter.showTaskList(tasks);
    }

    /**
     * Displays tasks filtered by a specific date.
     *
     * @param tasks The list of filtered tasks to display.
     * @param date The date for which tasks are being displayed.
     */
    public void showFilteredTasks(List<Task> tasks, LocalDateTime date) {
        taskListFormatter.showFilteredTasks(tasks, date);
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param tasks The list of matching tasks to display.
     * @param keyword The keyword that was searched for.
     */
    public void showMatchingTasks(List<Task> tasks, String keyword) {
        taskListFormatter.showMatchingTasks(tasks, keyword);
    }

    /**
     * Displays a confirmation message after adding a task.
     *
     * @param task The task that was added.
     * @param totalTasks The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        responseBuilder.showTaskAdded(task, totalTasks);
    }

    /**
     * Displays a confirmation message after deleting a task.
     *
     * @param task The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        responseBuilder.showTaskDeleted(task, totalTasks);
    }

    /**
     * Displays a confirmation message after marking/unmarking a task.
     *
     * @param task The task whose status was changed.
     * @param isDone true if task was marked as done, false if unmarked.
     */
    public void showTaskMarked(Task task, boolean isDone) {
        responseBuilder.showTaskMarked(task, isDone);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        messageFormatter.showError(message);
    }

    /**
     * Displays an error message for empty input.
     */
    public void showEmptyInputError() {
        messageFormatter.showEmptyInputError();
    }

    /**
     * Displays an error message for a command without arguments.
     *
     * @param command The command that needs arguments.
     * @param example The example of correct usage.
     */
    public void showCommandOnlyError(String command, String example) {
        messageFormatter.showCommandOnlyError(command, example);
    }

    /**
     * Displays an error message for invalid task number.
     *
     * @param taskCount The current number of tasks.
     */
    public void showInvalidTaskNumberError(int taskCount) {
        messageFormatter.showInvalidTaskNumberError(taskCount);
    }

    /**
     * Displays a corruption message after loading tasks with corrupted data.
     *
     * @param count The number of corrupted lines.
     */
    public void showCorruptionMessage(int count) {
        messageFormatter.showCorruptionMessage(count);
    }

    /**
     * Displays help information for all available commands.
     */
    public void showHelp() {
        responseBuilder.showHelp();
    }

    /**
     * Displays a grumpy motivational quote.
     * The quote is expected to be wrapped in ANSI color codes.
     *
     * @param quote The motivational quote to display (may contain ANSI color codes).
     */
    public void showCheerMessage(String quote) {
        messageFormatter.showCheerMessage(quote);
    }

    /**
     * Reads a command from user input.
     *
     * @return The trimmed user input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes scanner used for reading input.
     */
    public void close() {
        scanner.close();
    }
}
