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
        ArrayList<String> tasks = new ArrayList<>();
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
                    tasks.add(userInput);
                    System.out.println(LINE);
                    System.out.println("added: " + userInput);
                    System.out.println(LINE);
                }
            }
        }

        scanner.close();
    }
}
